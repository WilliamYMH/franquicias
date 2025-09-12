package com.nequi.franquicias.infrastructure.adapter.rest.mapper;

import com.nequi.franquicias.domain.model.Franquicia;
import com.nequi.franquicias.infrastructure.adapter.rest.dto.FranquiciaDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

/**
 * Mapeador para convertir entre entidades de dominio y DTOs de franquicias.
 */
@Component
@RequiredArgsConstructor
public class FranquiciaDTOMapper {
    
    private final SucursalDTOMapper sucursalDTOMapper;
    
    /**
     * Convierte una entidad de dominio a un DTO.
     *
     * @param franquicia entidad de dominio
     * @return DTO
     */
    public FranquiciaDTO toDTO(Franquicia franquicia) {
        FranquiciaDTO dto = FranquiciaDTO.builder()
                .id(franquicia.getId())
                .nombre(franquicia.getNombre())
                .build();
        
        // Mapear sucursales si existen
        if (franquicia.getSucursales() != null && !franquicia.getSucursales().isEmpty()) {
            dto.setSucursales(
                franquicia.getSucursales().stream()
                    .map(sucursalDTOMapper::toDTO)
                    .collect(Collectors.toList())
            );
        }
        
        return dto;
    }
    
    /**
     * Convierte un DTO a una entidad de dominio.
     *
     * @param dto DTO
     * @return entidad de dominio
     */
    public Franquicia toDomain(FranquiciaDTO dto) {
        Franquicia franquicia = Franquicia.builder()
                .id(dto.getId())
                .nombre(dto.getNombre())
                .build();
        
        // Mapear sucursales si existen
        if (dto.getSucursales() != null && !dto.getSucursales().isEmpty()) {
            franquicia.setSucursales(
                dto.getSucursales().stream()
                    .map(sucursalDTOMapper::toDomain)
                    .collect(Collectors.toList())
            );
        }
        
        return franquicia;
    }
}
