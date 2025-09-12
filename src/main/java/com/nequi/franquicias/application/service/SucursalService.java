package com.nequi.franquicias.application.service;

import com.nequi.franquicias.domain.model.Sucursal;
import com.nequi.franquicias.domain.port.input.SucursalUseCase;
import com.nequi.franquicias.domain.port.output.FranquiciaRepository;
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
    
    @Override
    public Mono<Sucursal> crearSucursal(String franquiciaId, Sucursal sucursal) {
        return franquiciaRepository.findById(franquiciaId)
                .flatMap(franquicia -> {
                    // Verificamos que la franquicia existe antes de crear la sucursal
                    return Mono.just(sucursal)
                            .map(s -> {
                                s.setId(null); // Aseguramos que se genere un nuevo ID
                                return s;
                            })
                            .flatMap(s -> sucursalRepository.save(s));
                });
    }
    
    @Override
    public Mono<Sucursal> obtenerSucursalPorId(String id) {
        return sucursalRepository.findById(id);
    }
    
    @Override
    public Flux<Sucursal> obtenerSucursalesPorFranquiciaId(String franquiciaId) {
        return sucursalRepository.findByFranquiciaId(franquiciaId);
    }
    
    @Override
    public Mono<Sucursal> actualizarSucursal(String id, Sucursal sucursal) {
        return sucursalRepository.findById(id)
                .flatMap(sucursalExistente -> {
                    sucursal.setId(id);
                    return sucursalRepository.save(sucursal);
                });
    }
    
    @Override
    public Mono<Void> eliminarSucursal(String id) {
        return sucursalRepository.deleteById(id);
    }
}
