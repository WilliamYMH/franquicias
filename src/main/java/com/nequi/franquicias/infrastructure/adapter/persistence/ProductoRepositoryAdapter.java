package com.nequi.franquicias.infrastructure.adapter.persistence;

import com.nequi.franquicias.domain.model.Producto;
import com.nequi.franquicias.domain.port.output.ProductoRepository;
import com.nequi.franquicias.infrastructure.adapter.persistence.mapper.ProductoMapper;
import com.nequi.franquicias.infrastructure.adapter.persistence.repository.ProductoR2DBCRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Adaptador para el repositorio de productos.
 */
@Component
@RequiredArgsConstructor
public class ProductoRepositoryAdapter implements ProductoRepository {
    
    private final ProductoR2DBCRepository repository;
    private final ProductoMapper mapper;
    
    @Override
    public Mono<Producto> save(Producto producto) {
        return Mono.just(producto)
                .map(p -> mapper.toEntity(p, p.getSucursalId()))
                .flatMap(repository::save)
                .map(mapper::toDomain);
    }
    
    @Override
    public Mono<Producto> findById(Long id) {
        return repository.findById(id)
                .map(mapper::toDomain);
    }
    
    @Override
    public Flux<Producto> findAll() {
        return repository.findAll()
                .map(mapper::toDomain);
    }
    
    @Override
    public Flux<Producto> findBySucursalId(Long sucursalId) {
        return repository.findBySucursalId(sucursalId)
                .map(mapper::toDomain);
    }
    
    @Override
    public Mono<Void> deleteById(Long id) {
        return repository.deleteById(id);
    }
}
