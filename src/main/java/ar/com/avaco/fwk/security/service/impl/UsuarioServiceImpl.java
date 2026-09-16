package ar.com.avaco.fwk.security.service.impl;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.com.avaco.fwk.commons.service.mail.MailSenderSMTPService;
import ar.com.avaco.fwk.core.component.service.NJBaseService;
import ar.com.avaco.fwk.core.exception.ErrorValidationException;
import ar.com.avaco.fwk.security.domain.Usuario;
import ar.com.avaco.fwk.security.exception.NuclearJSecurityException;
import ar.com.avaco.fwk.security.repository.UsuarioRepository;
import ar.com.avaco.fwk.security.service.UsuarioService;

@Transactional
@Service("usuarioService")
public class UsuarioServiceImpl extends NJBaseService<Long, Usuario, UsuarioRepository>
		implements UsuarioService, UserDetailsService {

	private static final Integer INICIO_REINTENTOS_LOGIN = 0;
	private static final String USER_NEWPASSWORD_EQUALS_CURRENT = "user.newpassword.currentpassword.equals";
	private static final String USER_CURRENT_PASSWORD_INVALID = "user.currentpassword.invalid";

	@Value("${email.from}")
	private String from;

	@Value("${email.cc}")
	private String cc;
	
	@Value("${email.subject.register}")
	private String subjectRegister;

	@Value("${email.body.register}")
	private String bodyRegister;

	@Value("${email.subject.resetPassword}")
	private String subjectResetPassword;
	
	@Value("${email.body.resetPassword}")
	private String bodyResetPassword;
	
	/**
	 * The Password Encoder
	 */
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private MailSenderSMTPService mailSenderSMTPService;

	@Override
	public void updatePassword(Usuario user, String password, String newPassword) {
		if (!passwordEncoder.matches(password, user.getPassword())) {
			throw new NuclearJSecurityException(USER_CURRENT_PASSWORD_INVALID);
		} else if (password.equals(newPassword)) {
			throw new NuclearJSecurityException(USER_NEWPASSWORD_EQUALS_CURRENT);
		}
		user.setPassword(encodePassword(newPassword));
		user.setFechaAltaPassword(Calendar.getInstance().getTime());
		user.setRequiereCambioPassword(Boolean.FALSE);
		getRepository().save(user);
	}

	@Override
	public Usuario save(Usuario usuario) throws NuclearJSecurityException {
		validarUsuario(usuario);

		// Por default el usuario se da de desbloqueado.
		usuario.setBloqueado(false);

		// La cantidad de intentos de login es cero.
		usuario.setIntentosFallidosLogin(INICIO_REINTENTOS_LOGIN);

		// Por mas que luego se reenviara el password, se requerira cambio de
		// password.
		usuario.setRequiereCambioPassword(false);

		String tmppass = generarPasswordAleatorio();
		usuario.setPassword(encodePassword(tmppass));

		usuario = getRepository().save(usuario);
		
		notifyPasswordNewUser(usuario, tmppass);

		return usuario;
	}

	@Override
	public String encodePassword(String tmppass) {
		return passwordEncoder.encode(tmppass);
	}

	@Override
	public void validarUsuario(Usuario usuario) throws NuclearJSecurityException {
		Map<String, String> errors = new HashMap<String, String>();
		String username = usuario.getUsername();
		Usuario userByUsername = getRepository().findByUsername(username);
		if (userByUsername != null) {
			errors.put("username", "Ya existe un usuario registrado con ese Nombre de Usuario.");
		}
		String mail = usuario.getEmail();
		Usuario userByMail = getRepository().findByEmail(mail);
		if (userByMail != null) {
			errors.put("email", "Ya existe un usuario registrado con ese Email.");
		}
		if (!errors.isEmpty()) {
			throw new ErrorValidationException("Se han encontrado los siguientes errores", errors);
		}

	}

	/**
	 * Genera un string en forma aleatoria para ser usado de password.
	 * 
	 * @return un string de 8 caracteres.
	 */
	@Override
	public String generarPasswordAleatorio() {
		String generateKey = KeyGenerators.string().generateKey();
		return generateKey;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return findByUsername(username);
	}

	@Override
	public boolean isUserExists(String username) {
		return getRepository().findByUsername(username) != null;
	}

	@Override
	public void sendMissingPassword(String username) {
		Usuario user = getRepository().findByUsername(username);
		if (user == null) {
			user = getRepository().findByEmail(username);
		}
		if (user != null) {
			generateNewPassword(user);
		} else {
			throw new UsernameNotFoundException("No se ha podido identificar al usuario");
		}
	}

	@Override
	public void sendMissingPasswordById(Long id) {
		Usuario user = getRepository().findOne(id);

		if (user != null) {
			generateNewPassword(user);
		} else {
			throw new UsernameNotFoundException("No se ha podido identificar al usuario");
		}
	}

	@Override
	public void generateNewPassword(Usuario user) {
		user.addPasswordToHistoric(user.getPassword());
		String tmppass = generarPasswordAleatorio();
		String enctmppass = encodePassword(tmppass);
		user.setPassword(enctmppass);
		user.setIntentosFallidosLogin(0);
		user.setRequiereCambioPassword(Boolean.TRUE);
		update(user);
		notifyPassword(user, tmppass);
	}

	@Override
	public void notifyPasswordNewUser(Usuario user, String tmpass) {
		String subject = subjectRegister;
		String body = bodyRegister;
		body = body.replaceAll("%nombreCompleto%", user.getNombreApellido());
		body = body.replaceAll("%username%", user.getUsername());
		body = body.replaceAll("%tmpass%", tmpass);
		mailSenderSMTPService.sendMail(from, user.getEmail(), cc, subject, body, null);
	}

	private void notifyPassword(Usuario user, String tmppas) {
		String subject = subjectResetPassword;
		String body = bodyResetPassword;
		body = body.replaceAll("%nombreCompleto%", user.getNombreApellido());
		body = body.replaceAll("%username%", user.getUsername());
		body = body.replaceAll("%tmppas%", tmppas);
		mailSenderSMTPService.sendMail(from, user.getEmail(), cc, subject, body, null);
	}

	public Usuario findById(Long id) {
		return getRepository().findOne(id);
	}

	@Override
	public boolean isUserExistWithEmail(String email) {
		return getRepository().isUserExistWithEmail(email);
	}

	@Override
	public Usuario findByUsername(String username) {
		Usuario user = getRepository().findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("Usuario " + username + "not found");
		}
		return user;
	}

	@Override
	public List<Usuario> getByIds(List<Long> lista) {
		return this.repository.findByIdIn(lista);
	}

	@Resource(name = "usuarioRepository")
	public void setUsuarioRepository(UsuarioRepository usuarioRepository) {
		this.repository = usuarioRepository;
	}

}