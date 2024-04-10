package com.autofix.AutoCareHub.Controllers;

import com.autofix.AutoCareHub.Controllers.Request.RegisterReparationDTO;
import com.autofix.AutoCareHub.Services.ReceiptService;
import com.autofix.AutoCareHub.Services.RepairService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/repair")
@CrossOrigin("*")
// TODO: recordar cambiar los responseEntity<?> por el tipo especifico
public class RepairController {
    @Autowired
    RepairService repairService;

    @Autowired
    ReceiptService receiptService;

    @GetMapping("/")
    public ResponseEntity<?> getAllReparations(){
        return ResponseEntity.ok( repairService.getAllReparations() );
    }

    @GetMapping("/actives")
    public ResponseEntity<?> getAllActiveReparations(){
        return ResponseEntity.ok(repairService.getAllActiveReparations());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findReparationById(@PathVariable Long id){
        return ResponseEntity.ok( repairService.getReparationById(id) );
    }

    @GetMapping("/patente")
    public ResponseEntity<?> findReparationsByPatente(@RequestParam("patente") String patente){
        return ResponseEntity.ok( repairService.getReparationsByPatente(patente) );
    }

    @PostMapping("/")
    public ResponseEntity<?> registerReparation(@RequestBody RegisterReparationDTO reparationDTO){
        return ResponseEntity.ok( receiptService.registerReparation(reparationDTO) );
    }

    // @PutMapping("/") reparation complete
    @PutMapping("/{id}")
    public ResponseEntity<?> completeReparation(@PathVariable Long id){
        return ResponseEntity.ok( repairService.reparationComplete(id) );
    }

}
