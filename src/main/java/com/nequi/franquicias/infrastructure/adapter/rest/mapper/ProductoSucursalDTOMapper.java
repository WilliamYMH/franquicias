package com.nequi.franquicias.infrastructure.adapter.rest.mapper;

import com.nequi.franquicias.domain.model.ProductoSucursal;
import com.nequi.franquicias.infrastructure.adapter.rest.dto.ProductoSucursalDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Mapeador para convertir entre entidades de dominio y DTOs de productos con sucursales.
 */
@Component
@RequiredArgsConstructor
public class ProductoSucursalDTOMapper {
    
    private final ProductoDTOMapper productoMapper;
    private final SucursalDTOMapper sucursalMapper;
    
    /**
     * Convierte una entidad de dominio a un DTO.
     *
     * @param productoSucursal entidad de dominio
     * @return DTO
     */
    public ProductoSucursalDTO toDTO(ProductoSucursal productoSucursal) {
        return ProductoSucursalDTO.builder()
                .producto(productoMapper.toDTO(productoSucursal.getProducto()))
                .sucursal(sucursalMapper.toDTO(productoSucursal.getSucursal()))
                .build();
    }
}
