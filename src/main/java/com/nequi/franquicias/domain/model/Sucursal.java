package com.nequi.franquicias.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * Entidad de dominio que representa una sucursal de una franquicia.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Sucursal {
    private Long id;
    private String nombre;
    private Long franquiciaId;
    
    @Builder.Default
    private List<Producto> productos = new ArrayList<>();
}
