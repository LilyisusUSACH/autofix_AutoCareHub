package com.autofix.AutoCareHub.Repositories;

import com.autofix.AutoCareHub.Entities.BonoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.Optional;

@Repository
public interface BonoRepository extends JpaRepository<BonoEntity, Long> {

    ArrayList<BonoEntity> getAllByMarca(String marca);
    ArrayList<BonoEntity> findAllByMarcaAndUsadoIsFalseOrderByAmountDesc(String marca);

    Optional<BonoEntity> getFirstByMarcaAndUsadoIsFalseOrderByAmountDesc(String marca);

}
