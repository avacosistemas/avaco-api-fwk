package ar.com.avaco.fwk.core.component.repository;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.hibernate.SessionFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.data.repository.core.RepositoryInformation;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;

public class NJRepositoryFactoryBean<R extends JpaRepository<T, I>, T, I extends Serializable>
        extends JpaRepositoryFactoryBean<R, T, I> {

    public NJRepositoryFactoryBean(Class<? extends R> repositoryInterface) {
        super(repositoryInterface);
    }

    @Override
    protected RepositoryFactorySupport createRepositoryFactory(EntityManager entityManager) {
        return new NJRepositoryFactory(entityManager);
    }

    private static class NJRepositoryFactory<T, I extends Serializable>
            extends JpaRepositoryFactory {

        private final EntityManager entityManager;

        public NJRepositoryFactory(EntityManager entityManager) {
            super(entityManager);
            this.entityManager = entityManager;
        }

        @Override
        protected JpaRepositoryImplementation<?, ?> getTargetRepository(
                RepositoryInformation information,
                EntityManager entityManager) {

            NJBaseRepository repository =
                    new NJBaseRepository(
                            information.getDomainType(),
                            entityManager);

            repository.setSessionFactory(getSessionFactory());

            return repository;
        }

        private SessionFactory getSessionFactory() {
            EntityManagerFactory entityManagerFactory =
                    entityManager.getEntityManagerFactory();

            return entityManagerFactory.unwrap(SessionFactory.class);
        }

        @Override
        protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
            return NJRepository.class;
        }
    }
}