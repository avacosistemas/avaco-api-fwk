/**
 * 
 */
package ar.com.avaco.fwk.security.epservice;

import ar.com.avaco.fwk.core.component.epservice.ConvertService;
import ar.com.avaco.fwk.security.domain.Permiso;
import ar.com.avaco.fwk.security.dto.Permission;

/**
 * @author avaco
 *
 */
public interface PermissionService extends ConvertService<Permission, Long, Permiso>{
	
}
