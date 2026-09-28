package ar.com.avaco.fwk.core.component.epservice;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import ar.com.avaco.fwk.core.component.dto.entity.DTOAuditableEntity;
import ar.com.avaco.fwk.core.component.service.NJService;
import ar.com.avaco.fwk.core.domain.AuditableEntity;

@Transactional
public abstract class CRUDAuditableEPBaseService<ID extends Serializable, DTO extends DTOAuditableEntity<ID>, T extends AuditableEntity<ID>, S extends NJService<ID, T>>
		extends CRUDEPBaseService<ID, DTO, T, S> {

	public CRUDAuditableEPBaseService(Class<T> theEntityClass, Class<DTO> theDtoClass) {
		super(theEntityClass, theDtoClass);
	}

	public List<DTO> convertToDtos(Collection<T> entities) {
		List<DTO> dtos = new ArrayList<DTO>();
		for (T entity : entities) {
			DTO convertToDto = convertToDto(entity);
			convertToDto.setUsuarioCreacion(entity.getUsuarioCreacion());
			convertToDto.setFechaCreacion(entity.getFechaCreacion());
			convertToDto.setUsuarioActualizacion(entity.getUsuarioActualizacion());
			convertToDto.setFechaActualizacion(entity.getFechaActualizacion());
			dtos.add(convertToDto);
		}
		return dtos;
	}


}
