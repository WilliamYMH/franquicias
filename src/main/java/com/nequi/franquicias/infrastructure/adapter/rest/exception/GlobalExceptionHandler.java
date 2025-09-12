package com.nequi.franquicias.infrastructure.adapter.rest.exception;

import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Manejador global de excepciones para la API REST.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    
    /**
     * Maneja excepciones de validación de datos.
     *
     * @param ex la excepción de validación
     * @return un mapa con los errores de validación
     */
    @ExceptionHandler(WebExchangeBindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Mono<Map<String, Object>> handleValidationException(WebExchangeBindException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("error", "Validation Error");
        
        Map<String, String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap(
                        fieldError -> fieldError.getField(),
                        fieldError -> fieldError.getDefaultMessage() != null ? fieldError.getDefaultMessage() : "Error de validación"
                ));
        
        response.put("errors", errors);
        
        return Mono.just(response);
    }
    
    /**
     * Maneja excepciones de estado de respuesta.
     *
     * @param ex la excepción de estado de respuesta
     * @return un mapa con el error
     */
    @ExceptionHandler(ResponseStatusException.class)
    public Mono<Map<String, Object>> handleResponseStatusException(ResponseStatusException ex, ServerWebExchange exchange) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", ex.getStatusCode().value());
        response.put("error", ex.getReason());
        
        exchange.getResponse().setStatusCode(ex.getStatusCode());
        
        return Mono.just(response);
    }
    
    /**
     * Maneja cualquier otra excepción no controlada.
     *
     * @param ex la excepción
     * @return un mapa con el error
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Mono<Map<String, Object>> handleGenericException(Exception ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.put("error", "Error interno del servidor");
        response.put("message", ex.getMessage());
        
        return Mono.just(response);
    }
    
    /**
     * Configuración para el manejador de errores web.
     */
    @Configuration
    public static class ErrorHandlerConfig {
        
        @Bean
        public WebProperties.Resources resources() {
            return new WebProperties.Resources();
        }
    }
    
    /**
     * Manejador de errores web para excepciones en el flujo reactivo.
     */
    @Component
    @Order(-2)
    public static class GlobalErrorWebExceptionHandler extends AbstractErrorWebExceptionHandler {
        
        public GlobalErrorWebExceptionHandler(ErrorAttributes errorAttributes,
                                             WebProperties.Resources resources,
                                             ApplicationContext applicationContext,
                                             ServerCodecConfigurer configurer) {
            super(errorAttributes, resources, applicationContext);
            this.setMessageWriters(configurer.getWriters());
        }
        
        @Override
        protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
            return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse);
        }
        
        private Mono<ServerResponse> renderErrorResponse(ServerRequest request) {
            Map<String, Object> errorPropertiesMap = getErrorAttributes(request, null);
            
            return ServerResponse.status(HttpStatus.valueOf((Integer) errorPropertiesMap.getOrDefault("status", 500)))
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(BodyInserters.fromValue(errorPropertiesMap));
        }
    }
}
