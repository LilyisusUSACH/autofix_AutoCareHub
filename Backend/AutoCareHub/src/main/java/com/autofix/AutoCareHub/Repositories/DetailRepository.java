package com.autofix.AutoCareHub.Repositories;

import com.autofix.AutoCareHub.Entities.DetailEntity;
import com.autofix.AutoCareHub.Entities.ReceiptEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DetailRepository extends JpaRepository<DetailEntity, Long> {
    @Transactional
    @Modifying
    @Query("UPDATE DetailEntity d SET d.value = :value, d.percent = :percent WHERE d.id = :id")
    void updateDetailById(Long id, int value, float percent);
}
