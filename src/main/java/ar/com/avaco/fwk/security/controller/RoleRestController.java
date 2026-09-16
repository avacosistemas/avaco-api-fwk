package ar.com.avaco.fwk.security.controller;

import javax.annotation.Resource;

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
import ar.com.avaco.fwk.security.dto.RolDTO;
import ar.com.avaco.fwk.security.epservice.RolEPService;

@RestController
public class RoleRestController extends AbstractDTORestController<RolDTO, Long, RolEPService> {

	@RequestMapping(value = "/roles/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<JSONResponse> list() {
		return super.list();
	}

	@RequestMapping(value = "/roles/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<JSONResponse> get(@PathVariable("id") Long id) throws BusinessException {
		return super.get(id);
	}

	@RequestMapping(value = "/roles/", method = RequestMethod.POST)
	public ResponseEntity<JSONResponse> create(@RequestBody RolDTO role) throws BusinessException {
		return super.create(role);
	}

	@RequestMapping(value = "/roles/{id}", method = RequestMethod.PUT)
	public ResponseEntity<JSONResponse> update(@PathVariable("id") Long id, @RequestBody RolDTO role)
			throws BusinessException {
		return super.update(id, role);
	}

	@RequestMapping(value = "/roles/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<JSONResponse> delete(@PathVariable("id") Long id) throws BusinessException {
		return super.delete(id);
	}

	public void setRoleService(RolEPService roleService) {
		super.service = roleService;
	}

	@Override
	@Resource(name = "rolEPService")
	public void setService(RolEPService service) {
		super.service = service;

	}

}