package ar.com.avaco.fwk.security.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

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
import ar.com.avaco.fwk.security.dto.PermisoDTO;
import ar.com.avaco.fwk.security.epservice.PermisoEPService;

@RestController
public class PermisoRestController extends AbstractDTORestController<PermisoDTO, Long, PermisoEPService>{

	// -------------------Retrieve All
	// permisos--------------------------------------------------------

	@RequestMapping(value = "/permissions", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<JSONResponse> list() {
		return super.list();
	}

	@RequestMapping(value = "/permissions/filterPermisoByNombre", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<JSONResponse> listFilter(@RequestParam String name) {
		List<PermisoDTO> pornombre = this.service.listPattern("codigo", name);
		List<PermisoDTO> porcodigo = this.service.listPattern("descripcion", name);
		
		Set<PermisoDTO> permisos = new HashSet<>();
		permisos.addAll(pornombre);
		permisos.addAll(porcodigo);
		
		JSONResponse response = new JSONResponse();
		response.setData(permisos);
		return new ResponseEntity<JSONResponse>(response, HttpStatus.OK);
	}

	// -------------------Retrieve single
	// Pages--------------------------------------------------------
	@RequestMapping(value = "/permissions/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<JSONResponse> get(@PathVariable("id") Long id) throws BusinessException {
		return super.get(id);
	}

	// -------------------Create a
	// Page--------------------------------------------------------
	@RequestMapping(value = "/permissions", method = RequestMethod.POST)
	public ResponseEntity<JSONResponse> create(@RequestBody PermisoDTO permission) throws BusinessException {
		return super.create(permission);
	}

	// ------------------- Update a Page
	// --------------------------------------------------------
	@RequestMapping(value = "/permissions", method = RequestMethod.PUT)
	public ResponseEntity<JSONResponse> update(@RequestBody PermisoDTO permission) throws BusinessException {
		return super.update(permission.getId(), permission);
	}
	// ------------------- Delete a Page
	// --------------------------------------------------------

	@RequestMapping(value = "/permissions/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<JSONResponse> delete(@PathVariable("id") Long id) throws BusinessException {
		return super.delete(id);
	}

	@Override
	@Resource(name = "permisoEPService")
	public void setService(PermisoEPService service) {
		super.service = service;
		
	}

}