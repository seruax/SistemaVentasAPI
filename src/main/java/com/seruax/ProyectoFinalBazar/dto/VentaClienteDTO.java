package com.seruax.ProyectoFinalBazar.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class VentaClienteDTO {

    private Long codigoVenta;
    private Double totalVenta;
    private int cantidadProductos;
    private String nombreCliente;
    private String apellidoCliente;
}
