package com.nequi.franquicias.infrastructure.adapter.persistence.repository;

import com.nequi.franquicias.infrastructure.adapter.persistence.entity.ProductoEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

/**
 * Repositorio R2DBC para productos.
 */
@Repository
public interface ProductoR2DBCRepository extends R2dbcRepository<ProductoEntity, String> {
    
    /**
     * Busca productos por el ID de la sucursal.
     *
     * @param sucursalId el ID de la sucursal
     * @return un flujo de productos
     */
    Flux<ProductoEntity> findBySucursalId(String sucursalId);
}
