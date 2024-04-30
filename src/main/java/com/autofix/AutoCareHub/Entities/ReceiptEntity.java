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
    @Builder.Default
    private List<ReparationEntity> reparaciones = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "bono_id")
    private BonoEntity bono;
    //@Embeddable
    //@CollectionTable(name="detalles")
    //private ArrayList<Integer> detail = new ArrayList<Integer>();

    @OneToMany(mappedBy = "receipt",cascade = CascadeType.MERGE)
    @Builder.Default
    private List<DetailEntity> details = new ArrayList<>();

    private Boolean retirado;

    private Boolean pagado;

    private int costoTotal;


}
