package com.nequi.franquicias.infrastructure.adapter.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para producto con una sucursal resumida (solo id y nombre).
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductoSucursalSummaryDTO {
    private ProductoDTO producto;
    private SucursalSummaryDTO sucursal;
}
