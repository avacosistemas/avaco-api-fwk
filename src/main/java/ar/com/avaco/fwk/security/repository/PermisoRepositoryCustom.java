package ar.com.avaco.fwk.security.repository;

import ar.com.avaco.fwk.security.domain.Permiso;

public interface PermisoRepositoryCustom {	
	Permiso getPermisoPorCodigo(String codigo);
}