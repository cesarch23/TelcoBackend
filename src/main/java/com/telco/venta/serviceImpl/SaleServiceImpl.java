package com.telco.venta.serviceImpl;

import com.telco.venta.dto.CreateSaleRequest;
import com.telco.venta.dto.RejectSaleRequest;
import com.telco.venta.dto.SaleResponse;
import com.telco.venta.dto.SalesFilterRequest;
import com.telco.venta.entity.Sale;
import com.telco.venta.entity.User;
import com.telco.venta.enums.SaleStatus;
import com.telco.venta.exception.BusinessException;
import com.telco.venta.exception.enums.BusinessExceptionReason;
import com.telco.venta.repository.SaleRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class SaleServiceImpl {
    private final SaleRepository saleRepository;
    private final UserServiceImpl userService;

    public SaleServiceImpl(SaleRepository saleRepository, UserServiceImpl userService) {
        this.saleRepository = saleRepository;
        this.userService = userService;
    }

    @Transactional
    public SaleResponse createSale(CreateSaleRequest request, Long agenteId) {
        // Validate codigo_llamada unique
        if (saleRepository.findByCodigoLlamada( request.getCodigoLlamada() ).isPresent()) {
            throw new BusinessException( BusinessExceptionReason.ENTITY_EXITS,"Venta con el codigo de llamad"+request.getCodigoLlamada());
        }

        Sale sale = Sale.builder()
                .agenteId(agenteId)
                .dniCliente(request.getDniCliente())
                .nombreCliente(request.getNombreCliente())
                .telefonoCliente(request.getTelefonoCliente())
                .direccionCliente(request.getDireccionCliente())
                .planActual(request.getPlanActual())
                .planNuevo(request.getPlanNuevo())
                .codigoLlamada(request.getCodigoLlamada())
                .producto(request.getProducto())
                .monto(request.getMonto())
                .estado(SaleStatus.PENDIENTE)
                .build();

        Sale savedSale = saleRepository.save(sale);
        return mapToResponse(savedSale);
    }

    public List<SaleResponse> getMyVentas(Long agenteId, SalesFilterRequest filter) {
        List<Sale> sales = querySalesByAgenteFilter(agenteId, filter);

        return sales.stream()
                .map(this::mapToResponse)
                .toList();
    }

    public List<SaleResponse> getPendingVentas() {
        List<Sale> sales = saleRepository.findByEstado(SaleStatus.PENDIENTE);

        return sales.stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Transactional
    public SaleResponse approveSale(Long saleId) {
        Sale sale = saleRepository.findById(saleId)
                .orElseThrow(() -> new BusinessException( BusinessExceptionReason.ENTITY_NOT_FOUND, "La venta con el id: " + saleId));

        if (sale.getEstado() != SaleStatus.PENDIENTE) {
            throw new BusinessException( BusinessExceptionReason.ENTITY_CONFICT, " Solo las ventas con estado PENDIENTE pueden ser aprovadas");
        }

        sale.setEstado(SaleStatus.APROBADA);
        sale.setFechaValidacion(LocalDate.now());

        Sale updatedSale = saleRepository.save(sale);
        return mapToResponse(updatedSale);
    }

    @Transactional
    public SaleResponse rejectSale(Long saleId, RejectSaleRequest request) {
        Sale sale = saleRepository.findById(saleId)
                .orElseThrow(() -> new BusinessException( BusinessExceptionReason.ENTITY_NOT_FOUND, "La venta con el id: " + saleId));

        if (sale.getEstado() != SaleStatus.PENDIENTE) {
            throw new BusinessException( BusinessExceptionReason.ENTITY_CONFICT, "Solo las ventas con estado PENDIENTE pueden ser rechazadas");
        }

        sale.setEstado(SaleStatus.RECHAZADA);
        sale.setMotivoRechazo(request.getMotivo());
        sale.setFechaValidacion(LocalDate.now());

        Sale updatedSale = saleRepository.save(sale);
        return mapToResponse(updatedSale);
    }

    public List<SaleResponse> getSupervisorVentas(Long supervisorId, SalesFilterRequest filter) {
        List<Sale> sales = querySalesBySupervisorFilter(supervisorId, filter);
        return sales.stream()
                .map(this::mapToResponse)
                .toList();
    }

    //
    private List<Sale> querySalesByAgenteFilter(Long agenteId, SalesFilterRequest filter) {
        if (filter == null) {
            return saleRepository.findByAgenteId(agenteId);
        }

        boolean hasEstado = filter.getEstado() != null && !filter.getEstado().isEmpty();
        boolean hasDateRange = filter.getDesde() != null || filter.getHasta() != null;

        LocalDate desde = filter.getDesde() != null ? filter.getDesde() : LocalDate.of(2000, 1, 1);
        LocalDate hasta = filter.getHasta() != null ? filter.getHasta() : LocalDate.of(2099, 12, 31);

        if (hasEstado && hasDateRange) {
            return saleRepository.findByAgenteIdEstadoAndDateRange(agenteId, SaleStatus.valueOf(filter.getEstado()), desde, hasta);
        } else if (hasEstado) {
            return saleRepository.findByAgenteIdAndEstado(agenteId, SaleStatus.valueOf(filter.getEstado()));
        } else if (hasDateRange) {
            return saleRepository.findByAgenteIdAndDateRange(agenteId, desde, hasta);
        }

        return saleRepository.findByAgenteId(agenteId);
    }

    private List<Sale> querySalesBySupervisorFilter(Long supervisorId, SalesFilterRequest filter) {
        if (filter == null) {
            return saleRepository.findBySupervisorId(supervisorId);
        }

        boolean hasEstado = filter.getEstado() != null && !filter.getEstado().isEmpty();
        boolean hasAgenteId = filter.getAgenteId() != null;
        boolean hasDateRange = filter.getDesde() != null || filter.getHasta() != null;

        LocalDate desde = filter.getDesde() != null ? filter.getDesde() : LocalDate.of(2000, 1, 1);
        LocalDate hasta = filter.getHasta() != null ? filter.getHasta() : LocalDate.of(2099, 12, 31);

        if (hasAgenteId && hasDateRange) {
            return saleRepository.findBySupervisorIdAgenteIdAndDateRange(supervisorId, filter.getAgenteId(), desde, hasta);
        } else if (hasAgenteId) {
            return saleRepository.findBySupervisorIdAndAgenteId(supervisorId, filter.getAgenteId());
        } else if (hasEstado && hasDateRange) {
            return saleRepository.findBySupervisorIdEstadoAndDateRange(supervisorId, SaleStatus.valueOf(filter.getEstado()), desde, hasta);
        } else if (hasEstado) {
            return saleRepository.findBySupervisorIdAndEstado(supervisorId, SaleStatus.valueOf(filter.getEstado()));
        } else if (hasDateRange) {
            return saleRepository.findBySupervisorIdAndDateRange(supervisorId, desde, hasta);
        }

        return saleRepository.findBySupervisorId(supervisorId);
    }

    private SaleResponse mapToResponse(Sale sale) {
        return SaleResponse.builder()
                .id(sale.getId())
                .agenteId(sale.getAgenteId())
                .dniCliente(sale.getDniCliente())
                .nombreCliente(sale.getNombreCliente())
                .telefonoCliente(sale.getTelefonoCliente())
                .direccionCliente(sale.getDireccionCliente())
                .planActual(sale.getPlanActual())
                .planNuevo(sale.getPlanNuevo())
                .codigoLlamada(sale.getCodigoLlamada())
                .producto(sale.getProducto())
                .monto(sale.getMonto())
                .estado(sale.getEstado())
                .motivoRechazo(sale.getMotivoRechazo())
                .fechaRegistro(sale.getFechaRegistro())
                .fechaValidacion(sale.getFechaValidacion())
                .createdAt(sale.getCreatedAt())
                .updatedAt(sale.getUpdatedAt())
                .build();
    }
}
