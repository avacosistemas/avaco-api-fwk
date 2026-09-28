package ar.com.avaco.fwk.commons.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.com.avaco.fwk.commons.domain.AyudaDinamica;
import ar.com.avaco.fwk.commons.repository.AyudaDinamicaRepository;
import ar.com.avaco.fwk.core.component.service.NJBaseService;

@Transactional
@Service("ayudaDinamicaService")
public class AyudaDinamicaServiceImpl extends NJBaseService<Long, AyudaDinamica, AyudaDinamicaRepository> implements AyudaDinamicaService {

	@Resource(name = "ayudaDinamicaRepository")
	void setRepository(AyudaDinamicaRepository ayudaDinamicaRepository) {
		this.repository = ayudaDinamicaRepository;
	}

}
