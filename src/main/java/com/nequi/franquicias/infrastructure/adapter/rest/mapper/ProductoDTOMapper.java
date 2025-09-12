package com.nequi.franquicias.infrastructure.adapter.rest.mapper;

import com.nequi.franquicias.domain.model.Producto;
import com.nequi.franquicias.infrastructure.adapter.rest.dto.ProductoDTO;
import org.springframework.stereotype.Component;

/**
 * Mapeador para convertir entre entidades de dominio y DTOs de productos.
 */
@Component
public class ProductoDTOMapper {
    
    /**
     * Convierte una entidad de dominio a un DTO.
     *
     * @param producto entidad de dominio
     * @return DTO
     */
    public ProductoDTO toDTO(Producto producto) {
        return ProductoDTO.builder()
                .id(producto.getId())
                .nombre(producto.getNombre())
                .stock(producto.getStock())
                .build();
    }
    
    /**
     * Convierte un DTO a una entidad de dominio.
     *
     * @param dto DTO
     * @return entidad de dominio
     */
    public Producto toDomain(ProductoDTO dto) {
        return Producto.builder()
                .id(dto.getId())
                .nombre(dto.getNombre())
                .stock(dto.getStock())
                .build();
    }
}
