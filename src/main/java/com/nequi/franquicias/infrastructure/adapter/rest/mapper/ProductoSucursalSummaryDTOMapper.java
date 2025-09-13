package com.nequi.franquicias.infrastructure.adapter.rest.mapper;

import com.nequi.franquicias.domain.model.ProductoSucursal;
import com.nequi.franquicias.infrastructure.adapter.rest.dto.ProductoSucursalSummaryDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Mapeador para convertir dominio ProductoSucursal a un DTO con sucursal resumida (id, nombre).
 */
@Component
@RequiredArgsConstructor
public class ProductoSucursalSummaryDTOMapper {

    private final ProductoDTOMapper productoMapper;
    private final SucursalSummaryDTOMapper sucursalSummaryMapper;

    public ProductoSucursalSummaryDTO toDTO(ProductoSucursal ps) {
        return ProductoSucursalSummaryDTO.builder()
                .producto(productoMapper.toDTO(ps.getProducto()))
                .sucursal(sucursalSummaryMapper.toDTO(ps.getSucursal()))
                .build();
    }
}
