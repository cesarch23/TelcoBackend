package com.telco.venta.dto;
import com.telco.venta.enums.SaleStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaleResponse {
    private Long id;
    private Long agenteId;
    private String dniCliente;
    private String nombreCliente;
    private String telefonoCliente;
    private String direccionCliente;
    private String planActual;
    private String planNuevo;
    private String codigoLlamada;
    private String producto;
    private BigDecimal monto;
    private SaleStatus estado;
    private String motivoRechazo;
    private LocalDate fechaRegistro;
    private LocalDate fechaValidacion;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}