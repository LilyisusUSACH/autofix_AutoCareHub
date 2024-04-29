package com.autofix.AutoCareHub.Controllers.Request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddNewBonoDTO {

    @NotBlank
    private String marca;

    @Min(0)
    private int amount;
}
