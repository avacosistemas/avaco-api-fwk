package ar.com.avaco.fwk.security.repository;

import ar.com.avaco.fwk.core.component.repository.NJRepository;
import ar.com.avaco.fwk.security.domain.Permiso;

public interface PermisoRepository extends NJRepository<Long, Permiso>,
		PermisoRepositoryCustom {
	
}