package com.nequi.franquicias.infrastructure.adapter.persistence.mapper;

import com.nequi.franquicias.domain.model.Sucursal;
import com.nequi.franquicias.infrastructure.adapter.persistence.entity.SucursalEntity;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Mapeador para convertir entre entidades de dominio y entidades de persistencia de sucursales.
 */
@Component
public class SucursalMapper {
    
    /**
     * Convierte una entidad de dominio a una entidad de persistencia.
     *
     * @param sucursal entidad de dominio
     * @param franquiciaId ID de la franquicia a la que pertenece la sucursal
     * @return entidad de persistencia
     */
    public SucursalEntity toEntity(Sucursal sucursal, String franquiciaId) {
        return SucursalEntity.builder()
                .id(sucursal.getId() != null ? sucursal.getId() : UUID.randomUUID().toString())
                .nombre(sucursal.getNombre())
                .franquiciaId(franquiciaId)
                .build();
    }
    
    /**
     * Convierte una entidad de persistencia a una entidad de dominio.
     *
     * @param entity entidad de persistencia
     * @return entidad de dominio
     */
    public Sucursal toDomain(SucursalEntity entity) {
        return Sucursal.builder()
                .id(entity.getId())
                .nombre(entity.getNombre())
                .build();
    }
}
