package com.nequi.franquicias.infrastructure.adapter.rest.controller;

import com.nequi.franquicias.domain.port.input.SucursalUseCase;
import com.nequi.franquicias.infrastructure.adapter.rest.dto.SucursalDTO;
import com.nequi.franquicias.infrastructure.adapter.rest.dto.SucursalUpdateDTO;
import com.nequi.franquicias.infrastructure.adapter.rest.mapper.SucursalDTOMapper;
import com.nequi.franquicias.infrastructure.adapter.rest.mapper.SucursalUpdateDTOMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Controlador REST para operaciones relacionadas con sucursales.
 */
@RestController
@RequestMapping("/api/sucursales")
@RequiredArgsConstructor
@Tag(name = "Sucursales", description = "API para gestionar sucursales")
public class SucursalController {
    
    private final SucursalUseCase sucursalUseCase;
    private final SucursalDTOMapper mapper;
    private final SucursalUpdateDTOMapper updateMapper;
    
    @PostMapping("/franquicia/{franquiciaId}")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Crear una nueva sucursal para una franquicia")
    public Mono<SucursalDTO> crearSucursal(@PathVariable Long franquiciaId, @Valid @RequestBody SucursalDTO sucursalDTO) {
        return Mono.just(sucursalDTO)
                .map(mapper::toDomain)
                .flatMap(sucursal -> sucursalUseCase.crearSucursal(franquiciaId, sucursal))
                .map(mapper::toDTO);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Obtener una sucursal por su ID")
    public Mono<SucursalDTO> obtenerSucursalPorId(@PathVariable Long id) {
        return sucursalUseCase.obtenerSucursalPorId(id)
                .map(mapper::toDTO);
    }
    
    @GetMapping("/franquicia/{franquiciaId}")
    @Operation(summary = "Obtener todas las sucursales de una franquicia")
    public Flux<SucursalDTO> obtenerSucursalesPorFranquiciaId(@PathVariable Long franquiciaId) {
        return sucursalUseCase.obtenerSucursalesPorFranquiciaId(franquiciaId)
                .map(mapper::toDTO);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Actualizar parcialmente una sucursal existente")
    public Mono<SucursalDTO> actualizarSucursalParcial(@PathVariable Long id, @Valid @RequestBody SucursalUpdateDTO sucursalUpdateDTO) {
        return sucursalUseCase.obtenerSucursalPorId(id)
                .map(sucursal -> updateMapper.applyUpdate(sucursalUpdateDTO, sucursal))
                .flatMap(sucursal -> sucursalUseCase.actualizarSucursal(id, sucursal))
                .map(mapper::toDTO);
    }
    
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Eliminar una sucursal por su ID")
    public Mono<Void> eliminarSucursal(@PathVariable Long id) {
        return sucursalUseCase.eliminarSucursal(id);
    }
}
