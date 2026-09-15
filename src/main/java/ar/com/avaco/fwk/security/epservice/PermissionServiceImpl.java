/**
 * 
 */
package ar.com.avaco.fwk.security.epservice;

import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import ar.com.avaco.fwk.core.component.epservice.AbstractConvertService;
import ar.com.avaco.fwk.security.domain.Permiso;
import ar.com.avaco.fwk.security.dto.Permission;
import ar.com.avaco.fwk.security.service.PermisoService;

/**
 * @author avaco
 *
 */
@Transactional
@Service("permissionService")
public class PermissionServiceImpl extends AbstractConvertService<Permission, Long, Permiso> implements PermissionService{
	
	public Permission convertToDto(Permiso permiso) {
		return new Permission(permiso.getId(), permiso.getCodigo(), permiso.getDescripcion(), permiso.getAuthority() != null );
	}
	
	@Resource(name = "permisoService")
	public void setPermisoService(PermisoService permisoService) {
		this.service = permisoService;
	}

	@Override
	protected Permiso newEntity() {
		return new Permiso();
	}

	@Override
	public Permiso convertToEntity(Permiso entity, Permission dto) {
		entity.setActivo(true);
		entity.setCodigo(dto.getCode());
		entity.setDescripcion(dto.getDescription());
		return entity;
	}

	@Override
	public List<Permission> listPattern(String field, Object pattern) {
		// TODO Auto-generated method stub
		return null;
	}

}
