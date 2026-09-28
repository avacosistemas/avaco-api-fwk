package ar.com.avaco.fwk.core.jpa;

import javax.persistence.EntityManager;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.context.spi.CurrentSessionContext;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.springframework.orm.jpa.EntityManagerHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 * @author AFlores
 */
public class EntityManagerSessionUnwrapper implements CurrentSessionContext {

	private static final long serialVersionUID = 1L;

	public EntityManagerSessionUnwrapper() {
	}

	public EntityManagerSessionUnwrapper(SessionFactory sessionFactory) {
	}

	public EntityManagerSessionUnwrapper(SessionFactoryImplementor sessionFactory) {
	}

	@Override
	public Session currentSession() throws HibernateException {
		for (Object v : TransactionSynchronizationManager.getResourceMap().values()) {
			if (v instanceof EntityManagerHolder) {
				EntityManagerHolder emh = (EntityManagerHolder) v;
				return getSessionFromEM(emh.getEntityManager());
			}
		}

		return null;
	}

	private static Session getSessionFromEM(EntityManager entityManager) {
		try {
			return entityManager.unwrap(Session.class);
		} catch (Exception e) {
			throw new HibernateException("No Session found", e);
		}
	}
}