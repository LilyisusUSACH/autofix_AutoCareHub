package com.autofix.AutoCareHub.Controllers;

import com.autofix.AutoCareHub.Entities.ReceiptEntity;
import com.autofix.AutoCareHub.Services.ReceiptService;
import org.apache.coyote.Response;
import org.hibernate.NonUniqueResultException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
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
    public ResponseEntity<?> calcularTotal(@RequestParam("patente") String patente,
                                           @RequestParam("bono") Boolean applyBono,
                                           @RequestParam(name = "bono_id", required = false) Long bono_id){
        try{
            return ResponseEntity.ok( receiptService.calculateAmountByPatente(patente, applyBono, bono_id) );
        }catch (Exception e){
            if (e.getMessage().equals("Calculado con anterioridad")){
                return ResponseEntity.badRequest().body("Error, ya fue calculado");
            }
            return ResponseEntity.badRequest().body(e.getMessage());

        }
    }

   // calculateAmountByReceiptId
   @PostMapping("/id")
   public ResponseEntity<?> calcularTotalById(@RequestParam("id") Long id,
                                          @RequestParam("bono") Boolean applyBono,
                                          @RequestParam(name = "bono_id", required = false) Long bono_id){
       try{
           return ResponseEntity.ok( receiptService.calculateAmountByReceiptId(id, applyBono, bono_id) );
       }catch (Exception e){
           return ResponseEntity.badRequest().body(e.getMessage());
       }
   }

   @PutMapping("/")
    public ResponseEntity<?> payRecipt(@RequestParam Long id){
        return new ResponseEntity<>(receiptService.paidReceipt(id), HttpStatus.ACCEPTED);
   }
}
