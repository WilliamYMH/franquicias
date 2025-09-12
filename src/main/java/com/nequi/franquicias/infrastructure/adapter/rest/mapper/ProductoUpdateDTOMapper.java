package com.nequi.franquicias.infrastructure.adapter.rest.mapper;

import com.nequi.franquicias.domain.model.Producto;
import com.nequi.franquicias.infrastructure.adapter.rest.dto.ProductoUpdateDTO;
import org.springframework.stereotype.Component;

/**
 * Mapeador para aplicar actualizaciones parciales a entidades de producto.
 */
@Component
public class ProductoUpdateDTOMapper {
    
    /**
     * Aplica las actualizaciones del DTO a la entidad de dominio.
     *
     * @param dto DTO con los campos a actualizar
     * @param producto entidad de dominio a actualizar
     * @return la entidad de dominio actualizada
     */
    public Producto applyUpdate(ProductoUpdateDTO dto, Producto producto) {
        if (dto.getNombre() != null) {
            producto.setNombre(dto.getNombre());
        }
        if (dto.getStock() != null) {
            producto.setStock(dto.getStock());
        }
        return producto;
    }
}
