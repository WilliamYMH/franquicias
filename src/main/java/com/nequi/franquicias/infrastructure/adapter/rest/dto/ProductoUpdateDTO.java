package com.nequi.franquicias.infrastructure.adapter.rest.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para actualización parcial de productos.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductoUpdateDTO {
    
    @Null(message = "No se permite actualizar el ID")
    private String id;
    
    private String nombre;
    
    @Min(value = 0, message = "El stock del producto no puede ser negativo")
    private Integer stock;
    
    @Null(message = "No se permite actualizar el ID de la sucursal")
    private String sucursalId;
}
