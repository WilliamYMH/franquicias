package com.nequi.franquicias.infrastructure.adapter.rest.mapper;

import com.nequi.franquicias.domain.model.Franquicia;
import com.nequi.franquicias.infrastructure.adapter.rest.dto.FranquiciaDTO;
import org.springframework.stereotype.Component;

/**
 * Mapeador para convertir entre entidades de dominio y DTOs de franquicias.
 */
@Component
public class FranquiciaDTOMapper {
    
    /**
     * Convierte una entidad de dominio a un DTO.
     *
     * @param franquicia entidad de dominio
     * @return DTO
     */
    public FranquiciaDTO toDTO(Franquicia franquicia) {
        return FranquiciaDTO.builder()
                .id(franquicia.getId())
                .nombre(franquicia.getNombre())
                .build();
    }
    
    /**
     * Convierte un DTO a una entidad de dominio.
     *
     * @param dto DTO
     * @return entidad de dominio
     */
    public Franquicia toDomain(FranquiciaDTO dto) {
        return Franquicia.builder()
                .id(dto.getId())
                .nombre(dto.getNombre())
                .build();
    }
}
