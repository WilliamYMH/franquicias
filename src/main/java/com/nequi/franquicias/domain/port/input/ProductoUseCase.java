package com.nequi.franquicias.domain.port.input;

import com.nequi.franquicias.domain.model.Producto;
import com.nequi.franquicias.domain.model.ProductoSucursal;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Puerto de entrada para los casos de uso relacionados con productos.
 */
public interface ProductoUseCase {
    
    /**
     * Crea un nuevo producto para una sucursal.
     *
     * @param sucursalId el ID de la sucursal
     * @param producto el producto a crear
     * @return el producto creado
     */
    Mono<Producto> crearProducto(String sucursalId, Producto producto);
    
    /**
     * Obtiene un producto por su ID.
     *
     * @param id el ID del producto
     * @return el producto encontrado o un Mono vacío si no existe
     */
    Mono<Producto> obtenerProductoPorId(String id);
    
    /**
     * Obtiene todos los productos de una sucursal.
     *
     * @param sucursalId el ID de la sucursal
     * @return un flujo de productos
     */
    Flux<Producto> obtenerProductosPorSucursalId(String sucursalId);
    
    /**
     * Actualiza un producto existente.
     *
     * @param id el ID del producto a actualizar
     * @param producto el producto con los datos actualizados
     * @return el producto actualizado
     */
    Mono<Producto> actualizarProducto(String id, Producto producto);
    
    /**
     * Actualiza el stock de un producto.
     *
     * @param id el ID del producto
     * @param cantidad la nueva cantidad de stock
     * @return el producto actualizado
     */
    Mono<Producto> actualizarStockProducto(String id, Integer cantidad);
    
    /**
     * Elimina un producto por su ID.
     *
     * @param id el ID del producto a eliminar
     * @return un Mono que completa cuando la operación termina
     */
    Mono<Void> eliminarProducto(String id);
    
    /**
     * Obtiene el producto con más stock por cada sucursal de una franquicia.
     *
     * @param franquiciaId el ID de la franquicia
     * @return un flujo de productos con su sucursal asociada
     */
    Flux<ProductoSucursal> obtenerProductoConMasStockPorSucursal(String franquiciaId);
}
