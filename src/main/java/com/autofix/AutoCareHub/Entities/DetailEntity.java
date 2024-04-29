package com.autofix.AutoCareHub.Entities;

import com.autofix.AutoCareHub.Enums.EDiscountsRecharges;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "detalles")
public class DetailEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private EDiscountsRecharges description;

    private int value;

    private float percent;

    @ManyToOne
    @JoinColumn(name = "receipt_id")
    @JsonIgnore
    private ReceiptEntity receipt;
}
