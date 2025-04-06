package com.aejimenez19.ControlCredito.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PrestamoResumenDto {
    private UUID id;
    private BigDecimal monto;
    private LocalDate fechaDesembolso;
    private BigDecimal tasaInteres;
    private String estado;
    private BigDecimal saldoRestante;
}
