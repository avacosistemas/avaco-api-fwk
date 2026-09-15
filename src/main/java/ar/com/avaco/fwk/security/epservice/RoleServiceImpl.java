/**
 * 
 */
package ar.com.avaco.fwk.security.epservice;

import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import ar.com.avaco.fwk.core.component.epservice.AbstractConvertService;
import ar.com.avaco.fwk.security.domain.Rol;
import ar.com.avaco.fwk.security.dto.Role;
import ar.com.avaco.fwk.security.service.RolService;

/**
 * @author avaco
 *
 */
@Transactional
@Service("roleService")
public class RoleServiceImpl extends AbstractConvertService<Role, Long, Rol> implements RoleService{
	
	public Role convertToDto(Rol permiso) {
		return new Role(permiso.getId(), permiso.getCodigo(), permiso.getNombre());
	}

	@Resource(name = "rolService")
	public void setRolService(RolService rolService) {
		this.service = rolService;
	}

	@Override
	protected Rol newEntity() {
		return new Rol();
	}

	@Override
	public Rol convertToEntity(Rol entity, Role dto) {
		entity.setNombre(dto.getName());
		entity.setCodigo(dto.getCode());
		return entity;
	}

	@Override
	public List<Role> listPattern(String field, Object pattern) {
		return null;
	}
}
