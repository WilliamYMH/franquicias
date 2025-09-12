package com.nequi.franquicias.infrastructure.adapter.rest.dto;

import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para actualización parcial de sucursales.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SucursalUpdateDTO {
    
    @Null(message = "No se permite actualizar el ID")
    private String id;
    
    private String nombre;
    
    @Null(message = "No se permite actualizar el ID de la franquicia")
    private String franquiciaId;
}
