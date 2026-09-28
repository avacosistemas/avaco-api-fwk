/**
 * 
 */
package ar.com.avaco.fwk.security.epservice;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import ar.com.avaco.fwk.core.component.epservice.CRUDEPBaseService;
import ar.com.avaco.fwk.security.domain.Rol;
import ar.com.avaco.fwk.security.dto.RolDTO;
import ar.com.avaco.fwk.security.service.RolService;

/**
 * @author avaco
 *
 */
@Transactional
@Service("rolEPService")
public class RolEPServiceImpl extends CRUDEPBaseService<Long, RolDTO, Rol, RolService> implements RolEPService {

	public RolEPServiceImpl() {
		super(Rol.class, RolDTO.class);
	}

	public RolDTO convertToDto(Rol permiso) {
		return new RolDTO(permiso.getId(), permiso.getCodigo(), permiso.getNombre());
	}

	@Override
	protected Rol convertToEntity(RolDTO dto) {
		Rol entity = new Rol();
		entity.setNombre(dto.getName());
		entity.setCodigo(dto.getCode());
		return entity;
	}

	@Override
	@Resource(name = "rolService")
	protected void setService(RolService service) {
		this.service = service;
	}

}
