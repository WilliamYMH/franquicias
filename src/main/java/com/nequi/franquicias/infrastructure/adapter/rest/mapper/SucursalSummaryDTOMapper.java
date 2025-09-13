package com.nequi.franquicias.infrastructure.adapter.rest.mapper;

import com.nequi.franquicias.domain.model.Sucursal;
import com.nequi.franquicias.infrastructure.adapter.rest.dto.SucursalSummaryDTO;
import org.springframework.stereotype.Component;

/**
 * Mapeador para convertir Sucursal de dominio a un DTO resumido (id, nombre).
 */
@Component
public class SucursalSummaryDTOMapper {

    public SucursalSummaryDTO toDTO(Sucursal sucursal) {
        if (sucursal == null) {
            return null;
        }
        return SucursalSummaryDTO.builder()
                .id(sucursal.getId())
                .nombre(sucursal.getNombre())
                .build();
    }
}
