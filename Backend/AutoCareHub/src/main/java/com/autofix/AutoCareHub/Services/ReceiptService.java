package com.autofix.AutoCareHub.Services;

import com.autofix.AutoCareHub.Entities.ReceiptEntity;
import com.autofix.AutoCareHub.Entities.ReparationEntity;
import com.autofix.AutoCareHub.Repositories.ReceiptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class ReceiptService {
    @Autowired
    ReceiptRepository receiptRepository;

    @Autowired
    VehicleService vehicleService;

    // get

        // find all
    public ArrayList<ReceiptEntity> findAllReceipt(){
        return (ArrayList<ReceiptEntity>) receiptRepository.findAll();
    }

        // find by Id
    public Optional<ReceiptEntity> findReceiptById(Long id){
        return receiptRepository.findById(id);
    }

        // find by patente
    public Optional<ReceiptEntity> findReceiptByPatente(String patente){
        return receiptRepository.findByPatente_Patente(patente);
    }

    // save

    public ReceiptEntity createReceiptEmpty(String patente){ // TODO: cambiar el elseThrow
        return ReceiptEntity.builder()
                .pagado(false)
                .retirado(false)
                .patente(
                        vehicleService.getVehicleByPatente(patente).orElseThrow()
                )
                .costoTotal(0)
                .build();
    }

    // update

    public ReceiptEntity addReparationToReceipt(ReceiptEntity receipt, ReparationEntity reparation){
        receipt.getReparaciones().add(reparation);
        reparation.setReceipt(receipt);
        return receipt;
    }

    // delete
}
