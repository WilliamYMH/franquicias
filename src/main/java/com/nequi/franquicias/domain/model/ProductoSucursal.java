package com.nequi.franquicias.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad de dominio que representa un producto con información de su sucursal.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductoSucursal {
    private Producto producto;
    private Sucursal sucursal;
}
