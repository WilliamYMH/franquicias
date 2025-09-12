package com.nequi.franquicias.infrastructure.adapter.persistence.mapper;

import com.nequi.franquicias.domain.model.Franquicia;
import com.nequi.franquicias.infrastructure.adapter.persistence.entity.FranquiciaEntity;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Mapeador para convertir entre entidades de dominio y entidades de persistencia de franquicias.
 */
@Component
public class FranquiciaMapper {
    
    /**
     * Convierte una entidad de dominio a una entidad de persistencia.
     *
     * @param franquicia entidad de dominio
     * @return entidad de persistencia
     */
    public FranquiciaEntity toEntity(Franquicia franquicia) {
        return FranquiciaEntity.builder()
                .id(franquicia.getId() != null ? franquicia.getId() : UUID.randomUUID().toString())
                .nombre(franquicia.getNombre())
                .build();
    }
    
    /**
     * Convierte una entidad de persistencia a una entidad de dominio.
     *
     * @param entity entidad de persistencia
     * @return entidad de dominio
     */
    public Franquicia toDomain(FranquiciaEntity entity) {
        return Franquicia.builder()
                .id(entity.getId())
                .nombre(entity.getNombre())
                .build();
    }
}
