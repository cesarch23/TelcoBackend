package com.telco.venta.dto;

import com.telco.venta.enums.SaleStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SalesReportResponse {
    private Map<SaleStatus, Integer> conteosPorEstado;
    private BigDecimal montoTotalAprobadas;
    private Map<LocalDate,DailySalesData> ventasPorDia;
}
