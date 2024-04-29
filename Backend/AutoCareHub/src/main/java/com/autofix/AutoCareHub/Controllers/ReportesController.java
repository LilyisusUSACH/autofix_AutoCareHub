package com.autofix.AutoCareHub.Controllers;

import com.autofix.AutoCareHub.Services.ReportesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reportes")
@CrossOrigin("*")
public class ReportesController {
    @Autowired
    ReportesService reportesService;
    @GetMapping("/r1")
    public ResponseEntity<?> getR1(){
        return new ResponseEntity<>(reportesService.getR1(), HttpStatus.OK);
    }
    @GetMapping("/r2")
    public ResponseEntity<?> getR2(){
        return new ResponseEntity<>(reportesService.getR2(), HttpStatus.OK);
    }

    @GetMapping("/r3")
    public ResponseEntity<?> getR3(){
        return new ResponseEntity<>(reportesService.getR3(), HttpStatus.OK);
    }

    @GetMapping("/r4")
    public ResponseEntity<?> getR4(){
        return new ResponseEntity<>(reportesService.getR4(), HttpStatus.OK);
    }

}
