package ar.com.avaco.fwk.core.component.epservice;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import ar.com.avaco.fwk.core.component.dto.PageDTO;
import ar.com.avaco.fwk.core.component.dto.entity.DTOEntity;
import ar.com.avaco.fwk.core.domain.filter.AbstractFilter;
import ar.com.avaco.fwk.core.exception.BusinessException;

public interface CRUDEPService<ID extends Serializable, DTO extends DTOEntity<ID>> {

	DTO save(DTO entity) throws BusinessException;

	DTO update(DTO entity) throws BusinessException ;

	List<DTO> save(Collection<DTO> entities);

	DTO get(ID id);

	List<DTO> list();

	void remove(ID id) throws BusinessException;

	int listCount(AbstractFilter abstractFilter);

	List<DTO> listFilter(AbstractFilter abstractFilter);

	List<DTO> listPattern(String field, Object pattern);

	PageDTO<DTO> listFilterCount(AbstractFilter abstractFilter);
	
	List<DTO> listEq(String field, Object value);

	<ID extends Serializable, D extends DTOEntity<ID>> PageDTO<D> listFilterCount(AbstractFilter abstractFilter,
			Class<D> targetDTO);

}
