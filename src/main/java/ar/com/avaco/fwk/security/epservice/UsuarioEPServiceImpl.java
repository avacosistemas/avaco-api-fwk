/**
 * 
 */
package ar.com.avaco.fwk.security.epservice;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.avaco.fwk.core.component.epservice.CRUDEPBaseService;
import ar.com.avaco.fwk.core.exception.ErrorValidationException;
import ar.com.avaco.fwk.security.domain.Acceso;
import ar.com.avaco.fwk.security.domain.Perfil;
import ar.com.avaco.fwk.security.domain.Usuario;
import ar.com.avaco.fwk.security.dto.PerfilDTO;
import ar.com.avaco.fwk.security.dto.UsuarioDTO;
import ar.com.avaco.fwk.security.service.PerfilService;
import ar.com.avaco.fwk.security.service.UsuarioService;

/**
 * @author avaco
 *
 */
@Transactional
@Service("usuarioEPService")
public class UsuarioEPServiceImpl extends CRUDEPBaseService<Long, UsuarioDTO, Usuario, UsuarioService>
		implements UsuarioEPService {

	@Autowired
	private PerfilService perfilService;

	public UsuarioDTO convertToDto(Usuario usuario) {
		Set<PerfilDTO> profiles = new HashSet<>();
		if (usuario.getAccesos() != null) {
			usuario.getAccesos().stream().forEach(e -> {
				PerfilDTO pdto = new PerfilDTO();
				pdto.setId(e.getPerfil().getId());
				profiles.add(pdto);
			});
		}

		return new UsuarioDTO(usuario.getId(), usuario.getUsername(), usuario.getNombre(), usuario.getApellido(),
				profiles, usuario.getEmail(), usuario.isEnabled(), usuario.getAdmin());
	}

	@Override
	public Usuario convertToEntity(UsuarioDTO dto) {
		Usuario entity = new Usuario();
		entity.setApellido(dto.getLastname());
		entity.setEmail(dto.getEmail());
		entity.setNombre(dto.getName());
		entity.setUsername(dto.getUsername());
		entity.setBloqueado(!dto.isEnabled());
		entity.setAdmin(dto.getAdmin());
		if (dto.getProfiles() != null) {
			Set<Acceso> accesos = new HashSet<>();
			dto.getProfiles().stream().forEach(e -> {
				Acceso acceso = new Acceso();
				Perfil p = perfilService.get(e.getId());
				acceso.setPerfil(p);
				acceso.setUsuario(entity);
				accesos.add(acceso);
			});
			entity.setAccesos(accesos);
		}
		return entity;
	}

	@Override
	public void updateValidation(UsuarioDTO user) throws ErrorValidationException {
		Map<String, String> errores = new HashMap<>();
		// Se valida que el nombre de usuario sea distinto de los existentes
		Usuario usuario = this.service.get(user.getId());

		if (!usuario.getUsername().equals(user.getUsername()) && getService().isUserExists(user.getUsername())) {
			errores.put("username", "Este nombre de usuario ya existe");
		}

		if (!usuario.getEmail().equals(user.getEmail()) && getService().isUserExistWithEmail(user.getEmail())) {
			errores.put("email", "Este email ya se encuentra registrado");
		}

		if (!errores.isEmpty()) {
			throw new ErrorValidationException("Se han encontrado errores de negocio en el objeto analizado", errores);
		}
	}

	@Override
	@Resource(name = "usuarioService")
	protected void setService(UsuarioService service) {
		this.service = service;
	}

}
