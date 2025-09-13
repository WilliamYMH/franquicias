package com.nequi.franquicias.infrastructure.adapter.rest.controller;

import com.nequi.franquicias.domain.port.input.FranquiciaUseCase;
import com.nequi.franquicias.infrastructure.adapter.rest.dto.FranquiciaDTO;
import com.nequi.franquicias.infrastructure.adapter.rest.dto.FranquiciaUpdateDTO;
import com.nequi.franquicias.infrastructure.adapter.rest.mapper.FranquiciaDTOMapper;
import com.nequi.franquicias.infrastructure.adapter.rest.mapper.FranquiciaUpdateDTOMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Controlador REST para operaciones relacionadas con franquicias.
 */
@RestController
@RequestMapping("/api/franquicias")
@RequiredArgsConstructor
@Tag(name = "Franquicias", description = "API para gestionar franquicias")
public class FranquiciaController {
    
    private final FranquiciaUseCase franquiciaUseCase;
    private final FranquiciaDTOMapper mapper;
    private final FranquiciaUpdateDTOMapper updateMapper;
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Crear una nueva franquicia")
    public Mono<FranquiciaDTO> crearFranquicia(@Valid @RequestBody FranquiciaDTO franquiciaDTO) {
        return Mono.just(franquiciaDTO)
                .map(mapper::toDomain)
                .flatMap(franquiciaUseCase::crearFranquicia)
                .map(mapper::toDTO);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Obtener una franquicia por su ID")
    public Mono<FranquiciaDTO> obtenerFranquiciaPorId(@PathVariable Long id) {
        return franquiciaUseCase.obtenerFranquiciaPorId(id)
                .map(mapper::toDTO);
    }
    
    @GetMapping
    @Operation(summary = "Obtener todas las franquicias")
    public Flux<FranquiciaDTO> obtenerTodasLasFranquicias() {
        return franquiciaUseCase.obtenerTodasLasFranquicias()
                .map(mapper::toDTO);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Actualizar cualquier atributo de una franquicia")
    public Mono<FranquiciaDTO> actualizarCualquierAtributo(@PathVariable Long id, @Valid @RequestBody FranquiciaUpdateDTO franquiciaUpdateDTO) {
        return franquiciaUseCase.obtenerFranquiciaPorId(id)
                .map(franquicia -> updateMapper.applyUpdate(franquiciaUpdateDTO, franquicia))
                .flatMap(franquicia -> franquiciaUseCase.actualizarFranquicia(id, franquicia))
                .map(mapper::toDTO);
    }
    
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Eliminar una franquicia por su ID")
    public Mono<Void> eliminarFranquicia(@PathVariable Long id) {
        return franquiciaUseCase.eliminarFranquicia(id);
    }
}
