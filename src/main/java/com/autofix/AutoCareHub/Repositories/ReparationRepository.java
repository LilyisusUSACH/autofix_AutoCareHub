package com.autofix.AutoCareHub.Repositories;

import com.autofix.AutoCareHub.Controllers.Request.R2DTO;
import com.autofix.AutoCareHub.Entities.ReceiptEntity;
import com.autofix.AutoCareHub.Entities.ReparationEntity;
import com.autofix.AutoCareHub.Entities.VehicleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReparationRepository extends JpaRepository<ReparationEntity, Long> {

    Optional<ReparationEntity> findByReceipt_PatentePatente(String patente);

    ArrayList<ReparationEntity> findAllByFechaSalidaIsNull();

    ArrayList<ReparationEntity> findAllByFechaSalidaIsNullOrderByFechaIngresoDescHoraIngresoDesc();


    Optional<ReparationEntity> findFirstByReceiptOrderByFechaIngresoDesc(ReceiptEntity receipt);

    List<ReparationEntity> findAllByReceipt_PatentePatente(String patente);

    List<ReparationEntity> findAllByFechaIngresoAndReceipt(LocalDate fecha, ReceiptEntity receipt);

    int countByFechaIngresoBetweenAndReceipt_Patente(LocalDate inicio, LocalDate termino, VehicleEntity patente);

    void deleteById(Long id);

}
