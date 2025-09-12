package com.nequi.franquicias.infrastructure.adapter.persistence;

import com.nequi.franquicias.domain.model.Sucursal;
import com.nequi.franquicias.domain.port.output.SucursalRepository;
import com.nequi.franquicias.infrastructure.adapter.persistence.mapper.SucursalMapper;
import com.nequi.franquicias.infrastructure.adapter.persistence.repository.SucursalR2DBCRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Adaptador para el repositorio de sucursales.
 */
@Component
@RequiredArgsConstructor
public class SucursalRepositoryAdapter implements SucursalRepository {
    
    private final SucursalR2DBCRepository repository;
    private final SucursalMapper mapper;
    
    @Override
    public Mono<Sucursal> save(Sucursal sucursal) {
        return Mono.just(sucursal)
                .map(s -> mapper.toEntity(s, s.getId()))
                .flatMap(repository::save)
                .map(mapper::toDomain);
    }
    
    @Override
    public Mono<Sucursal> findById(String id) {
        return repository.findById(id)
                .map(mapper::toDomain);
    }
    
    @Override
    public Flux<Sucursal> findAll() {
        return repository.findAll()
                .map(mapper::toDomain);
    }
    
    @Override
    public Flux<Sucursal> findByFranquiciaId(String franquiciaId) {
        return repository.findByFranquiciaId(franquiciaId)
                .map(mapper::toDomain);
    }
    
    @Override
    public Mono<Void> deleteById(String id) {
        return repository.deleteById(id);
    }
}
