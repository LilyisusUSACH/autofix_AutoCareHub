package com.autofix.AutoCareHub.Repositories;

import com.autofix.AutoCareHub.Entities.DetailEntity;
import com.autofix.AutoCareHub.Entities.ReceiptEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetailRepository extends JpaRepository<DetailEntity, Long> {

}
