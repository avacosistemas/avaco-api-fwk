package ar.com.avaco.fwk.security.controller;

import javax.annotation.Resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ar.com.avaco.fwk.core.component.controller.AbstractDTORestController;
import ar.com.avaco.fwk.core.component.dto.JSONResponse;
import ar.com.avaco.fwk.core.exception.BusinessException;
import ar.com.avaco.fwk.security.dto.UsuarioDTO;
import ar.com.avaco.fwk.security.epservice.UsuarioEPService;

/**
 * @author beto
 *
 */
@RestController
public class UserRestController extends AbstractDTORestController<UsuarioDTO, Long, UsuarioEPService> {

	@RequestMapping(value = "/users/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<JSONResponse> list() {
		return super.list();
	}

	@RequestMapping(value = "/users/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<JSONResponse> get(@PathVariable("id") Long id) throws BusinessException {
		return super.get(id);
	}

	@RequestMapping(value = "/users/", method = RequestMethod.POST)
	public ResponseEntity<JSONResponse> create(@RequestBody UsuarioDTO user) throws BusinessException {
		UsuarioDTO result = this.service.save(user);
		return new ResponseEntity<JSONResponse>(getResponseOK(result), HttpStatus.CREATED);
	}

	@RequestMapping(value = "/users/", method = RequestMethod.PUT)
	public ResponseEntity<JSONResponse> update(@RequestBody UsuarioDTO user) throws BusinessException {
		return super.update(user.getId(), user);
	}

	@RequestMapping(value = "/users/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<JSONResponse> delete(@PathVariable("id") Long id) throws BusinessException {
		return super.delete(id);
	}

	@Override
	@Resource(name = "usuarioEPService")
	public void setService(UsuarioEPService service) {
		super.service = service;
	}

}