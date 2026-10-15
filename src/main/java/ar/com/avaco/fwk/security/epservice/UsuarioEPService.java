/**
 * 
 */
package ar.com.avaco.fwk.security.epservice;

import java.util.List;

import ar.com.avaco.fwk.core.component.epservice.CRUDEPService;
import ar.com.avaco.fwk.core.exception.ErrorValidationException;
import ar.com.avaco.fwk.security.dto.UsuarioDTO;

/**
 * @author avaco
 *
 */
public interface UsuarioEPService extends CRUDEPService<Long, UsuarioDTO> {

    void updateValidation(UsuarioDTO user) throws ErrorValidationException;

	List<UsuarioDTO> findByPermiso(String permiso);

}
