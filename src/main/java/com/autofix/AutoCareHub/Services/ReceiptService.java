package com.autofix.AutoCareHub.Services;

import com.autofix.AutoCareHub.Controllers.Request.RegisterReparationDTO;
import com.autofix.AutoCareHub.Entities.*;
import com.autofix.AutoCareHub.Enums.*;
import com.autofix.AutoCareHub.Repositories.DetailRepository;
import com.autofix.AutoCareHub.Repositories.ReceiptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Console;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Year;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @Autowired
    BonoService bonoService;

    @Autowired
    DetailService detailService;

    // get

        // find all
    public ArrayList<ReceiptEntity> findAllReceipt(){
        return (ArrayList<ReceiptEntity>) receiptRepository.findAll();
    }

        // find all by patente
    public ArrayList<ReceiptEntity> findAllReceiptsByPatente(String patente){
        return (ArrayList<ReceiptEntity>) receiptRepository.findAllByPatente_PatenteOrderByIdDesc(patente);
    }

        // find by Id
    public Optional<ReceiptEntity> findReceiptById(Long id){
        return receiptRepository.findById(id);
    }

        // find by patente
    public Optional<ReceiptEntity> findReceiptByPatente(String patente){
        return receiptRepository.findByPatente_PatenteOrderByPagadoDesc(patente);
    }
    //
    public Optional<ReceiptEntity> findReceiptUnpaidByPatente(String patente){
        return receiptRepository.findByPatente_PatenteAndPagadoIsFalse(patente);
    }

    public List<ReceiptEntity> findReceiptsUnpaidByPatente(String patente){
        return receiptRepository.findAllByPatente_PatenteAndPagadoIsFalse(patente);
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
                //.detail(new ArrayList<>())
                .reparaciones(new ArrayList<>())
                .build();
        receiptRepository.save(receipt);
        return receipt;
    }

    public ReceiptEntity saveReceipt(ReceiptEntity receipt){
        receiptRepository.saveAndFlush(receipt);
        return receipt;
    }

    public ReparationEntity registerReparation(RegisterReparationDTO reparationDTO){
        int[] a = ERepValue.DIESEL.getValues();
        Optional<ReceiptEntity> optionalReceipt = findReceiptUnpaidByPatente(reparationDTO.getPatente());
        ReceiptEntity receipt = optionalReceipt.orElseGet(() -> createReceiptEmpty(reparationDTO.getPatente()));
        //if(receipt.getCostoTotal()!=0) receipt = createReceiptEmpty(reparationDTO.getPatente());
        ReparationEntity reparation = ReparationEntity.builder()
                .fechaIngreso((reparationDTO.getFechaIngreso() == null)?LocalDate.now():reparationDTO.getFechaIngreso())
                .horaIngreso((reparationDTO.getHoraIngreso() == null)?LocalTime.now():reparationDTO.getHoraIngreso()) //LocalTime.now()
                .fechaSalida(reparationDTO.getFechaSalida())
                .horaSalida(reparationDTO.getHoraSalida())
                .fechaRetiro(reparationDTO.getFechaRetiro())
                .horaRetiro(reparationDTO.getHoraRetiro())
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
        receipt.getReparaciones().add(reparation);
        reparation.setReceipt(receipt);
        repairService.saveReparation(reparation);
        saveReceipt(receipt);
        return reparation;
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

    public int nReparationsDiscount(ReceiptEntity receipt, int costoReparaciones){
        VehicleEntity vehicle = receipt.getPatente();
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
        detailService.createDetail(EDiscountsRecharges.descuento_reparaciones,
                -1*Math.round(costoReparaciones*discount) , -1*discount, receipt);
        return Math.round(costoReparaciones*discount);
        // TODO: ver si redondear o truncar ((int) el casteo trunca) o tirar para arriba (Math.ceil(f))D:
    }

    public int ingresoDiscount(ReceiptEntity receipt, int costoReparaciones){
        ReparationEntity reparationFirstFecha = repairService.getFirstIngresadoByReceipt(receipt).orElseThrow(); // obtengo el primero en ingresar
        ReparationEntity reparation = repairService.getFirstInADay(reparationFirstFecha.getFechaIngreso(), receipt);

        float descuento = 0.1f;// TODO: pasar a constante

        DayOfWeek ingresoDay = reparation.getFechaIngreso().getDayOfWeek();

        LocalTime ingresoHours = reparation.getHoraIngreso();

        LocalTime start = LocalTime.parse( HoraInicioDescuento );
        LocalTime end = LocalTime.parse( HoraTerminoDescuento );

//        System.out.println(ingresoDay);
//        System.out.println(ingresoHours);
        
        if( ingresoDay.getValue() == DayOfWeek.MONDAY.getValue()
                || ingresoDay.getValue() == DayOfWeek.THURSDAY.getValue()){

            if(ingresoHours.isAfter(start) && ingresoHours.isBefore(end)){
                detailService.createDetail(EDiscountsRecharges.descuento_dia,
                        -1*Math.round(costoReparaciones*descuento), -1*descuento, receipt);
             return Math.round(costoReparaciones*descuento);
            }
        }
        detailService.createDetail(EDiscountsRecharges.descuento_dia,
                0, 0f, receipt);
        return 0;
    }

    public int kmRecargo(ReceiptEntity receipt, int costoReparaciones){
        VehicleEntity vehicle = receipt.getPatente();
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
        detailService.createDetail(EDiscountsRecharges.recargo_km,
                Math.round(costoReparaciones*recargo), recargo, receipt);
        return Math.round(costoReparaciones*recargo);
    }

    public int oldRecargo(ReceiptEntity receipt, int costoReparaciones){
        VehicleEntity vehicle = receipt.getPatente();
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
        detailService.createDetail(EDiscountsRecharges.recargo_antiguedad,
                Math.round(costoReparaciones*recargo), recargo, receipt);
        return Math.round(costoReparaciones*recargo);
    }

    public int retrasoRecargo(ReceiptEntity receipt, int costoReparaciones){
        ArrayList<ReparationEntity> reparations = new ArrayList<>(receipt.getReparaciones());
        reparations.sort(Comparator.comparing(ReparationEntity::getFechaSalida).reversed());
        int diference = (int) DAYS.between(reparations.get(0).getFechaSalida(), LocalDate.now());
        detailService.createDetail(EDiscountsRecharges.recargo_retraso,
                Math.round( costoReparaciones * (diference * 0.05f)),  (diference * 0.05f), receipt);

        return Math.round( costoReparaciones * (diference * 0.05f));
    }

    public ReceiptEntity calculateAmountByPatente(String patente, Boolean applyBono, Long id_bono) throws Exception {
        Optional<ReceiptEntity> optionalReceipt = findReceiptUnpaidByPatente(patente);
        if(optionalReceipt.isEmpty()){
            throw new Exception("No hay recibos pendientes");
        }
        ReceiptEntity receipt = optionalReceipt.get();
        if(receipt.getCostoTotal()>0) {
            receipt.setCostoTotal(0);
        };
        int sumaRep = receipt.getReparaciones().stream().mapToInt(ReparationEntity::getMontoTotal).sum();
//        for (ReparationEntity reparation : receipt.getReparaciones()) {
//            sumaRep += reparation.getMontoTotal();
//        }
        detailService.createDetail(EDiscountsRecharges.suma_reparacion,
                sumaRep, 1, receipt);
        //receipt.getDetail().add("La suma de las reparaciones es :");

        int discounts = nReparationsDiscount(receipt,sumaRep) +
                ingresoDiscount(receipt, sumaRep);
        if(applyBono) { //
            Optional<BonoEntity> bono;
            if(id_bono == null) {
                bono = bonoService.findBonoDisponibleByMarca(receipt.getPatente().getMarca());
            }else{
                bono = bonoService.findBonoById(id_bono);
                if (bono.isPresent() && bono.get().getUsado()) bono = Optional.empty();
            }
            if(bono.isPresent() && receipt.getBono()!= null && !receipt.getBono().equals(bono.get())){
                DetailEntity detailFound =  receipt.getDetails().stream().filter( detail -> detail.getDescription().equals(EDiscountsRecharges.descuento_bono) ).findFirst().orElseThrow();
                receipt.getDetails().remove(detailFound);
                detailService.deleteDetail(detailFound.getId());
                receipt.getBono().setUsado(false);
                receipt.getBono().setReceipt(null);
                receipt.setBono(null);
            }
            if (bono.isPresent()) {
                discounts += bonoService.useBono(bono.get(), receipt); // TODO: hacer esto cuando termine el bono service
                detailService.createDetail(EDiscountsRecharges.descuento_bono,
                        -1*bono.get().getAmount(), -1, receipt);
            }
        }
        else{
                DetailEntity detailFound =  receipt.getDetails().stream().filter( detail -> detail.getDescription().equals(EDiscountsRecharges.descuento_bono) ).findFirst().orElseThrow();
                receipt.getDetails().remove(detailFound);
                detailService.deleteDetail(detailFound.getId());
                receipt.getBono().setUsado(false);
                receipt.getBono().setReceipt(null);
                receipt.setBono(null);
        }
        int recargos = kmRecargo(receipt, sumaRep) +
                oldRecargo(receipt, sumaRep) +
                retrasoRecargo(receipt, sumaRep);
        int costoTotal =  (sumaRep - discounts + recargos);
        detailService.createDetail(EDiscountsRecharges.iva,
                Math.round(costoTotal*IVA), IVA, receipt);
        costoTotal = Math.round(costoTotal*(1+IVA));
        receipt.setCostoTotal(costoTotal);
        saveReceipt(receipt);
        return receipt;
    }
    //     public ReceiptEntity calculateAmountByReceipt(ReceiptEntity receipt){
    // delete

    public ReceiptEntity calculateAmountByReceiptId(Long id, Boolean applyBono, Long id_bono) throws Exception {
        Optional<ReceiptEntity> optionalReceipt = findReceiptById(id);
        if(optionalReceipt.isEmpty()){
            throw new Exception("No hay recibos pendientes");
        }
        ReceiptEntity receipt = optionalReceipt.get();
        if(receipt.getCostoTotal()>0) {
            receipt.setCostoTotal(0);
        };
        int sumaRep = receipt.getReparaciones().stream().mapToInt(ReparationEntity::getMontoTotal).sum();
//        for (ReparationEntity reparation : receipt.getReparaciones()) {
//            sumaRep += reparation.getMontoTotal();
//        }
        detailService.createDetail(EDiscountsRecharges.suma_reparacion,
                sumaRep, 1, receipt);
        //receipt.getDetail().add("La suma de las reparaciones es :");

        int discounts = nReparationsDiscount(receipt,sumaRep) +
                ingresoDiscount(receipt, sumaRep);
        if(applyBono) { //
            Optional<BonoEntity> bono;
            if(id_bono == null) {
                bono = bonoService.findBonoDisponibleByMarca(receipt.getPatente().getMarca());
            }else{
                bono = bonoService.findBonoById(id_bono);
                if (bono.isPresent() && bono.get().getUsado() && !bono.get().equals(receipt.getBono())) bono = Optional.empty();
            }
            if( bono.isPresent() && receipt.getBono()!=null ){
                Optional<DetailEntity> detailFound =  receipt.getDetails()
                        .stream().filter( detail -> detail.getDescription().equals(EDiscountsRecharges.descuento_bono) )
                        .findFirst();
                if(detailFound.isPresent()){
                    receipt.getDetails().remove(detailFound.get());
                    detailService.deleteDetail(detailFound.get().getId());
                    receipt.getBono().setUsado(false);
                    receipt.getBono().setReceipt(null);
                    receipt.setBono(null);
                }
            }
//            if(bono.isPresent() && receipt.getBono()!= null && !receipt.getBono().equals(bono.get())){
//                DetailEntity detailFound =  receipt.getDetails().stream().filter( detail -> detail.getDescription().equals(EDiscountsRecharges.descuento_bono) ).findFirst().orElseThrow();
//                receipt.getDetails().remove(detailFound);
//                detailService.deleteDetail(detailFound.getId());
//                receipt.getBono().setUsado(false);
//                receipt.getBono().setReceipt(null);
//                receipt.setBono(null);
//            }
            if (bono.isPresent()) {
                discounts += bonoService.useBono(bono.get(), receipt); // TODO: hacer esto cuando termine el bono service
                detailService.createDetail(EDiscountsRecharges.descuento_bono,
                        -1*bono.get().getAmount(), -1, receipt);
            }
        }
        else{
            if(receipt.getBono()!=null){
                Optional<DetailEntity> detailFound =  receipt.getDetails()
                        .stream().filter( detail -> detail.getDescription().equals(EDiscountsRecharges.descuento_bono) )
                        .findFirst();
                if(detailFound.isPresent()){
                    receipt.getDetails().remove(detailFound.get());
                    detailService.deleteDetail(detailFound.get().getId());
                    receipt.getBono().setUsado(false);
                    receipt.getBono().setReceipt(null);
                    receipt.setBono(null);
                }
            }
        }
        int recargos = kmRecargo(receipt, sumaRep) +
                oldRecargo(receipt, sumaRep) +
                retrasoRecargo(receipt, sumaRep);
        int costoTotal =  (sumaRep - discounts + recargos);
        detailService.createDetail(EDiscountsRecharges.iva,
                Math.round(costoTotal*IVA), IVA, receipt);
        costoTotal = Math.round(costoTotal*(1+IVA));
        receipt.setCostoTotal(costoTotal);
        saveReceipt(receipt);
        return receipt;
    }

    public ReceiptEntity updateReceipt(ReceiptEntity receipt){
        return receiptRepository.saveAndFlush(receipt);
    }

    public ReceiptEntity paidReceipt(Long id){
            Optional<ReceiptEntity> receipt = receiptRepository.findById(id);
            if(receipt.isPresent()){
                receipt.get().setPagado(true);
                receipt.get().setRetirado(true);
                receipt.get().getReparaciones().forEach( reparation -> {
                    reparation.setFechaRetiro(LocalDate.now());
                    reparation.setHoraRetiro(LocalTime.now());
                });
                receiptRepository.save(receipt.get());
            }
            return receipt.orElseGet(() -> null);
    }
    public boolean deleteReceipt(Long id){
        try{
            receiptRepository.deleteById(id);
            return true;
        }catch (Exception e) {
            return false;
        }
    }
}
