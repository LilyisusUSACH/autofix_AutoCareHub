package com.autofix.AutoCareHub.Entities;

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

    @ManyToOne()
    @JoinColumn(name = "vehiculo_id")
    private VehicleEntity vehicle;

}
