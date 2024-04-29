package com.autofix.AutoCareHub.Controllers;

import com.autofix.AutoCareHub.Controllers.Request.AddNewBonoDTO;
import com.autofix.AutoCareHub.Services.BonoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bono")
@CrossOrigin("*")
public class BonoController {

    @Autowired
    BonoService bonoService;

    @GetMapping("/")
    public ResponseEntity<?> getAllBonos(){
        return ResponseEntity.ok( bonoService.getAllBonos() );
    }

    @GetMapping("/marca")
    public ResponseEntity<?> getBonosByMarca(@RequestParam("marca") String marca){
        return ResponseEntity.ok( bonoService.findBonosByMarca(marca) );
    }

    @GetMapping("/marcaDisp")
    public ResponseEntity<?> getBonosDispoByMarca(@RequestParam("marca") String marca){
        return ResponseEntity.ok( bonoService.findBonosDispoByMarca(marca) );
    }

    @PostMapping("/")
    public ResponseEntity<?> addNewBono(@RequestBody AddNewBonoDTO bonoDTO){
        return ResponseEntity.ok(bonoService.createNewBono(bonoDTO));
    }
}
