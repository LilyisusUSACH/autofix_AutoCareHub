package com.autofix.AutoCareHub.Controllers;

import com.autofix.AutoCareHub.Controllers.Request.RegisterVehicleDTO;
import com.autofix.AutoCareHub.Entities.ReceiptEntity;
import com.autofix.AutoCareHub.Entities.ReparationEntity;
import com.autofix.AutoCareHub.Entities.VehicleEntity;
import com.autofix.AutoCareHub.Services.ReceiptService;
import com.autofix.AutoCareHub.Services.RepairService;
import com.autofix.AutoCareHub.Services.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

import static java.time.temporal.ChronoUnit.DAYS;

// TODO: RECORDAR EN FRONT PASAR TODO EN MINUSCULAS
// o arreglarlo aca
@RestController
@RequestMapping("/api/vehicle")
@CrossOrigin("*")
public class VehicleController {
    @Autowired
    VehicleService vehicleService;

    @Autowired
    ReceiptService receiptService;

    @Autowired
    RepairService repairService;

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

    @GetMapping("/patente/status")
    public ResponseEntity<?> getStatusVehicleByPatente(@RequestParam("patente") String patente){
        vehicleService.getVehicleByPatente(patente);
        Optional<VehicleEntity> optVehicle = vehicleService.getVehicleByPatente(patente);
        Map<String, Object> estadoVehiculo = new HashMap<>();
        estadoVehiculo.put("patente", patente);

        if (optVehicle.isPresent()){
            VehicleEntity vehicle = optVehicle.get();
            Optional<ReceiptEntity> optionalReceipt = receiptService.findReceiptUnpaidByPatente(patente);
            if (optionalReceipt.isPresent()){
                ReceiptEntity receipt = optionalReceipt.get();
                ArrayList<ReparationEntity> reparations = new ArrayList<>(receipt.getReparaciones());
                if(reparations.stream().anyMatch(reparation ->  reparation.getFechaSalida() == null)){
                    estadoVehiculo.put("estado", "Su vehiculo con patente " + patente.toUpperCase() + " se encuentra actualmente en reparaciones, sera notificado cuando estas sean concluidas"); // hacer con unretired
                    return ResponseEntity.ok(estadoVehiculo);
                }
                reparations.sort(Comparator.comparing(ReparationEntity::getFechaSalida).reversed());
                if(reparations.get(0).getFechaSalida().isEqual(LocalDate.now()) ||   reparations.get(0).getFechaSalida().isAfter(LocalDate.now())){
                    estadoVehiculo.put("estado", "El vehiculo se encuentra listo para ser retirado"); // hacer con unretired
                    return ResponseEntity.ok(estadoVehiculo);
                }else{
                    int diference = (int) DAYS.between(reparations.get(0).getFechaSalida(), LocalDate.now());
                    estadoVehiculo.put("estado", "El vehiculo esta esperando en el taller hace " + String.valueOf(diference) + " dias, cuidado con los intereses"); // hacer con unretired
                    return ResponseEntity.ok(estadoVehiculo);
                }
            }
            estadoVehiculo.put("estado", "El vehiculo no se encuentra presente"); // hacer con unretired
            return ResponseEntity.ok(estadoVehiculo);
        }
        return ResponseEntity.notFound().build();
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
