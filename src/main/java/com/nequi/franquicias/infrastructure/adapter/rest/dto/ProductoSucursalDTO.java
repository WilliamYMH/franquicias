package com.nequi.franquicias.infrastructure.adapter.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para transferir información de productos con su sucursal asociada.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductoSucursalDTO {
    private ProductoDTO producto;
    private SucursalDTO sucursal;
}
