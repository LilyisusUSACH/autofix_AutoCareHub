package com.autofix.AutoCareHub.Controllers;

import com.autofix.AutoCareHub.Controllers.Request.RegisterVehicleDTO;
import com.autofix.AutoCareHub.Entities.VehicleEntity;
import com.autofix.AutoCareHub.Services.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// TODO: RECORDAR EN FRONT PASAR TODO EN MINUSCULAS
// o arreglarlo aca
@RestController
@RequestMapping("/api/vehicle")
@CrossOrigin("*")
public class VehicleController {
    @Autowired
    VehicleService vehicleService;

    @GetMapping("/")
    public ResponseEntity<?> listVehicles(){
        return ResponseEntity.ok(vehicleService.getAllVehicles());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findVehicleById(@PathVariable Long id){
        return ResponseEntity.ok(vehicleService.getVehicleById(id));
    }

    @GetMapping("/patente")
    public ResponseEntity<?> findVehicleByPatente(@RequestParam("patente") String patente){
        return ResponseEntity.ok(vehicleService.getVehicleByPatente(patente));
    }

    @GetMapping("/marca")
    public ResponseEntity<?> findVehiclesByMarca(@RequestParam("marca") String marca){
        return ResponseEntity.ok(vehicleService.getVehiclesByMarca(marca));
    }

    @PostMapping("/")
    public ResponseEntity<?> registerVehicle(@RequestBody RegisterVehicleDTO vehicleDTO){
        VehicleEntity vehicle = vehicleService.registerVehicle(vehicleDTO);
        if(vehicle == null){
            return new ResponseEntity<>(
                    "Vehicle is already registered, only need to update Km",
                    HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(vehicle);
    }



}
