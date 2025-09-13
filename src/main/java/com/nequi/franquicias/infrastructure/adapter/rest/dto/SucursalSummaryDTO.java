package com.nequi.franquicias.infrastructure.adapter.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO resumido de sucursal: solo id y nombre.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SucursalSummaryDTO {
    private Long id;
    private String nombre;
}
