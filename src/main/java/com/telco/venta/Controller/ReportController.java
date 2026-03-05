package com.telco.venta.Controller;

import com.telco.venta.dto.SalesReportResponse;
import com.telco.venta.serviceImpl.ReportServImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/reportes")
public class ReportController {
    private final ReportServImpl reportService;

    public ReportController(ReportServImpl reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/resumen")
    @PreAuthorize("hasAnyRole('ADMIN','SUPERVISOR')")
    public ResponseEntity<SalesReportResponse> getSalesResumen(
            @RequestParam(required = false) LocalDate desde,
            @RequestParam(required = false) LocalDate hasta) {
        SalesReportResponse response = reportService.salesReport(desde, hasta);
        return ResponseEntity.ok(response);
    }
}
