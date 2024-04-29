package com.autofix.AutoCareHub.Services;

import com.autofix.AutoCareHub.Controllers.Request.AddNewBonoDTO;
import com.autofix.AutoCareHub.Entities.BonoEntity;
import com.autofix.AutoCareHub.Entities.ReceiptEntity;
import com.autofix.AutoCareHub.Entities.VehicleEntity;
import com.autofix.AutoCareHub.Repositories.BonoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class BonoService {
    @Autowired
    BonoRepository bonoRepository;

    public ArrayList<BonoEntity> getAllBonos(){
        return (ArrayList<BonoEntity>) bonoRepository.findAll();
    }

    public ArrayList<BonoEntity> findBonosByMarca(String marca){
        return bonoRepository.getAllByMarca(marca);
    }

    public ArrayList<BonoEntity> findBonosDispoByMarca(String marca){
        return bonoRepository.findAllByMarcaAndUsadoIsFalseOrderByAmountDesc(marca);
    }

    public Optional<BonoEntity> findBonoById(Long id){
        return bonoRepository.findById(id);
    }

    public Optional<BonoEntity> findBonoDisponibleByMarca(String marca){
        return bonoRepository.getFirstByMarcaAndUsadoIsFalseOrderByAmountDesc(marca);
    }

    public int useBono(BonoEntity bono, ReceiptEntity receipt){
        bono.setReceipt(receipt);
        bono.setUsado(true);
        receipt.setBono(bono);
        bonoRepository.save(bono);
        return bono.getAmount();
    }

    public BonoEntity createNewBono(AddNewBonoDTO newBonoDTO){
        BonoEntity bono = BonoEntity.builder()
                .amount(newBonoDTO.getAmount())
                .usado(false)
                .marca(newBonoDTO.getMarca())
                .build();
        bonoRepository.save(bono);
        return bono;
    }

}
