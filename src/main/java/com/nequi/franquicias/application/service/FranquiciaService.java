package com.nequi.franquicias.application.service;

import com.nequi.franquicias.domain.model.Franquicia;
import com.nequi.franquicias.domain.port.input.FranquiciaUseCase;
import com.nequi.franquicias.domain.port.output.FranquiciaRepository;
import com.nequi.franquicias.domain.port.output.SucursalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Servicio que implementa los casos de uso relacionados con franquicias.
 */
@Service
@RequiredArgsConstructor
public class FranquiciaService implements FranquiciaUseCase {
    
    private final FranquiciaRepository franquiciaRepository;
    private final SucursalRepository sucursalRepository;
    
    @Override
    public Mono<Franquicia> crearFranquicia(Franquicia franquicia) {
        return franquiciaRepository.save(franquicia);
    }
    
    @Override
    public Mono<Franquicia> obtenerFranquiciaPorId(Long id) {
        return franquiciaRepository.findById(id)
                .flatMap(franquicia -> {
                    // Cargamos las sucursales asociadas a la franquicia
                    return sucursalRepository.findByFranquiciaId(franquicia.getId())
                            .collectList()
                            .map(sucursales -> {
                                franquicia.setSucursales(sucursales);
                                return franquicia;
                            });
                });
    }
    
    @Override
    public Flux<Franquicia> obtenerTodasLasFranquicias() {
        return franquiciaRepository.findAll();
    }
    
    @Override
    public Mono<Franquicia> actualizarFranquicia(Long id, Franquicia franquicia) {
        return franquiciaRepository.findById(id)
                .flatMap(franquiciaExistente -> {
                    franquicia.setId(id);
                    return franquiciaRepository.save(franquicia);
                });
    }
    
    @Override
    public Mono<Void> eliminarFranquicia(Long id) {
        return franquiciaRepository.deleteById(id);
    }
}
