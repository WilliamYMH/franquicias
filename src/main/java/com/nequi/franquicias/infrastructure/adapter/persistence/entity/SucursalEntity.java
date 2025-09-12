package com.nequi.franquicias.infrastructure.adapter.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * Entidad de persistencia para sucursales.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("sucursales")
public class SucursalEntity {
    
    @Id
    private Long id;
    
    @Column("nombre")
    private String nombre;
    
    @Column("franquicia_id")
    private Long franquiciaId;
}
