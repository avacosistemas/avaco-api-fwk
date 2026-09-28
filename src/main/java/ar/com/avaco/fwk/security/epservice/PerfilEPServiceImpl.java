/**
 * 
 */
package ar.com.avaco.fwk.security.epservice;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.avaco.fwk.core.component.epservice.CRUDEPBaseService;
import ar.com.avaco.fwk.security.domain.Perfil;
import ar.com.avaco.fwk.security.domain.Permiso;
import ar.com.avaco.fwk.security.domain.Rol;
import ar.com.avaco.fwk.security.dto.PerfilDTO;
import ar.com.avaco.fwk.security.dto.PermisoDTO;
import ar.com.avaco.fwk.security.dto.RolDTO;
import ar.com.avaco.fwk.security.service.PerfilService;
import ar.com.avaco.fwk.security.service.RolService;

/**
 * @author avaco
 *
 */
@Transactional
@Service("perfilEPService")
public class PerfilEPServiceImpl extends CRUDEPBaseService<Long, PerfilDTO, Perfil, PerfilService>
		implements PerfilEPService {

	public PerfilEPServiceImpl() {
		super(Perfil.class, PerfilDTO.class);
	}

	@Autowired
	private RolService rolService;

	public PerfilDTO convertToDto(Perfil perfil) {
		List<PermisoDTO> permisos = new ArrayList<PermisoDTO>();
		for (Permiso p : perfil.getPermisos()) {
			PermisoDTO dto = new PermisoDTO(p.getId(), p.getCodigo(), p.getDescripcion(), p.getAuthority() != null);
			permisos.add(dto);
		}

		RolDTO rolDTO = new RolDTO();
		rolDTO.setCode(perfil.getRol().getCodigo());
		rolDTO.setId(perfil.getRol().getId());
		rolDTO.setName(perfil.getRol().getNombre());

		return new PerfilDTO(perfil.getId(), perfil.getNombre(), rolDTO, permisos, perfil.isActivo());
	}

	@Override
	public Perfil convertToEntity(PerfilDTO dto) {
		Perfil entity = new Perfil();

		if (dto.getId() == null) {
			entity = new Perfil();
			entity.setActivo(true);
			Rol rol = rolService.list().stream().filter(role -> role.getCodigo().equals("ADM")).findFirst()
					.orElse(new Rol());
			entity.setRol(rol);
		} else {
			entity = this.service.get(dto.getId());
		}

		entity.setNombre(dto.getName());

//		//FIXME Por default tiene el rol ADM, modificar cuando se requiera
//		List<Permiso> permisos = new  ArrayList<>();
//		for(PermisoDTO permission :dto.getPermissions()) {
//			Permiso p = new Permiso();
//			p.setId(permission.getId());
//			permisos.add(permisoEPService.convertToEntity(p, permission));
//		}
//		entity.getPermisos().addAll(permisos);
		return entity;
	}

	@Override
	@Resource(name = "perfilService")
	protected void setService(PerfilService service) {
		this.service = service;
	}

}
