package com.nequi.franquicias.domain.port.input;

import com.nequi.franquicias.domain.model.Sucursal;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Puerto de entrada para los casos de uso relacionados con sucursales.
 */
public interface SucursalUseCase {
    
    /**
     * Crea una nueva sucursal para una franquicia.
     *
     * @param franquiciaId el ID de la franquicia
     * @param sucursal la sucursal a crear
     * @return la sucursal creada
     */
    Mono<Sucursal> crearSucursal(Long franquiciaId, Sucursal sucursal);
    
    /**
     * Obtiene una sucursal por su ID.
     *
     * @param id el ID de la sucursal
     * @return la sucursal encontrada o un Mono vacío si no existe
     */
    Mono<Sucursal> obtenerSucursalPorId(Long id);
    
    /**
     * Obtiene todas las sucursales de una franquicia.
     *
     * @param franquiciaId el ID de la franquicia
     * @return un flujo de sucursales
     */
    Flux<Sucursal> obtenerSucursalesPorFranquiciaId(Long franquiciaId);
    
    /**
     * Actualiza una sucursal existente.
     *
     * @param id el ID de la sucursal a actualizar
     * @param sucursal la sucursal con los datos actualizados
     * @return la sucursal actualizada
     */
    Mono<Sucursal> actualizarSucursal(Long id, Sucursal sucursal);
    
    /**
     * Elimina una sucursal por su ID.
     *
     * @param id el ID de la sucursal a eliminar
     * @return un Mono que completa cuando la operación termina
     */
    Mono<Void> eliminarSucursal(Long id);
}
