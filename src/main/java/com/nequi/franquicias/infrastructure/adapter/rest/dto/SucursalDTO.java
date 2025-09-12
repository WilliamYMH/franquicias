package com.nequi.franquicias.infrastructure.adapter.rest.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * DTO para transferir información de sucursales entre la API y los clientes.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SucursalDTO {
    
    private String id;
    
    @NotBlank(message = "El nombre de la sucursal no puede estar vacío")
    private String nombre;
    
    private String franquiciaId;
    
    @Builder.Default
    private List<ProductoDTO> productos = new ArrayList<>();
}
