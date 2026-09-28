package ar.com.avaco.fwk.core.component.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;

import ar.com.avaco.fwk.core.component.dto.entity.DTOEntity;
import ar.com.avaco.fwk.core.domain.Entity;
import ar.com.avaco.fwk.core.domain.filter.AbstractFilter;

@NoRepositoryBean
public interface NJRepository<ID extends Serializable, E extends Entity<ID>> extends JpaRepository<E, ID> {

	int listCount(AbstractFilter abstractFilter);

	List<E> listFilter(AbstractFilter abstractFilter);

	List<E> listPattern(String field, Object pattern);

	List<E> listEqField(String field, Object pattern);

	<ID extends Serializable, D extends DTOEntity<ID>> List<D> listFilter(AbstractFilter filter, Class<D> dtoClass);

	@Transactional(readOnly = false)
	void remove(ID id);
}