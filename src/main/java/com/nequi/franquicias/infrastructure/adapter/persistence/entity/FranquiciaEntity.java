package com.nequi.franquicias.infrastructure.adapter.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * Entidad de persistencia para franquicias.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("franquicias")
public class FranquiciaEntity {
    
    @Id
    private String id;
    
    @Column("nombre")
    private String nombre;
}
