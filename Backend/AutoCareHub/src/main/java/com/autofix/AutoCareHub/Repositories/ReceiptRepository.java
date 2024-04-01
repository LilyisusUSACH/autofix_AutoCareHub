package com.autofix.AutoCareHub.Repositories;

import com.autofix.AutoCareHub.Entities.ReceiptEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReceiptRepository extends JpaRepository<ReceiptEntity, Long> {
    Optional<ReceiptEntity> findByPatente_Patente(String patente);
}
