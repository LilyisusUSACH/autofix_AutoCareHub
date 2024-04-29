package com.autofix.AutoCareHub.Controllers.Request;

import com.autofix.AutoCareHub.Enums.ECarType;
import com.autofix.AutoCareHub.Enums.EMotorType;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterVehicleDTO {

    @NotBlank
    private String patente;

    @NotBlank
    private String marca;

    @NotBlank
    private String modelo;

    @NotBlank
    private ECarType carType;

    @NotBlank
    private EMotorType motorType;

    @Min(0)
    private int nAsientos;

    @Min(0)
    private int kmRecorridos;

    @Min(0)
    private int fabricationYear;
}
