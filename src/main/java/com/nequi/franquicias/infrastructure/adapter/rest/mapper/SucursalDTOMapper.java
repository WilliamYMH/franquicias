package com.nequi.franquicias.infrastructure.adapter.rest.mapper;

import com.nequi.franquicias.domain.model.Sucursal;
import com.nequi.franquicias.infrastructure.adapter.rest.dto.SucursalDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

/**
 * Mapeador para convertir entre entidades de dominio y DTOs de sucursales.
 */
@Component
@RequiredArgsConstructor
public class SucursalDTOMapper {
    
    private final ProductoDTOMapper productoDTOMapper;
    
    /**
     * Convierte una entidad de dominio a un DTO.
     *
     * @param sucursal entidad de dominio
     * @return DTO
     */
    public SucursalDTO toDTO(Sucursal sucursal) {
        SucursalDTO dto = SucursalDTO.builder()
                .id(sucursal.getId())
                .nombre(sucursal.getNombre())
                .franquiciaId(sucursal.getFranquiciaId())
                .build();
        
        // Mapear productos si existen
        if (sucursal.getProductos() != null && !sucursal.getProductos().isEmpty()) {
            dto.setProductos(
                sucursal.getProductos().stream()
                    .map(productoDTOMapper::toDTO)
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
    public Sucursal toDomain(SucursalDTO dto) {
        Sucursal sucursal = Sucursal.builder()
                .id(dto.getId())
                .nombre(dto.getNombre())
                .franquiciaId(dto.getFranquiciaId())
                .build();
        
        // Mapear productos si existen
        if (dto.getProductos() != null && !dto.getProductos().isEmpty()) {
            sucursal.setProductos(
                dto.getProductos().stream()
                    .map(productoDTOMapper::toDomain)
                    .collect(Collectors.toList())
            );
        }
        
        return sucursal;
    }
}
