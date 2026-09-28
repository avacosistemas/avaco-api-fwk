package ar.com.avaco.fwk.commons.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ar.com.avaco.fwk.commons.dto.AyudaDinamicaDTO;
import ar.com.avaco.fwk.commons.epservice.AyudaDinamicaEPService;
import ar.com.avaco.fwk.core.component.controller.AbstractDTORestController;
import ar.com.avaco.fwk.core.component.dto.JSONResponse;
import ar.com.avaco.fwk.core.exception.BusinessException;

@RestController
public class AyudaDinamicaRestController extends AbstractDTORestController<AyudaDinamicaDTO, Long, AyudaDinamicaEPService> {

	@RequestMapping(value = "/ayuda-dinamica", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<JSONResponse> get(@RequestParam String path) {
		List<AyudaDinamicaDTO> list = this.service.listEq("path", path);
		AyudaDinamicaDTO ayuda = new AyudaDinamicaDTO();
		if (!list.isEmpty()) ayuda = list.get(0);
		return OKDATA(ayuda);
	}

	@Override
	@RequestMapping(value = "/ayuda-dinamica", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<JSONResponse> create(@RequestBody AyudaDinamicaDTO dto) throws BusinessException {
		return 	super.create(dto);
	}

	@Override
	@RequestMapping(value = "/ayuda-dinamica/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<JSONResponse> update(@PathVariable Long id, @RequestBody AyudaDinamicaDTO dto)
			throws BusinessException {
		return super.update(id, dto);
	}


	@Resource(name = "ayudaDinamicaEPService")
	public void setService(AyudaDinamicaEPService ayudaDinamicaEPService) {
		super.service = ayudaDinamicaEPService;
	}

}