package ar.com.avaco.fwk.security.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import ar.com.avaco.fwk.core.component.service.NJBaseService;
import ar.com.avaco.fwk.security.domain.Perfil;
import ar.com.avaco.fwk.security.domain.Permiso;
import ar.com.avaco.fwk.security.repository.PerfilRepository;
import ar.com.avaco.fwk.security.service.PerfilService;
import ar.com.avaco.fwk.security.service.PermisoService;

@Transactional
@Service("perfilService")
public class PerfilServiceImpl extends
		NJBaseService<Long, Perfil, PerfilRepository> implements
		PerfilService {

	private PermisoService permisoService;
	
	@Override
	public List<Permiso> listPermisosByPerfil(Long idPerfil) {
		Perfil one = this.repository.getOne(idPerfil);
		return new ArrayList<Permiso>(one.getPermisos());
	}
	
	@Resource(name = "perfilRepository")
	public void setPerfilRepository(PerfilRepository perfilRepository) {
		repository = perfilRepository;
	}

	@Override
	public void agregarPermisoAPerfil(Long idPerfil, Long idPermiso) {
		Permiso permiso = this.permisoService.get(idPermiso);
		Perfil one = this.repository.getOne(idPerfil);
		one.getPermisos().add(permiso);
		this.repository.save(one);
	}

	@Override
	public void quitarPermisoAPerfil(Long idPerfil, Long idPermiso) {
		Permiso permiso = this.permisoService.get(idPermiso);
		Perfil one = this.repository.getOne(idPerfil);
		one.getPermisos().remove(permiso);
		this.repository.save(one);
	}

	
	@Resource(name = "permisoService")
	public void setPermisoService(PermisoService permisoService) {
		this.permisoService = permisoService;
	}
}