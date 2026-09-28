package ar.com.avaco.fwk.commons.epservice;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.com.avaco.fwk.commons.domain.AyudaDinamica;
import ar.com.avaco.fwk.commons.dto.AyudaDinamicaDTO;
import ar.com.avaco.fwk.commons.service.AyudaDinamicaService;
import ar.com.avaco.fwk.core.component.epservice.CRUDAuditableEPBaseService;

@Transactional
@Service("ayudaDinamicaEPService")
public class AyudaDinamicaEPServiceImpl extends CRUDAuditableEPBaseService<Long, AyudaDinamicaDTO, AyudaDinamica, AyudaDinamicaService>
		implements AyudaDinamicaEPService {

	public AyudaDinamicaEPServiceImpl() {
		super(AyudaDinamica.class, AyudaDinamicaDTO.class);
	}

	@Override
	@Resource(name = "ayudaDinamicaService")
	protected void setService(AyudaDinamicaService service) {
		this.service = service;
	}

}