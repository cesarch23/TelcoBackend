package com.telco.venta.serviceImpl;

import com.telco.venta.dto.DailySalesData;
import com.telco.venta.dto.SalesReportResponse;
import com.telco.venta.entity.Sale;
import com.telco.venta.enums.SaleStatus;
import com.telco.venta.repository.SaleRepository;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReportServImpl {
    private final SaleRepository saleRepository;


    public ReportServImpl(SaleRepository saleRepository) {
        this.saleRepository = saleRepository;
    }
    public SalesReportResponse salesReport(LocalDate inicio, LocalDate fin){
        if(inicio == null || fin== null){
            //generar por periodo
        }
        List<Sale> sales = saleRepository.findByDateRange(inicio,fin);
        long countPendiente = sales.stream().filter( sale -> SaleStatus.valueOf("PENDIENTE").equals( sale.getEstado() )).count();
        long countAprobada = sales.stream().filter( sale -> SaleStatus.valueOf("APROBADA").equals( sale.getEstado() )).count();
        long countRechazada = sales.stream().filter( sale -> SaleStatus.valueOf("RECHAZADA").equals( sale.getEstado() )).count();

        Map<SaleStatus, Integer> conteosPorEstado = new HashMap<>();
        conteosPorEstado.put(SaleStatus.PENDIENTE, (int) countPendiente);
        conteosPorEstado.put(SaleStatus.APROBADA, (int) countAprobada);
        conteosPorEstado.put(SaleStatus.RECHAZADA, (int) countRechazada);

        BigDecimal montoTotalAprobadas = sales.stream()
                .filter(sale-> SaleStatus.valueOf("RECHAZADA").equals(sale.getEstado()))
                .map(sale -> sale.getMonto())
                .reduce(BigDecimal.ZERO,(subtotal, monto)-> subtotal.add(monto));

        Map<LocalDate, List<Sale>> saleByDate = sales.stream()
                .collect(Collectors.groupingBy( sale -> sale.getFechaRegistro() ) );

        Map<LocalDate, DailySalesData> ventasPorDia = saleByDate.entrySet()
                .stream()
                .collect(Collectors.toMap(
                        entry -> entry.getKey(),
                        entry -> {

                            List<Sale> dailySales = entry.getValue();

                            int cantidad = dailySales.size();

                            BigDecimal monto = dailySales.stream()
                                    .map(Sale::getMonto)
                                    .reduce(BigDecimal.ZERO, BigDecimal::add);

                            return new DailySalesData(cantidad, monto);
                        }
                ));



        return SalesReportResponse.builder()
                .conteosPorEstado(conteosPorEstado)
                .montoTotalAprobadas(montoTotalAprobadas)
                .ventasPorDia(ventasPorDia)
                .build();
    }

}
