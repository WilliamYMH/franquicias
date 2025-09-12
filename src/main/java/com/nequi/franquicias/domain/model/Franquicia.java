package com.nequi.franquicias.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * Entidad de dominio que representa una franquicia.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Franquicia {
    private String id;
    private String nombre;
    
    @Builder.Default
    private List<Sucursal> sucursales = new ArrayList<>();
}
