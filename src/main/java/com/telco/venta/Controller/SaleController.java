package com.telco.venta.Controller;


import com.telco.venta.dto.CreateSaleRequest;
import com.telco.venta.dto.RejectSaleRequest;
import com.telco.venta.dto.SaleResponse;
import com.telco.venta.dto.SalesFilterRequest;
import com.telco.venta.serviceImpl.SaleServiceImpl;
import com.telco.venta.serviceImpl.UserServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/ventas")
public class SaleController {
    private final SaleServiceImpl saleService;
    private final UserServiceImpl userService;

    public SaleController(SaleServiceImpl saleService, UserServiceImpl userService) {
        this.saleService = saleService;
        this.userService = userService;
    }

    @PostMapping
    @PreAuthorize("hasRole('AGENTE')")
    public ResponseEntity<SaleResponse> createSale(@Valid @RequestBody CreateSaleRequest request) {
        Long agenteId = userService.getCurrentUserId();
        SaleResponse response = saleService.createSale(request, agenteId);
        return ResponseEntity.ok(response);
    }
    /**
     * Obtienee todas las ventas de un agente
     * */
    @GetMapping("/mis-ventas")
    @PreAuthorize("hasRole('AGENTE')")
    public ResponseEntity<List<SaleResponse>> getMyVentas(
            @RequestParam(required = false) String estado,
            @RequestParam(required = false) LocalDate desde,
            @RequestParam(required = false) LocalDate hasta) {
        Long agenteId = userService.getCurrentUserId();
        SalesFilterRequest filter = SalesFilterRequest.builder().estado(estado).desde(desde).hasta(hasta).build();
        List<SaleResponse> response = saleService.getMyVentas(agenteId, filter);
        return ResponseEntity.ok(response);
    }
    /**
     * Obtienee todas las ventas PENDIENTES solo BACKOFFICE
     * */
    @GetMapping("/pendientes")
    @PreAuthorize("hasRole('BACKOFFICE')")
    public ResponseEntity<List<SaleResponse>> getPendingVentas() {
        List<SaleResponse> response = saleService.getPendingVentas();
        return ResponseEntity.ok(response);
    }
    /**
     * Aprobar venta solo BACKOFFICE
     * */
    @PostMapping("/{id}/aprobar")
    @PreAuthorize("hasRole('BACKOFFICE')")
    public ResponseEntity<SaleResponse> approveSale(@PathVariable Long id) {
        SaleResponse response = saleService.approveSale(id);
        return ResponseEntity.ok(response);
    }
    /**
     * Rechazar venta solo BACKOFFICE
     * */
    @PostMapping("/{id}/rechazar")
    @PreAuthorize("hasRole('BACKOFFICE')")
    public ResponseEntity<SaleResponse> rejectSale(
            @PathVariable Long id,
            @Valid @RequestBody RejectSaleRequest request) {
        SaleResponse response = saleService.rejectSale(id, request);
        return ResponseEntity.ok(response);
    }
    /**
     * obtener ventas de agentes bajo su supervision
     * */
    @GetMapping("/equipo")
    @PreAuthorize("hasRole('SUPERVISOR')")
    public ResponseEntity<List<SaleResponse>> getTeamVentas(
            @RequestParam(required = false) String estado,
            @RequestParam(required = false) Long agenteId,
            @RequestParam(required = false) LocalDate desde,
            @RequestParam(required = false) LocalDate hasta) {
        Long supervisorId = userService.getCurrentUserId();
        SalesFilterRequest filter = SalesFilterRequest.builder()
                .estado(estado)
                .agenteId(agenteId)
                .desde(desde)
                .hasta(hasta)
                .build();
        List<SaleResponse> response = saleService.getSupervisorVentas(supervisorId, filter);
        return ResponseEntity.ok(response);
    }
}
