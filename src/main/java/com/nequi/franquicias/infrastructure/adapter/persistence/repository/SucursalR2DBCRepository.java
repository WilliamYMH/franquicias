package com.nequi.franquicias.infrastructure.adapter.persistence.repository;

import com.nequi.franquicias.infrastructure.adapter.persistence.entity.SucursalEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

/**
 * Repositorio R2DBC para sucursales.
 */
@Repository
public interface SucursalR2DBCRepository extends R2dbcRepository<SucursalEntity, Long> {
    
    /**
     * Busca sucursales por el ID de la franquicia.
     *
     * @param franquiciaId el ID de la franquicia
     * @return un flujo de sucursales
     */
    Flux<SucursalEntity> findByFranquiciaId(Long franquiciaId);
}
