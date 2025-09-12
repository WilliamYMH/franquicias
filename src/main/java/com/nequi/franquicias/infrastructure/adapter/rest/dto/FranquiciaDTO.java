package com.nequi.franquicias.infrastructure.adapter.rest.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * DTO para transferir información de franquicias entre la API y los clientes.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FranquiciaDTO {
    
    private Long id;
    
    @NotBlank(message = "El nombre de la franquicia no puede estar vacío")
    private String nombre;
    
    @Builder.Default
    private List<SucursalDTO> sucursales = new ArrayList<>();
}
