package com.nequi.franquicias.infrastructure.adapter.rest.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para transferir información de productos entre la API y los clientes.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductoDTO {
    
    private String id;
    
    @NotBlank(message = "El nombre del producto no puede estar vacío")
    private String nombre;
    
    @NotNull(message = "El stock del producto no puede ser nulo")
    @Min(value = 0, message = "El stock del producto no puede ser negativo")
    private Integer stock;
    
    private String sucursalId;
}
