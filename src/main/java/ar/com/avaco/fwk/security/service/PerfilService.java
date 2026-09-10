package ar.com.avaco.fwk.security.service;

import java.util.List;

import ar.com.avaco.fwk.core.component.service.NJService;
import ar.com.avaco.fwk.security.domain.Perfil;
import ar.com.avaco.fwk.security.domain.Permiso;

/**
 * @author avaco
 */
public interface PerfilService extends NJService<Long, Perfil> {

	List<Permiso> listPermisosByPerfil(Long idPerfil);

	void agregarPermisoAPerfil(Long idPerfil, Long idPermiso);

	void quitarPermisoAPerfil(Long idPerfil, Long idPermiso);

}