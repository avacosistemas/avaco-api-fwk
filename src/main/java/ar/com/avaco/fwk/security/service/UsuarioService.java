package ar.com.avaco.fwk.security.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import ar.com.avaco.fwk.core.component.service.NJService;
import ar.com.avaco.fwk.security.domain.Usuario;
import ar.com.avaco.fwk.security.exception.NuclearJSecurityException;

/**
 * the user service.
 * 
 * @author aogonzalez
 */
public interface UsuarioService extends NJService<Long, Usuario> {

	/**
	 * Updates the password of the user encrypting it.
	 * @param user The user to be used to set the new password.
	 * @param newPassword the new password with no encryption.
	 * @param user the user to update the password.
	 */
	void updatePassword(Usuario user, String password,String newPassword);

	/**
	 * Checks if the user exists.
	 * @param username
	 * @return
	 */
	boolean isUserExists(String username);
	
	void sendMissingPassword(String username);
	
	void sendMissingPasswordById(Long id);

	UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
	
	void generateNewPassword(Usuario user);
	
	Usuario findById(Long id);
	
	boolean isUserExistWithEmail(String email);
	
	Usuario findByUsername(String username);

	List<Usuario> getByIds(List<Long> lista);

	void validarUsuario(Usuario usuario) throws NuclearJSecurityException;

	/**
	 * Genera un string en forma aleatoria para ser usado de password.
	 * 
	 * @return un string de 8 caracteres.
	 */
	String generarPasswordAleatorio();

	String encodePassword(String tmppass);

	void notifyPasswordNewUser(Usuario user, String tmpass);
	
}

