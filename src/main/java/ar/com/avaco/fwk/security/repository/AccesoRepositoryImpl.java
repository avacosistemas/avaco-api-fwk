package ar.com.avaco.fwk.security.repository;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import ar.com.avaco.fwk.core.component.repository.NJBaseRepository;
import ar.com.avaco.fwk.security.domain.Acceso;

/**
 * Repositorio de Accesos
 * 
 * @author betongo
 *
 */
@Repository("accesoRepository")
public class AccesoRepositoryImpl extends NJBaseRepository<Long, Acceso> implements AccesoRepositoryCustom {

	public AccesoRepositoryImpl(EntityManager entityManager) {
		super(Acceso.class, entityManager);
	}

}
