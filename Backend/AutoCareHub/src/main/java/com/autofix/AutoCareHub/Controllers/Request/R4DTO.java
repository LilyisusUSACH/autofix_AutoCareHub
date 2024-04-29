package com.autofix.AutoCareHub.Controllers.Request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class R4DTO {
    int type_rep;
    Long count;
    int tipo_motor;
    Long monto_total;
}
