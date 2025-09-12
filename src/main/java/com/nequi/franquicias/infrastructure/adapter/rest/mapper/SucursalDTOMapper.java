package com.nequi.franquicias.infrastructure.adapter.rest.mapper;

import com.nequi.franquicias.domain.model.Sucursal;
import com.nequi.franquicias.infrastructure.adapter.rest.dto.SucursalDTO;
import org.springframework.stereotype.Component;

/**
 * Mapeador para convertir entre entidades de dominio y DTOs de sucursales.
 */
@Component
public class SucursalDTOMapper {
    
    /**
     * Convierte una entidad de dominio a un DTO.
     *
     * @param sucursal entidad de dominio
     * @return DTO
     */
    public SucursalDTO toDTO(Sucursal sucursal) {
        return SucursalDTO.builder()
                .id(sucursal.getId())
                .nombre(sucursal.getNombre())
                .franquiciaId(sucursal.getFranquiciaId())
                .build();
    }
    
    /**
     * Convierte un DTO a una entidad de dominio.
     *
     * @param dto DTO
     * @return entidad de dominio
     */
    public Sucursal toDomain(SucursalDTO dto) {
        return Sucursal.builder()
                .id(dto.getId())
                .nombre(dto.getNombre())
                .franquiciaId(dto.getFranquiciaId())
                .build();
    }
}
