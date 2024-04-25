package com.autofix.AutoCareHub.Entities;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
// @Builder ver si usar
@Builder
@Table(name = "bono")
public class BonoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String marca;

    private Boolean usado;

    private int amount;

    @OneToOne()
    @JoinColumn(name = "receipt_id")
    @JsonIgnore
    private ReceiptEntity receipt;

    @JsonGetter
    private Long receiptId(){
        if(receipt != null) return receipt.getId();
        return null;
    }

}
