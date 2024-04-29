package com.autofix.AutoCareHub.Controllers.Request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class R1DTO {
    Long gastado_por_vehiculo;
    Long id;
    String patente;
}
