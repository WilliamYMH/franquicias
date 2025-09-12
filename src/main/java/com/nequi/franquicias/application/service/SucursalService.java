package com.nequi.franquicias.application.service;

import com.nequi.franquicias.domain.model.Sucursal;
import com.nequi.franquicias.domain.port.input.SucursalUseCase;
import com.nequi.franquicias.domain.port.output.FranquiciaRepository;
import com.nequi.franquicias.domain.port.output.ProductoRepository;
import com.nequi.franquicias.domain.port.output.SucursalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Servicio que implementa los casos de uso relacionados con sucursales.
 */
@Service
@RequiredArgsConstructor
public class SucursalService implements SucursalUseCase {
    
    private final SucursalRepository sucursalRepository;
    private final FranquiciaRepository franquiciaRepository;
    private final ProductoRepository productoRepository;
    
    @Override
    public Mono<Sucursal> crearSucursal(Long franquiciaId, Sucursal sucursal) {
        return franquiciaRepository.findById(franquiciaId)
                .flatMap(franquicia -> {
                    // Verificamos que la franquicia existe antes de crear la sucursal
                    return Mono.just(sucursal)
                            .map(s -> {
                                s.setId(null); // Aseguramos que se genere un nuevo ID
                                s.setFranquiciaId(franquiciaId); // Asignamos el franquiciaId a la sucursal
                                return s;
                            })
                            .flatMap(s -> sucursalRepository.save(s));
                });
    }
    
    @Override
    public Mono<Sucursal> obtenerSucursalPorId(Long id) {
        return sucursalRepository.findById(id)
                .flatMap(sucursal -> {
                    // Cargamos los productos asociados a la sucursal
                    return productoRepository.findBySucursalId(sucursal.getId())
                            .collectList()
                            .map(productos -> {
                                sucursal.setProductos(productos);
                                return sucursal;
                            });
                });
    }
    
    @Override
    public Flux<Sucursal> obtenerSucursalesPorFranquiciaId(Long franquiciaId) {
        return sucursalRepository.findByFranquiciaId(franquiciaId);
    }
    
    @Override
    public Mono<Sucursal> actualizarSucursal(Long id, Sucursal sucursal) {
        return sucursalRepository.findById(id)
                .flatMap(sucursalExistente -> {
                    sucursal.setId(id);
                    return sucursalRepository.save(sucursal);
                });
    }
    
    @Override
    public Mono<Void> eliminarSucursal(Long id) {
        return sucursalRepository.deleteById(id);
    }
}
