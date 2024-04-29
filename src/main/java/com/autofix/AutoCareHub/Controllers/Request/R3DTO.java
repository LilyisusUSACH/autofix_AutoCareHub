package com.autofix.AutoCareHub.Controllers.Request;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class R3DTO {
    String promedio_reparacion;
    String marca;
}
