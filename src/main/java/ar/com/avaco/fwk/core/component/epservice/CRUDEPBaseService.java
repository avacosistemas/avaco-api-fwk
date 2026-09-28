package ar.com.avaco.fwk.core.component.epservice;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.modelmapper.ModelMapper;

import ar.com.avaco.fwk.core.component.dto.PageDTO;
import ar.com.avaco.fwk.core.component.dto.entity.DTOEntity;
import ar.com.avaco.fwk.core.component.service.NJService;
import ar.com.avaco.fwk.core.domain.Entity;
import ar.com.avaco.fwk.core.domain.filter.AbstractFilter;
import ar.com.avaco.fwk.core.exception.BusinessException;

public abstract class CRUDEPBaseService<ID extends Serializable, DTO extends DTOEntity<ID>, T extends Entity<ID>, S extends NJService<ID, T>>
		implements CRUDEPService<ID, DTO> {

	protected S service;

	protected final ModelMapper modelMapper = new ModelMapper();
	
	private final Class<DTO> dtoClass;
	private final Class<T> entityClass;

	public CRUDEPBaseService(Class<T> theEntityClass, Class<DTO> theDtoClass) {
		dtoClass = theDtoClass;
		entityClass = theEntityClass;
	}
	
	@Override
	public DTO save(DTO dto) throws BusinessException {
		validationSave(dto);
		T entity = convertToEntityForSave(dto);
		service.save(entity);
		T saved = service.get(entity.getId());
		DTO convertToDto = convertToDto(saved);
		return convertToDto;
	}

	protected void validationSave(DTO dto) {
		// Implementar en los hijos
	}

	protected void validationUpdate(DTO dto) {
		// Implementar en los hijos
	}

	@Override
	public DTO update(DTO dto) throws BusinessException {
		T entity = convertToEntityForUpdate(dto);
		entity = service.update(entity);
		DTO convertToDto = convertToDto(entity);
		return convertToDto;
	}

	@Override
	public List<DTO> save(Collection<DTO> dtos) {
		List<T> entities = convertToEntities(dtos);
		entities = service.save(entities);
		return convertToDtos(entities);
	}

	@Override
	public DTO get(ID id) {
		T t = service.get(id);
		return convertToDto(t);
	}

	@Override
	public List<DTO> list() {
		List<T> entities = service.list();
		List<DTO> dtos = convertToDtos(entities);
		return dtos;
	}

	@Override
	public void remove(ID id) {
		service.remove(id);
	}

	@Override
	public int listCount(AbstractFilter abstractFilter) {
		return this.service.listCount(abstractFilter);
	}

	@Override
	public List<DTO> listFilter(AbstractFilter abstractFilter) {
		return convertToDtos(service.listFilter(abstractFilter));
	}

	@Override
	public List<DTO> listPattern(String field, Object pattern) {
		List<T> listPattern = this.service.listPattern(field, pattern);
		return convertToDtos(listPattern);
	}

	protected T convertToEntity(DTO dto) {
		return modelMapper.map(dto, entityClass);
	}

	protected DTO convertToDto(T entity) {
		return modelMapper.map(entity, dtoClass);
	}

	protected T convertToEntityForSave(DTO dto) {
		return convertToEntity(dto);
	}

	protected T convertToEntityForUpdate(DTO dto) {
		return convertToEntity(dto);
	}

	
	public List<T> convertToEntities(Collection<DTO> dtos) {
		List<T> entities = new ArrayList<T>();
		for (DTO dto : dtos) {
			T convertToEntity = convertToEntity(dto);
			entities.add(convertToEntity);
		}
		return entities;
	}

	public List<DTO> convertToDtos(Collection<T> entities) {
		List<DTO> dtos = new ArrayList<DTO>();
		for (T entity : entities) {
			dtos.add(convertToDto(entity));
		}
		return dtos;
	}

	protected S getService() {
		return this.service;
	}

	protected abstract void setService(S service);

	@Override
	public PageDTO<DTO> listFilterCount(AbstractFilter abstractFilter) {
		PageDTO<DTO> page = new PageDTO<DTO>();
		List<DTO> listFilter = listFilter(abstractFilter);
		int listCount = listCount(abstractFilter);
		page.setList(listFilter);
		page.setTotalReg(listCount);
		page.setPage(abstractFilter.getFirst());
		page.setPageSize(abstractFilter.getRows());
		return page;
	}

	@Override
	public <ID extends Serializable, D extends DTOEntity<ID>> PageDTO<D> listFilterCount(AbstractFilter abstractFilter,
			Class<D> targetDTO) {
		PageDTO<D> page = new PageDTO<>();
		List<D> listFilter = this.service.listFilter(abstractFilter, targetDTO);
		int listCount = listCount(abstractFilter);
		page.setList(listFilter);
		page.setTotalReg(listCount);
		return page;
	}
	
	@Override
	public List<DTO> listEq(String field, Object value) {
		return convertToDtos(this.service.listEqField(field, value));
	}
	
}
