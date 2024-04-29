package com.autofix.AutoCareHub.Services;

import com.autofix.AutoCareHub.Controllers.Request.R2DTO;
import com.autofix.AutoCareHub.Controllers.Request.R3DTO;
import com.autofix.AutoCareHub.Controllers.Request.R4DTO;
import com.autofix.AutoCareHub.Repositories.ReportesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportesService {
    @Autowired
    ReportesRepository reportesRepository;

    public List<R2DTO> getR2(){
        return reportesRepository.findR2();
    }

    public List<R3DTO> getR3(){
        return reportesRepository.findR3();
    }

    public List<R4DTO> getR4(){
        return reportesRepository.findR4();
    }

}
