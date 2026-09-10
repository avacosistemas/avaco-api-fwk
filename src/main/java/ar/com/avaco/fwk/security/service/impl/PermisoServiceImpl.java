package ar.com.avaco.fwk.security.service.impl;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import ar.com.avaco.fwk.core.component.service.NJBaseService;
import ar.com.avaco.fwk.security.domain.Permiso;
import ar.com.avaco.fwk.security.repository.PermisoRepository;
import ar.com.avaco.fwk.security.service.PermisoService;

@Transactional
@Service("permisoService")
public class PermisoServiceImpl extends
	NJBaseService<Long, Permiso, PermisoRepository> implements
		PermisoService {

	@Resource(name = "permisoRepository")
	public void setPermisoRepository(PermisoRepository permisoRepository) {
		repository = permisoRepository;
	}
}