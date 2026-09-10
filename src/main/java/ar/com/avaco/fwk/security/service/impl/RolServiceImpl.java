/**
 * 
 */
package ar.com.avaco.fwk.security.service.impl;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import ar.com.avaco.fwk.core.component.service.NJBaseService;
import ar.com.avaco.fwk.security.domain.Rol;
import ar.com.avaco.fwk.security.repository.RolRepository;
import ar.com.avaco.fwk.security.service.RolService;

/**
 * @author avaco
 */
@Transactional
@Service("rolService")
public class RolServiceImpl extends NJBaseService<Long, Rol, RolRepository>
		implements RolService {

	@Resource(name = "rolRepository")
	public void setRolRepository(RolRepository rolRepository) {
		repository = rolRepository;
	}
}