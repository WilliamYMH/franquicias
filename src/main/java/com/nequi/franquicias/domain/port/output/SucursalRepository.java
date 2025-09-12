package com.nequi.franquicias.domain.port.output;

import com.nequi.franquicias.domain.model.Sucursal;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Puerto de salida para la persistencia de sucursales.
 */
public interface SucursalRepository {
    
    /**
     * Guarda una sucursal.
     *
     * @param sucursal la sucursal a guardar
     * @return la sucursal guardada
     */
    Mono<Sucursal> save(Sucursal sucursal);
    
    /**
     * Busca una sucursal por su ID.
     *
     * @param id el ID de la sucursal
     * @return la sucursal encontrada o un Mono vacío si no existe
     */
    Mono<Sucursal> findById(Long id);
    
    /**
     * Obtiene todas las sucursales.
     *
     * @return un flujo de sucursales
     */
    Flux<Sucursal> findAll();
    
    /**
     * Obtiene todas las sucursales de una franquicia.
     *
     * @param franquiciaId el ID de la franquicia
     * @return un flujo de sucursales
     */
    Flux<Sucursal> findByFranquiciaId(Long franquiciaId);
    
    /**
     * Elimina una sucursal por su ID.
     *
     * @param id el ID de la sucursal a eliminar
     * @return un Mono que completa cuando la operación termina
     */
    Mono<Void> deleteById(Long id);
}
