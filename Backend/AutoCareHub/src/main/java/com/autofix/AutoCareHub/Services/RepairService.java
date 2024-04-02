package com.autofix.AutoCareHub.Services;

import com.autofix.AutoCareHub.Controllers.Request.RegisterReparationDTO;
import com.autofix.AutoCareHub.Entities.ReceiptEntity;
import com.autofix.AutoCareHub.Entities.ReparationEntity;
import com.autofix.AutoCareHub.Entities.VehicleEntity;
import com.autofix.AutoCareHub.Enums.ERepValue;
import com.autofix.AutoCareHub.Repositories.ReparationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class RepairService {
    @Autowired
    ReparationRepository reparationRepository;

    @Autowired
    ReceiptService receiptService;
    // get

        // Find all
    public ArrayList<ReparationEntity> getAllReparations(){
        return (ArrayList<ReparationEntity>) reparationRepository.findAll();
    }

        // Find By Id
    public Optional<ReparationEntity> getReparationById(Long id){
        return reparationRepository.findById(id);
    }

        // Find By Patente
    public Optional<ReparationEntity> getReparationByPatente(String patente){
        return reparationRepository.findByReceipt_PatentePatente(patente);
    }

        // Find first order by fecha ingreso
    public Optional<ReparationEntity> getFirstIngresadoByReceipt(ReceiptEntity receipt){
        return reparationRepository.findFirstByReceiptOrderByFechaIngresoDesc(receipt);
    }

        // Find by fecha ingreso
    public ArrayList<ReparationEntity> getAllByFechaIngresoReceipt(LocalDate date, ReceiptEntity receipt){
        return (ArrayList<ReparationEntity>) reparationRepository.findAllByFechaIngresoAndReceipt(date, receipt);
    }

        // get The first in a day
    public ReparationEntity getFirstInADay(LocalDate date, ReceiptEntity receipt){
        ArrayList<ReparationEntity> reparations = getAllByFechaIngresoReceipt(date, receipt);
        reparations.sort(Comparator.comparing(ReparationEntity::getHoraIngreso));
        return reparations.get(0);
    }

    // count n repair 12 meses
    public int countRepairsInUltimate12Month(VehicleEntity vehicle){
        return reparationRepository.countByFechaIngresoBetweenAnAndReceipt_Patente(LocalDate.now(), LocalDate.now().minusMonths(12), vehicle);
    }

    // save

    // Deberia crear la boleta aca? o es trabajo del receipt service(? no lo sabremos ah sdjkjkds
    public ReparationEntity registerReparation(RegisterReparationDTO reparationDTO){
        int[] a = ERepValue.DIESEL.getValues();
        Optional<ReceiptEntity> optionalReceipt = receiptService.findReceiptUnpaidByPatente(reparationDTO.getPatente());
        ReceiptEntity receipt = optionalReceipt.orElseGet(() -> receiptService.createReceiptEmpty(reparationDTO.getPatente()));
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
        receiptService.addReparationToReceipt(receipt,reparation);
        return reparation;
    }

    // Update TODO: añadir salida (ya reparado) y retiro

    public ReparationEntity reparationComplete(ReparationEntity reparation){
        reparation.setFechaSalida(LocalDate.now());
        reparation.setHoraSalida(LocalTime.now());
        return reparation;
    }

    public ReparationEntity reparationDelivered(ReparationEntity reparation){
        reparation.setFechaRetiro(LocalDate.now());
        reparation.setHoraRetiro(LocalTime.now());
        return reparation;
    }
    // delete ?

}
