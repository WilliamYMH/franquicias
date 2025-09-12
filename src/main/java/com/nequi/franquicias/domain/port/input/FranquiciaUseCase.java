package com.nequi.franquicias.domain.port.input;

import com.nequi.franquicias.domain.model.Franquicia;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Puerto de entrada para los casos de uso relacionados con franquicias.
 */
public interface FranquiciaUseCase {
    
    /**
     * Crea una nueva franquicia.
     *
     * @param franquicia la franquicia a crear
     * @return la franquicia creada
     */
    Mono<Franquicia> crearFranquicia(Franquicia franquicia);
    
    /**
     * Obtiene una franquicia por su ID.
     *
     * @param id el ID de la franquicia
     * @return la franquicia encontrada o un Mono vacío si no existe
     */
    Mono<Franquicia> obtenerFranquiciaPorId(Long id);
    
    /**
     * Obtiene todas las franquicias.
     *
     * @return un flujo de franquicias
     */
    Flux<Franquicia> obtenerTodasLasFranquicias();
    
    /**
     * Actualiza una franquicia existente.
     *
     * @param id el ID de la franquicia a actualizar
     * @param franquicia la franquicia con los datos actualizados
     * @return la franquicia actualizada
     */
    Mono<Franquicia> actualizarFranquicia(Long id, Franquicia franquicia);
    
    /**
     * Elimina una franquicia por su ID.
     *
     * @param id el ID de la franquicia a eliminar
     * @return un Mono que completa cuando la operación termina
     */
    Mono<Void> eliminarFranquicia(Long id);
}
