package com.autofix.AutoCareHub.Repositories;

import com.autofix.AutoCareHub.Entities.ReparationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReparationRepository extends JpaRepository<ReparationEntity, Long> {
    Optional<ReparationEntity> findByReceipt_PatentePatente(String patente);
}
