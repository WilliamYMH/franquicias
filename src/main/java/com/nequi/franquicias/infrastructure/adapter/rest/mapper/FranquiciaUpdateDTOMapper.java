package com.nequi.franquicias.infrastructure.adapter.rest.mapper;

import com.nequi.franquicias.domain.model.Franquicia;
import com.nequi.franquicias.infrastructure.adapter.rest.dto.FranquiciaUpdateDTO;
import org.springframework.stereotype.Component;

/**
 * Mapeador para aplicar actualizaciones parciales a entidades de franquicia.
 */
@Component
public class FranquiciaUpdateDTOMapper {
    
    /**
     * Aplica las actualizaciones del DTO a la entidad de dominio.
     *
     * @param dto DTO con los campos a actualizar
     * @param franquicia entidad de dominio a actualizar
     * @return la entidad de dominio actualizada
     */
    public Franquicia applyUpdate(FranquiciaUpdateDTO dto, Franquicia franquicia) {
        if (dto.getNombre() != null) {
            franquicia.setNombre(dto.getNombre());
        }
        return franquicia;
    }
}
