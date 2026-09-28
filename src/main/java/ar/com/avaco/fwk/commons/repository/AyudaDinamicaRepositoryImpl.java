package ar.com.avaco.fwk.commons.repository;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import ar.com.avaco.fwk.commons.domain.AyudaDinamica;
import ar.com.avaco.fwk.core.component.repository.NJBaseRepository;

@Repository("ayudaDinamicaRepository")
public class AyudaDinamicaRepositoryImpl extends NJBaseRepository<Long, AyudaDinamica> implements AyudaDinamicaRepositoryCustom {

	public AyudaDinamicaRepositoryImpl(EntityManager entityManager) {
		super(AyudaDinamica.class, entityManager);
	}

}