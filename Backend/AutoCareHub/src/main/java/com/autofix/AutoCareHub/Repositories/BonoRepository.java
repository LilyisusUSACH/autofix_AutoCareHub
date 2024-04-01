package com.autofix.AutoCareHub.Repositories;

import com.autofix.AutoCareHub.Entities.BonoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BonoRepository extends JpaRepository<BonoEntity, Long> {

}
