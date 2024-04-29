package com.autofix.AutoCareHub.Controllers.Request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class R2DTO {
    int tipo_rep;
    int cantidad;
    int tipo_vehiculo;
    int monto_total;
}
