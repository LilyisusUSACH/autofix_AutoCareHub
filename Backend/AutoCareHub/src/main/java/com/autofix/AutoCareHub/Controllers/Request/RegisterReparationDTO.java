package com.autofix.AutoCareHub.Controllers.Request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterReparationDTO {

    @Min(0)
    @Max(11)
    private int typeRep;

    @NotBlank
    private String patente;

}
