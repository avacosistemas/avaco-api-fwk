/**
 * 
 */
package ar.com.avaco.fwk.security.epservice;

import java.util.List;

import ar.com.avaco.fwk.core.component.epservice.CRUDEPService;
import ar.com.avaco.fwk.security.dto.AccesoDTO;

/**
 * @author avaco
 *
 */
public interface AccesoEPService extends CRUDEPService<Long, AccesoDTO> {

	List<AccesoDTO> list(Long usuarioId);

	void delete(Long id, Long idUsuario);

}
