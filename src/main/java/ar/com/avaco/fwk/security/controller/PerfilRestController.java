package ar.com.avaco.fwk.security.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ar.com.avaco.fwk.core.component.controller.AbstractDTORestController;
import ar.com.avaco.fwk.core.component.dto.JSONResponse;
import ar.com.avaco.fwk.core.exception.BusinessException;
import ar.com.avaco.fwk.security.dto.PerfilDTO;
import ar.com.avaco.fwk.security.epservice.PerfilEPService;

@RestController
public class PerfilRestController extends AbstractDTORestController<PerfilDTO, Long, PerfilEPService> {

	@RequestMapping(value = "/profiles", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<JSONResponse> list() {
		return super.list();
	}

	@RequestMapping(value = "/profiles/filterByName", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<JSONResponse> list(@RequestParam String name) {
		List<PerfilDTO> listPattern = this.service.listPattern("nombre", name);
		JSONResponse response = new JSONResponse();
		response.setData(listPattern);
		return new ResponseEntity<JSONResponse>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/profiles/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<JSONResponse> get(@PathVariable("id") Long id) throws BusinessException {
		return super.get(id);
	}

	@RequestMapping(value = "/profiles", method = RequestMethod.POST)
	public ResponseEntity<JSONResponse> create(@RequestBody PerfilDTO profile) throws BusinessException {
		return super.create(profile);
	}

	@RequestMapping(value = "/profiles", method = RequestMethod.PUT)
	public ResponseEntity<JSONResponse> update(@RequestBody PerfilDTO profile) throws BusinessException {
		return super.update(profile.getId(), profile);
	}

	@RequestMapping(value = "/profiles/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<JSONResponse> delete(@PathVariable("id") Long id) throws BusinessException {
		ResponseEntity<JSONResponse> resp;
		try {
			resp = super.delete(id);
		} catch (DataIntegrityViolationException e) {
			throw new BusinessException("No se puede borrar el Perfil porque existe una relacion con Acceso");
		} catch (Exception e) {
			throw new BusinessException("Ocurrio un error al eliminar el perfil");
		}

		return resp;
	}

	@Override
	@Resource(name = "perfilEPService")
	public void setService(PerfilEPService service) {
		super.service = service;

	}

}