package com.autofix.AutoCareHub.Services;

import com.autofix.AutoCareHub.Controllers.Request.RegisterReparationDTO;
import com.autofix.AutoCareHub.Entities.ReceiptEntity;
import com.autofix.AutoCareHub.Entities.ReparationEntity;
import com.autofix.AutoCareHub.Entities.VehicleEntity;
import com.autofix.AutoCareHub.Enums.EDiscNRep;
import com.autofix.AutoCareHub.Enums.ERecKm;
import com.autofix.AutoCareHub.Enums.ERecOld;
import com.autofix.AutoCareHub.Enums.ERepValue;
import com.autofix.AutoCareHub.Repositories.ReceiptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Year;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Optional;

import static com.autofix.AutoCareHub.Constants.Constants.*;
import static java.time.temporal.ChronoUnit.DAYS;

@Service
public class ReceiptService {

    @Autowired
    ReceiptRepository receiptRepository;

    @Autowired
    VehicleService vehicleService;

    @Autowired
    RepairService repairService;

    // get

        // find all
    public ArrayList<ReceiptEntity> findAllReceipt(){
        return (ArrayList<ReceiptEntity>) receiptRepository.findAll();
    }

        // find all by patente
    public ArrayList<ReceiptEntity> findAllReceiptsByPatente(String patente){
        return (ArrayList<ReceiptEntity>) receiptRepository.findAllByPatente_Patente(patente);
    }

        // find by Id
    public Optional<ReceiptEntity> findReceiptById(Long id){
        return receiptRepository.findById(id);
    }

        // find by patente
    public Optional<ReceiptEntity> findReceiptByPatente(String patente){
        return receiptRepository.findByPatente_Patente(patente);
    }
    //
    public Optional<ReceiptEntity> findReceiptUnpaidByPatente(String patente){
        return receiptRepository.findByPatente_PatenteAndPagadoIsFalse(patente);
    }
    // save

    public ReceiptEntity createReceiptEmpty(String patente){ // TODO: cambiar el elseThrow
        ReceiptEntity receipt =  ReceiptEntity.builder()
                .pagado(false)
                .retirado(false)
                .patente(
                        vehicleService.getVehicleByPatente(patente).orElseThrow()
                )
                .costoTotal(0)
                .build();
        receiptRepository.save(receipt);
        return receipt;
    }

    public ReceiptEntity saveReceipt(ReceiptEntity receipt){
        return receipt;
    }

    // update

    public ReparationEntity registerReparation(RegisterReparationDTO reparationDTO){
        int[] a = ERepValue.DIESEL.getValues();
        Optional<ReceiptEntity> optionalReceipt = findReceiptUnpaidByPatente(reparationDTO.getPatente());
        ReceiptEntity receipt = optionalReceipt.orElseGet(() -> createReceiptEmpty(reparationDTO.getPatente()));
        ReparationEntity reparation = ReparationEntity.builder()
                .fechaIngreso(LocalDate.now())
                .horaIngreso(LocalTime.now())
                .typeRep(reparationDTO.getTypeRep())
                .montoTotal(
                        switch (receipt.getPatente().getMotorType()){
                            case gasolina -> ERepValue.GASOLINA.getValues()[reparationDTO.getTypeRep()];
                            case diesel -> ERepValue.DIESEL.getValues()[reparationDTO.getTypeRep()];
                            case hibrido -> ERepValue.HIBRIDO.getValues()[reparationDTO.getTypeRep()];
                            case electrico -> ERepValue.ELECTRICO.getValues()[reparationDTO.getTypeRep()];
                        }
                )
                .build();
        addReparationToReceipt(receipt,reparation);
        repairService.saveReparation(reparation);
        saveReceipt(receipt);
        return reparation;
    }


    public ReceiptEntity addReparationToReceipt(ReceiptEntity receipt, ReparationEntity reparation){
        receipt.getReparaciones().add(reparation);
        reparation.setReceipt(receipt);
        return saveReceipt(receipt);
    }

    public ReceiptEntity delivered(String patente){
        Optional<ReceiptEntity> optionalReceipt = findReceiptUnpaidByPatente(patente);
        ReceiptEntity receipt = optionalReceipt.orElseThrow();
        receipt.setRetirado(true);
        for (ReparationEntity reparation : receipt.getReparaciones()) {
            repairService.reparationDelivered(reparation);
        }
        return saveReceipt(receipt);
    }

    public int nReparationsDiscount(VehicleEntity vehicle, int costoReparaciones){
        int cantidad = repairService.countRepairsInUltimate12Month(vehicle);
        if(cantidad == 0){
            return 0;
        }
        float discount = switch (vehicle.getMotorType()){
            case gasolina -> EDiscNRep.GASOLINA.getValues()[cantidad];
            case diesel -> EDiscNRep.DIESEL.getValues()[cantidad];
            case hibrido -> EDiscNRep.HIBRIDO.getValues()[cantidad];
            case electrico -> EDiscNRep.ELECTRICO.getValues()[cantidad];
        };
        return Math.round(costoReparaciones*discount);
        // TODO: ver si redondear o truncar ((int) el casteo trunca) o tirar para arriba (Math.ceil(f))D:
    }

    public int ingresoDiscount(ReceiptEntity receipt, int costoReparaciones){
        ReparationEntity reparationFirstFecha = repairService.getFirstIngresadoByReceipt(receipt).orElseThrow(); // obtengo el primero en ingresar
        ReparationEntity reparation = repairService.getFirstInADay(reparationFirstFecha.getFechaIngreso(), receipt);

        DayOfWeek ingresoDay = reparation.getFechaIngreso().getDayOfWeek();

        LocalTime ingresoHours = reparation.getHoraIngreso();

        LocalTime start = LocalTime.parse( HoraInicioDescuento );
        LocalTime end = LocalTime.parse( HoraTerminoDescuento );

        if( ingresoDay.getValue() == DayOfWeek.MONDAY.getValue()
                || ingresoDay.getValue() == DayOfWeek.THURSDAY.getValue()){
            if(ingresoHours.isAfter(start) && ingresoHours.isBefore(end)){
             return Math.round(costoReparaciones*0.1f);
            }
        }
        return 0;
    }

    public int kmRecargo(VehicleEntity vehicle, int costoReparaciones){
        int km = vehicle.getKmRecorridos();
        int categoria;
        if(km <= 5000){
            categoria = 0;
        } else if (km <= 12000){
            categoria = 1;
        } else if (km <= 25000){
            categoria = 2;
        } else if (km <= 40000){
            categoria = 3;
        }else {
            categoria = 4;
        }
        float recargo = switch (vehicle.getCarType()){
            case sedan -> ERecKm.SEDAN.getValues()[categoria];
            case hatchback -> ERecKm.HATCHBACK.getValues()[categoria];
            case suv -> ERecKm.SUV.getValues()[categoria];
            case pickup -> ERecKm.PICKUP.getValues()[categoria];
            case furgoneta -> ERecKm.FURGONETA.getValues()[categoria];
        };
        return Math.round(costoReparaciones*recargo);
    }

    public int oldRecargo(VehicleEntity vehicle, int costoReparaciones){
        int antiguedad = Year.now().minusYears(vehicle.getFabricationYear().getValue()).getValue();
        int categoria;
        if(antiguedad <= 5){
            categoria = 0;
        } else if (antiguedad <= 10){
            categoria = 1;
        } else if (antiguedad <= 15){
            categoria = 2;
        } else{
            categoria = 3;
        }
        float recargo = switch (vehicle.getCarType()){
            case sedan -> ERecOld.SEDAN.getValues()[categoria];
            case hatchback -> ERecOld.HATCHBACK.getValues()[categoria];
            case suv -> ERecOld.SUV.getValues()[categoria];
            case pickup -> ERecOld.PICKUP.getValues()[categoria];
            case furgoneta -> ERecOld.FURGONETA.getValues()[categoria];
        };
        return Math.round(costoReparaciones*recargo);
    }

    public int retrasoRecargo(ReceiptEntity receipt, int costoReparaciones){
        ArrayList<ReparationEntity> reparations = (ArrayList<ReparationEntity>) receipt.getReparaciones();
        reparations.sort(Comparator.comparing(ReparationEntity::getFechaSalida).reversed());
        int diference = (int) DAYS.between(LocalDate.now(),reparations.get(0).getFechaSalida());
        return Math.round( costoReparaciones * (diference * 0.05f));
    }

    public ReceiptEntity calculateAmountByPatente(String patente, Boolean applyBono){
        Optional<ReceiptEntity> optionalReceipt = findReceiptUnpaidByPatente(patente);
        ReceiptEntity receipt = optionalReceipt.orElseThrow();
        int sumaRep = 0;
        for (ReparationEntity reparation : receipt.getReparaciones()) {
            sumaRep += reparation.getMontoTotal();
        }
        int discounts = nReparationsDiscount(receipt.getPatente(),sumaRep) +
                ingresoDiscount(receipt, sumaRep);
        if(applyBono){
            discounts += 0; // TODO: hacer esto cuando termine el bono service
        }
        int recargos = kmRecargo(receipt.getPatente(), sumaRep) +
                oldRecargo(receipt.getPatente(), sumaRep);

        return saveReceipt(receipt);
    }
    //     public ReceiptEntity calculateAmountByReceipt(ReceiptEntity receipt){
    // delete
}
