package com.autofix.AutoCareHub.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
// @Builder ver si usar
@Builder
@Entity
@Table(name = "boleta")
public class ReceiptEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "patente_vehiculo")
    private VehicleEntity patente;

    @OneToMany(mappedBy = "receipt")
    private List<ReparationEntity> reparaciones = new ArrayList<>();

    //@Embeddable
    //@CollectionTable(name="detalles")
    //private ArrayList<Integer> detail = new ArrayList<Integer>();

    private Boolean retirado;

    private Boolean pagado;

    private int costoTotal;
}
