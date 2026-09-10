package ar.com.avaco.fwk.security.repository;

import javax.persistence.EntityManager;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import ar.com.avaco.fwk.core.component.repository.NJBaseRepository;
import ar.com.avaco.fwk.security.domain.Perfil;

/**
 * @author avaco
 */
@Repository("perfilRepository")
public class PerfilRepositoryImpl extends NJBaseRepository<Long, Perfil> implements PerfilRepository {

	public PerfilRepositoryImpl(EntityManager entityManager) {
		super(Perfil.class, entityManager);
	}

	@Override
	public boolean existePerfil(String nombre, Long rolId) {
		Criteria c = this.getCurrentSession().createCriteria(this.getHandledClass());
		c.add(Restrictions.eq("nombre", nombre));
		c.add(Restrictions.eq("rol.id", rolId));
		
		return c.uniqueResult() != null;
	}
}