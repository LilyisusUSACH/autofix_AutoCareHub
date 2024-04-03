package com.autofix.AutoCareHub.Controllers;

import com.autofix.AutoCareHub.Services.ReceiptService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/receipt")
@CrossOrigin("*")
public class ReceiptController {
    @Autowired
    ReceiptService receiptService;
    // TODO: corregir los null cuando algo no existe
    @GetMapping("/")
    public ResponseEntity<?> getAllReceipts(){
        return ResponseEntity.ok( receiptService.findAllReceipt() );
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findReceiptById(@PathVariable Long id){
        return ResponseEntity.ok( receiptService.findReceiptById(id) );
    }

    @GetMapping("/patente")
    public ResponseEntity<?> findReceiptsByPatente(@RequestParam("patente") String patente){
        return ResponseEntity.ok( receiptService.findAllReceiptsByPatente(patente) );
    }

    @GetMapping("/patente/ultimo")
    public ResponseEntity<?> findReceiptByPatente(@RequestParam("patente") String patente){
        return ResponseEntity.ok( receiptService.findReceiptByPatente(patente) );
    }

    @GetMapping("/patente/nopago")
    public ResponseEntity<?> findUnpaidReceiptByPatente(@RequestParam("patente") String patente){
        return ResponseEntity.ok( receiptService.findReceiptUnpaidByPatente(patente) );
    }

    @PostMapping("/patente")
    public ResponseEntity<?> calcularTotal(@RequestParam("patente") String patente, @RequestParam("bono") Boolean applyBono ){
        return ResponseEntity.ok( receiptService.calculateAmountByPatente(patente, applyBono) );
    }

}
