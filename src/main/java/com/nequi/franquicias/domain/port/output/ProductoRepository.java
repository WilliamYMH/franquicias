package com.nequi.franquicias.domain.port.output;

import com.nequi.franquicias.domain.model.Producto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Puerto de salida para la persistencia de productos.
 */
public interface ProductoRepository {
    
    /**
     * Guarda un producto.
     *
     * @param producto el producto a guardar
     * @return el producto guardado
     */
    Mono<Producto> save(Producto producto);
    
    /**
     * Busca un producto por su ID.
     *
     * @param id el ID del producto
     * @return el producto encontrado o un Mono vacío si no existe
     */
    Mono<Producto> findById(String id);
    
    /**
     * Obtiene todos los productos.
     *
     * @return un flujo de productos
     */
    Flux<Producto> findAll();
    
    /**
     * Obtiene todos los productos de una sucursal.
     *
     * @param sucursalId el ID de la sucursal
     * @return un flujo de productos
     */
    Flux<Producto> findBySucursalId(String sucursalId);
    
    /**
     * Elimina un producto por su ID.
     *
     * @param id el ID del producto a eliminar
     * @return un Mono que completa cuando la operación termina
     */
    Mono<Void> deleteById(String id);
}
