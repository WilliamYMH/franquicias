package com.nequi.franquicias.domain.port.output;

import com.nequi.franquicias.domain.model.Franquicia;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Puerto de salida para la persistencia de franquicias.
 */
public interface FranquiciaRepository {
    
    /**
     * Guarda una franquicia.
     *
     * @param franquicia la franquicia a guardar
     * @return la franquicia guardada
     */
    Mono<Franquicia> save(Franquicia franquicia);
    
    /**
     * Busca una franquicia por su ID.
     *
     * @param id el ID de la franquicia
     * @return la franquicia encontrada o un Mono vacío si no existe
     */
    Mono<Franquicia> findById(Long id);
    
    /**
     * Obtiene todas las franquicias.
     *
     * @return un flujo de franquicias
     */
    Flux<Franquicia> findAll();
    
    /**
     * Elimina una franquicia por su ID.
     *
     * @param id el ID de la franquicia a eliminar
     * @return un Mono que completa cuando la operación termina
     */
    Mono<Void> deleteById(Long id);
}
