package com.autofix.AutoCareHub.Entities;

import com.autofix.AutoCareHub.Enums.ECarType;
import com.autofix.AutoCareHub.Enums.EMotorType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.Year;


@Data
@AllArgsConstructor
@NoArgsConstructor
// @Builder ver si usar
@Builder
@Entity
@Table(name = "vehiculo")
public class VehicleEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique=true) // o verificar con service si ya existe
    private String patente;

    private String marca;

    private String modelo;

    private ECarType carType;

    private EMotorType motorType;

    private Year fabricationYear;

    private int nAsientos;

    private int kmRecorridos;
}
