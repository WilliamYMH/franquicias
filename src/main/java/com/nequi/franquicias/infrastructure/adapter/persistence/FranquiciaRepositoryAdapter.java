package com.nequi.franquicias.infrastructure.adapter.persistence;

import com.nequi.franquicias.domain.model.Franquicia;
import com.nequi.franquicias.domain.port.output.FranquiciaRepository;
import com.nequi.franquicias.infrastructure.adapter.persistence.mapper.FranquiciaMapper;
import com.nequi.franquicias.infrastructure.adapter.persistence.repository.FranquiciaR2DBCRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Adaptador para el repositorio de franquicias.
 */
@Component
@RequiredArgsConstructor
public class FranquiciaRepositoryAdapter implements FranquiciaRepository {
    
    private final FranquiciaR2DBCRepository repository;
    private final FranquiciaMapper mapper;
    
    @Override
    public Mono<Franquicia> save(Franquicia franquicia) {
        return Mono.just(franquicia)
                .map(mapper::toEntity)
                .flatMap(repository::save)
                .map(mapper::toDomain);
    }
    
    @Override
    public Mono<Franquicia> findById(String id) {
        return repository.findById(id)
                .map(mapper::toDomain);
    }
    
    @Override
    public Flux<Franquicia> findAll() {
        return repository.findAll()
                .map(mapper::toDomain);
    }
    
    @Override
    public Mono<Void> deleteById(String id) {
        return repository.deleteById(id);
    }
}
