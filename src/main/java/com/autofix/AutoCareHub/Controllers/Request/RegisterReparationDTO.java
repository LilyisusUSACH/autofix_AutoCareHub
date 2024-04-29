package com.autofix.AutoCareHub.Controllers.Request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterReparationDTO {

    @Min(0)
    @Max(11)
    private int typeRep;

    @NotBlank
    private String patente;

    @Nullable
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate fechaIngreso;

    @Nullable
    @JsonFormat(pattern="HH:mm:ss")
    private LocalTime horaIngreso;

    @Nullable
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate fechaSalida;

    @Nullable
    @JsonFormat(pattern="HH:mm:ss")
    private LocalTime horaSalida;

    @Nullable
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate fechaRetiro;

    @Nullable
    @JsonFormat(pattern="HH:mm:ss")
    private LocalTime horaRetiro;

}
