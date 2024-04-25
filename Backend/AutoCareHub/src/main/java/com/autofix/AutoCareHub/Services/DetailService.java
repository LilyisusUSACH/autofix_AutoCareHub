package com.autofix.AutoCareHub.Services;

import com.autofix.AutoCareHub.Entities.DetailEntity;
import com.autofix.AutoCareHub.Entities.ReceiptEntity;
import com.autofix.AutoCareHub.Enums.EDiscountsRecharges;
import com.autofix.AutoCareHub.Repositories.DetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DetailService {
    @Autowired
    DetailRepository detailRepository;
    public DetailEntity createDetail(EDiscountsRecharges description, int value, float percent, ReceiptEntity receipt){
        DetailEntity detail = DetailEntity.builder()
                .description(description)
                .percent(percent)
                .value(value)
                .receipt(receipt)
                .build();
//        if(receipt.getDetails().stream().anyMatch(e -> e.getDescription() == description)){
//            detail = null;
//            return detail;
//        }
        for (DetailEntity det : receipt.getDetails()){
            if(det.getDescription().equals(description)){
                det.setValue(value);
                det.setPercent(percent);
                detailRepository.save(det);
                return det;
            }
        }
        receipt.getDetails().add(detail);
        detailRepository.save(detail);
        return detail;
    }
}
