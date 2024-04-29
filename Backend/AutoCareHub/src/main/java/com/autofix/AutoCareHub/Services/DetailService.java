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
        for (DetailEntity det : receipt.getDetails()){
            if(det.getDescription().equals(description)){
                det.setValue(value);
                det.setPercent(percent);
                return det;
            }
        }
        DetailEntity detail = DetailEntity.builder()
                .description(description)
                .percent(percent)
                .value(value)
                .receipt(receipt)
                .build();
        receipt.getDetails().add(detail);
        return detail;
    }

    public void deleteDetail(Long id){
        detailRepository.deleteById(id);
    }
}
