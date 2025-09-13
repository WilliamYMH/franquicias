package com.nequi.franquicias.application.service;

import com.nequi.franquicias.domain.model.Producto;
import com.nequi.franquicias.domain.model.ProductoSucursal;
import com.nequi.franquicias.domain.model.Sucursal;
import com.nequi.franquicias.domain.port.input.ProductoUseCase;
import com.nequi.franquicias.domain.port.output.ProductoRepository;
import com.nequi.franquicias.domain.port.output.SucursalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Comparator;

/**
 * Servicio que implementa los casos de uso relacionados con productos.
 */
@Service
@RequiredArgsConstructor
public class ProductoService implements ProductoUseCase {
    
    private final ProductoRepository productoRepository;
    private final SucursalRepository sucursalRepository;
    
    @Override
    public Mono<Producto> crearProducto(Long sucursalId, Producto producto) {
        return sucursalRepository.findById(sucursalId)
                .flatMap(sucursal -> {
                    // Verificamos que la sucursal existe antes de crear el producto
                    return Mono.just(producto)
                            .map(p -> {
                                p.setId(null); // Aseguramos que se genere un nuevo ID
                                p.setSucursalId(sucursalId); // Asignamos el sucursalId al producto
                                return p;
                            })
                            .flatMap(p -> productoRepository.save(p));
                });
    }
    
    @Override
    public Mono<Producto> obtenerProductoPorId(Long id) {
        return productoRepository.findById(id);
    }
    
    @Override
    public Flux<Producto> obtenerProductosPorSucursalId(Long sucursalId) {
        return productoRepository.findBySucursalId(sucursalId);
    }
    
    @Override
    public Mono<Producto> actualizarProducto(Long id, Producto producto) {
        return productoRepository.findById(id)
                .flatMap(productoExistente -> {
                    producto.setId(id);
                    return productoRepository.save(producto);
                });
    }
    
    @Override
    public Mono<Void> eliminarProducto(Long id) {
        return productoRepository.deleteById(id);
    }
    
    @Override
    public Flux<ProductoSucursal> obtenerProductoConMasStockPorSucursal(Long franquiciaId) {
        return sucursalRepository.findByFranquiciaId(franquiciaId)
                .flatMap(sucursal -> {
                    return productoRepository.findBySucursalId(sucursal.getId())
                            .sort(Comparator.comparing(Producto::getStock).reversed())
                            .take(1)
                            .map(producto -> ProductoSucursal.builder()
                                    .producto(producto)
                                    .sucursal(sucursal)
                                    .build());
                });
    }
}
