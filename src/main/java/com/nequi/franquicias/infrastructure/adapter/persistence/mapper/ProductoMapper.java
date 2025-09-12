package com.nequi.franquicias.infrastructure.adapter.persistence.mapper;

import com.nequi.franquicias.domain.model.Producto;
import com.nequi.franquicias.infrastructure.adapter.persistence.entity.ProductoEntity;
import org.springframework.stereotype.Component;

/**
 * Mapeador para convertir entre entidades de dominio y entidades de persistencia de productos.
 */
@Component
public class ProductoMapper {
    
    /**
     * Convierte una entidad de dominio a una entidad de persistencia.
     *
     * @param producto entidad de dominio
     * @param sucursalId ID de la sucursal a la que pertenece el producto
     * @return entidad de persistencia
     */
    public ProductoEntity toEntity(Producto producto, Long sucursalId) {
        return ProductoEntity.builder()
                .id(producto.getId())
                .nombre(producto.getNombre())
                .stock(producto.getStock())
                .sucursalId(sucursalId)
                .build();
    }
    
    /**
     * Convierte una entidad de persistencia a una entidad de dominio.
     *
     * @param entity entidad de persistencia
     * @return entidad de dominio
     */
    public Producto toDomain(ProductoEntity entity) {
        return Producto.builder()
                .id(entity.getId())
                .nombre(entity.getNombre())
                .stock(entity.getStock())
                .sucursalId(entity.getSucursalId())
                .build();
    }
}
