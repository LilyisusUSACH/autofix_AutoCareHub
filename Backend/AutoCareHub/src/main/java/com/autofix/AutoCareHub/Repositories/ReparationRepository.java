package com.autofix.AutoCareHub.Repositories;

import com.autofix.AutoCareHub.Entities.ReceiptEntity;
import com.autofix.AutoCareHub.Entities.ReparationEntity;
import com.autofix.AutoCareHub.Entities.VehicleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReparationRepository extends JpaRepository<ReparationEntity, Long> {

    Optional<ReparationEntity> findByReceipt_PatentePatente(String patente);

    ArrayList<ReparationEntity> findAllByFechaSalidaIsNull();

    ArrayList<ReparationEntity> findAllByFechaSalidaIsNullOrderByFechaIngresoDescHoraIngresoDesc();


    Optional<ReparationEntity> findFirstByReceiptOrderByFechaIngresoDesc(ReceiptEntity receipt);

    List<ReparationEntity> findAllByReceipt_PatentePatente(String patente);

    List<ReparationEntity> findAllByFechaIngresoAndReceipt(LocalDate fecha, ReceiptEntity receipt);

    int countByFechaIngresoBetweenAndReceipt_Patente(LocalDate inicio, LocalDate termino, VehicleEntity patente);

    void deleteById(Long id);

    /* TODO: Implementar las querys de los R

    R2 :
    SELECT r.type_rep as tipo_de_rep , count(*) as cantidad, v.car_type as tipo_vehiculo, sum(b.costo_total) as monto_total  FROM reparacion as r, boleta as b, vehiculo as v
	WHERE r.boleta = b.id and b.patente_vehiculo = v.id
		GROUP BY r.type_rep, v.car_type
			ORDER BY monto_total DESC

	R3:
        SELECT avg(tiempo.dif) as promedio_reparacion, v.marca
    FROM(
        SELECT (fecha_salida+hora_salida)-(fecha_ingreso+hora_ingreso) as dif, r.id
        FROM reparacion as r
        WHERE r.fecha_salida is not null
        ) as tiempo, reparacion as r, boleta as b, vehiculo as v
    WHERE tiempo.id = r.id and r.boleta = b.id and b.patente_vehiculo = v.id
    GROUP BY v.marca

    R4:
        SELECT r.type_rep, count(*), v.motor_type as tipo_motor, sum(r.monto_total) as monto_total FROM reparacion as r, boleta as b, vehiculo as v
	WHERE r.boleta = b.id and b.patente_vehiculo = v.id
		GROUP BY r.type_rep, v.motor_type
			ORDER BY monto_total DESC

     */

}
