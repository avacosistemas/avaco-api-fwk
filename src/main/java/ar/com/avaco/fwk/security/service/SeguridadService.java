package ar.com.avaco.fwk.security.service;

import java.io.Serializable;
import java.util.List;

import ar.com.avaco.fwk.security.domain.Acceso;
import ar.com.avaco.fwk.security.domain.Usuario;

/**
 * Facade para validaciones de seguridad
 * 
 * @author HDimitri
 */
public interface SeguridadService extends Serializable {
	
	List<Acceso> getAccesosConPermiso(Usuario usuario, String permiso);
	
	boolean hasPermiso(Usuario usuario, String permiso);

	boolean hasRol(Usuario usuario, String permiso, String rol);

	boolean hasRolYPermisos(Usuario usuario, List<String> permisos, String rol);
}