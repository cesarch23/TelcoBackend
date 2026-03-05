package com.telco.venta.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.math.BigDecimal;

@Builder
@NoArgsConstructor
public class CreateSaleRequest {

    @NotBlank(message = "DNI cliente no puede esta vacio")
    @Pattern(regexp = "^\\d{8}$|^\\d{11}$", message = "DNI o Num. de carnet de extranjeria debe tener 8 u 11 digitos")
    private String dniCliente;

    @NotBlank(message = "Nombre cliente no puede ser blanco")
    private String nombreCliente;

    @NotBlank(message = "Telefono cliente no puede esta vacio")
    @Pattern(regexp = "^\\d{9}$", message = "Telefono debe tener 9 digitos")
    private String telefonoCliente;

    @NotBlank(message = "Direccion cliente no puede esta vacio")
    private String direccionCliente;

    private String planActual;

    @NotBlank(message = "Plan nuevo no puede esta vacio")
    private String planNuevo;

    @NotBlank(message = "Codigo llamada no puede esta vacio")
    private String codigoLlamada;

    @NotBlank(message = "Producto no puede esta vacio")
    private String producto;

    @NotNull(message = "Monto no puede esta vacio")
    @DecimalMin(value = "0.01", message = "Monto debe ser mayor que 0")
    private BigDecimal monto;

    public CreateSaleRequest(String dniCliente, String nombreCliente, String telefonoCliente, String direccionCliente, String planActual, String planNuevo, String codigoLlamada, String producto, BigDecimal monto) {
        this.dniCliente = dniCliente;
        this.nombreCliente = nombreCliente;
        this.telefonoCliente = telefonoCliente;
        this.direccionCliente = direccionCliente;
        this.planActual = planActual;
        this.planNuevo = planNuevo;
        this.codigoLlamada = codigoLlamada;
        this.producto = producto;
        this.monto = monto;
    }

    public String getDniCliente() {
        return dniCliente;
    }

    public void setDniCliente(String dniCliente) {
        this.dniCliente = dniCliente;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getTelefonoCliente() {
        return telefonoCliente;
    }

    public void setTelefonoCliente(String telefonoCliente) {
        this.telefonoCliente = telefonoCliente;
    }

    public String getDireccionCliente() {
        return direccionCliente;
    }

    public void setDireccionCliente(String direccionCliente) {
        this.direccionCliente = direccionCliente;
    }

    public String getPlanActual() {
        return planActual;
    }

    public void setPlanActual(String planActual) {
        this.planActual = planActual;
    }

    public String getPlanNuevo() {
        return planNuevo;
    }

    public void setPlanNuevo(String planNuevo) {
        this.planNuevo = planNuevo;
    }

    public String getCodigoLlamada() {
        return codigoLlamada;
    }

    public void setCodigoLlamada(String codigoLlamada) {
        this.codigoLlamada = codigoLlamada;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }
}
