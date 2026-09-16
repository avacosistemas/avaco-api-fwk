/**
 * 
 */
package ar.com.avaco.fwk.security.epservice;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import ar.com.avaco.fwk.core.component.epservice.CRUDEPBaseService;
import ar.com.avaco.fwk.security.domain.Permiso;
import ar.com.avaco.fwk.security.dto.PermisoDTO;
import ar.com.avaco.fwk.security.service.PermisoService;

/**
 * @author avaco
 *
 */
@Transactional
@Service("permisoEPService")
public class PermisoEPServiceImpl extends CRUDEPBaseService<Long, PermisoDTO, Permiso, PermisoService>
		implements PermisoEPService {

	public PermisoDTO convertToDto(Permiso permiso) {
		return new PermisoDTO(permiso.getId(), permiso.getCodigo(), permiso.getDescripcion(),
				permiso.getAuthority() != null);
	}

	@Override
	protected Permiso convertToEntity(PermisoDTO dto) {
		Permiso permiso = new Permiso();
		permiso.setActivo(true);
		permiso.setCodigo(dto.getCode());
		permiso.setDescripcion(dto.getDescription());
		return permiso;
	}

	@Override
	@Resource(name = "permisoService")
	protected void setService(PermisoService service) {
		this.service = service;

	}

}
