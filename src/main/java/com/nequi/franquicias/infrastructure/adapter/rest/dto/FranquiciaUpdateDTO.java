package com.nequi.franquicias.infrastructure.adapter.rest.dto;

import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para actualización parcial de franquicias.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FranquiciaUpdateDTO {
    
    @Null(message = "No se permite actualizar el ID")
    private String id;
    
    private String nombre;
}
