package com.autofix.AutoCareHub.Repositories;

import com.autofix.AutoCareHub.Controllers.Request.R2DTO;
import com.autofix.AutoCareHub.Controllers.Request.R3DTO;
import com.autofix.AutoCareHub.Controllers.Request.R4DTO;
import com.autofix.AutoCareHub.Entities.VehicleEntity;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ReportesRepository{

    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<R2DTO> findR2(){
        String queryR2 = "SELECT r.type_rep as tipo_de_rep , count(*) as cantidad, v.car_type as tipo_vehiculo, sum(r.monto_total) as monto_total FROM reparacion as r, boleta as b, vehiculo as v" +
                " WHERE r.boleta = b.id and b.patente_vehiculo = v.id" +
                " GROUP BY r.type_rep, v.car_type" +
                " ORDER BY monto_total DESC";
        return jdbcTemplate.query(queryR2, (rs, rowNum) -> new R2DTO(rs.getInt("tipo_de_rep"), rs.getInt("cantidad"),
                rs.getInt("tipo_vehiculo"), rs.getInt("monto_total")));
    };

    public List<R3DTO> findR3(){
        String queryr3 = "    SELECT avg(tiempo.dif) as promedio_reparacion, v.marca" +
                " FROM(" +
                " SELECT (fecha_salida+hora_salida)-(fecha_ingreso+hora_ingreso) as dif, r.id" +
                " FROM reparacion as r" +
                "   WHERE r.fecha_salida is not null" +
                "   ) as tiempo, reparacion as r, boleta as b, vehiculo as v" +
                "  WHERE tiempo.id = r.id and r.boleta = b.id and b.patente_vehiculo = v.id" +
                "  GROUP BY v.marca";
        return jdbcTemplate.query(queryr3, (rs, rowNum) -> new R3DTO( rs.getString("promedio_reparacion"), rs.getString("marca")));
    }

    public List<R4DTO> findR4(){
        String queryR4 = "SELECT r.type_rep, count(*), v.motor_type as tipo_motor, sum(r.monto_total) as monto_total FROM reparacion as r, boleta as b, vehiculo as v" +
                " WHERE r.boleta = b.id and b.patente_vehiculo = v.id" +
                " GROUP BY r.type_rep, v.motor_type" +
                " ORDER BY monto_total DESC";
        return jdbcTemplate.query(queryR4, (rs, rowNum) -> new R4DTO(rs.getInt("type_rep"), rs.getLong("count"), rs.getInt("tipo_motor"), rs.getLong("monto_total") ));
    }



    /* TODO: Implementar las querys de los R

    R2 :
    SELECT r.type_rep as tipo_de_rep , count(*) as cantidad, v.car_type as tipo_vehiculo, sum(r.monto_total) as monto_total  FROM reparacion as r, boleta as b, vehiculo as v
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
