package com.autofix.AutoCareHub.Repositories;

import com.autofix.AutoCareHub.Entities.VehicleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VehicleRepository extends JpaRepository<VehicleEntity, Long> {

    Optional<VehicleEntity> findByPatente(String patente);

    List<VehicleEntity> findByMarca(String marca);

    boolean existsByPatente(String patente);
}
