package com.nequi.franquicias.infrastructure.adapter.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * Entidad de persistencia para productos.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("productos")
public class ProductoEntity {
    
    @Id
    private Long id;
    
    @Column("nombre")
    private String nombre;
    
    @Column("stock")
    private Integer stock;
    
    @Column("sucursal_id")
    private Long sucursalId;
}
