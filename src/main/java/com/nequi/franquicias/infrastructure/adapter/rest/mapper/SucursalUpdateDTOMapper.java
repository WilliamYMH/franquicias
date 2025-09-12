package com.nequi.franquicias.infrastructure.adapter.rest.mapper;

import com.nequi.franquicias.domain.model.Sucursal;
import com.nequi.franquicias.infrastructure.adapter.rest.dto.SucursalUpdateDTO;
import org.springframework.stereotype.Component;

/**
 * Mapeador para aplicar actualizaciones parciales a entidades de sucursal.
 */
@Component
public class SucursalUpdateDTOMapper {
    
    /**
     * Aplica las actualizaciones del DTO a la entidad de dominio.
     *
     * @param dto DTO con los campos a actualizar
     * @param sucursal entidad de dominio a actualizar
     * @return la entidad de dominio actualizada
     */
    public Sucursal applyUpdate(SucursalUpdateDTO dto, Sucursal sucursal) {
        if (dto.getNombre() != null) {
            sucursal.setNombre(dto.getNombre());
        }
        return sucursal;
    }
}
