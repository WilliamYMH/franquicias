package com.nequi.franquicias.infrastructure.adapter.rest.controller;

import com.nequi.franquicias.domain.port.input.ProductoUseCase;
import com.nequi.franquicias.infrastructure.adapter.rest.dto.ProductoDTO;
import com.nequi.franquicias.infrastructure.adapter.rest.dto.ProductoSucursalSummaryDTO;
import com.nequi.franquicias.infrastructure.adapter.rest.dto.ProductoUpdateDTO;
import com.nequi.franquicias.infrastructure.adapter.rest.mapper.ProductoDTOMapper;
import com.nequi.franquicias.infrastructure.adapter.rest.mapper.ProductoSucursalSummaryDTOMapper;
import com.nequi.franquicias.infrastructure.adapter.rest.mapper.ProductoUpdateDTOMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Controlador REST para operaciones relacionadas con productos.
 */
@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
@Tag(name = "Productos", description = "API para gestionar productos")
public class ProductoController {
    
    private final ProductoUseCase productoUseCase;
    private final ProductoDTOMapper mapper;
    private final ProductoSucursalSummaryDTOMapper productoSucursalMapper;
    private final ProductoUpdateDTOMapper updateMapper;
    
    @PostMapping("/sucursal/{sucursalId}")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Crear un nuevo producto para una sucursal")
    public Mono<ProductoDTO> crearProducto(@PathVariable Long sucursalId, @Valid @RequestBody ProductoDTO productoDTO) {
        return Mono.just(productoDTO)
                .map(mapper::toDomain)
                .flatMap(producto -> productoUseCase.crearProducto(sucursalId, producto))
                .map(mapper::toDTO);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Obtener un producto por su ID")
    public Mono<ProductoDTO> obtenerProductoPorId(@PathVariable Long id) {
        return productoUseCase.obtenerProductoPorId(id)
                .map(mapper::toDTO);
    }
    
    @GetMapping("/sucursal/{sucursalId}")
    @Operation(summary = "Obtener todos los productos de una sucursal")
    public Flux<ProductoDTO> obtenerProductosPorSucursalId(@PathVariable Long sucursalId) {
        return productoUseCase.obtenerProductosPorSucursalId(sucursalId)
                .map(mapper::toDTO);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Actualizar cualquier atributo de un producto")
    public Mono<ProductoDTO> actualizarCualquierAtributo(@PathVariable Long id, @Valid @RequestBody ProductoUpdateDTO productoUpdateDTO) {
        return productoUseCase.obtenerProductoPorId(id)
                .map(producto -> updateMapper.applyUpdate(productoUpdateDTO, producto))
                .flatMap(producto -> productoUseCase.actualizarProducto(id, producto))
                .map(mapper::toDTO);
    }
    
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Eliminar un producto por su ID")
    public Mono<Void> eliminarProducto(@PathVariable Long id) {
        return productoUseCase.eliminarProducto(id);
    }
    
    @GetMapping("/max-stock/franquicia/{franquiciaId}")
    @Operation(summary = "Obtener el producto con más stock por sucursal para una franquicia específica")
    public Flux<ProductoSucursalSummaryDTO> obtenerProductoConMasStockPorSucursal(@PathVariable Long franquiciaId) {
        return productoUseCase.obtenerProductoConMasStockPorSucursal(franquiciaId)
                .map(productoSucursalMapper::toDTO);
    }
}
