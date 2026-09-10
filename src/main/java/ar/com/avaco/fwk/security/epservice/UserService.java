/**
 * 
 */
package ar.com.avaco.fwk.security.epservice;

import ar.com.avaco.fwk.core.component.epservice.ConvertService;
import ar.com.avaco.fwk.core.exception.ErrorValidationException;
import ar.com.avaco.fwk.security.domain.Usuario;
import ar.com.avaco.fwk.security.dto.UpdatePasswordDTO;
import ar.com.avaco.fwk.security.dto.User;

/**
 * @author avaco
 *
 */
public interface UserService extends ConvertService<User, Long, Usuario>{
	void updatePassword(UpdatePasswordDTO resetPassword);
	void updateValidation(User user) throws ErrorValidationException;
	User saveUser(User user) throws ErrorValidationException;
	User getByUsername(String username);
}
