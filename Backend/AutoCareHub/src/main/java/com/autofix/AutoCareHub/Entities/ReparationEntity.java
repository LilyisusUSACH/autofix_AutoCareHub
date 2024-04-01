package com.autofix.AutoCareHub.Entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
// @Builder ver si usar
@Builder
@Entity
@Table(name = "reparacion")
public class ReparationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private int typeRep;

    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate fechaIngreso;

    @JsonFormat(pattern="HH:mm:ss")
    private LocalTime horaIngreso;

    // private Boolean reparado;

    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate fechaSalida;

    @JsonFormat(pattern="HH:mm:ss")
    private LocalTime horaSalida;

    // private Boolean retirado;

    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate fechaRetiro;

    @JsonFormat(pattern="HH:mm:ss")
    private LocalTime horaRetiro;

    private float montoTotal;

    @ManyToOne()
    @JoinColumn(name = "boleta")
    private ReceiptEntity receipt;

}
