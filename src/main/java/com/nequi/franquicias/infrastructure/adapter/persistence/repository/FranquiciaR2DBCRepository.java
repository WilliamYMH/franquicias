package com.nequi.franquicias.infrastructure.adapter.persistence.repository;

import com.nequi.franquicias.infrastructure.adapter.persistence.entity.FranquiciaEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio R2DBC para franquicias.
 */
@Repository
public interface FranquiciaR2DBCRepository extends R2dbcRepository<FranquiciaEntity, Long> {
    // No se necesitan métodos adicionales por ahora, ya que los métodos básicos
    // como findById, findAll, save, deleteById, etc. son proporcionados por R2dbcRepository
}
