/**
 * 
 */
package ar.com.avaco.fwk.security.epservice;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.avaco.fwk.core.component.epservice.CRUDEPBaseService;
import ar.com.avaco.fwk.security.domain.Acceso;
import ar.com.avaco.fwk.security.domain.Perfil;
import ar.com.avaco.fwk.security.domain.Usuario;
import ar.com.avaco.fwk.security.dto.AccesoDTO;
import ar.com.avaco.fwk.security.service.AccesoService;
import ar.com.avaco.fwk.security.service.PerfilService;
import ar.com.avaco.fwk.security.service.UsuarioService;

/**
 * @author avaco
 *
 */
@Transactional
@Service("accesoEPService")
public class AccesoEPServiceImpl extends CRUDEPBaseService<Long, AccesoDTO, Acceso, AccesoService>
		implements AccesoEPService {

	public AccesoEPServiceImpl() {
		super(Acceso.class, AccesoDTO.class);
	}

	@Autowired
	private PerfilService perfilService;

	@Autowired
	private UsuarioService usuarioService;

	@Override
	protected Acceso convertToEntity(AccesoDTO dto) {
		Acceso a = new Acceso();
		a.setId(dto.getId());
		a.setPerfil(perfilService.get(dto.getIdGrupo()));
		a.setUsuario(usuarioService.get(dto.getIdUsuario()));
		return a;
	}

	@Override
	protected AccesoDTO convertToDto(Acceso entity) {
		AccesoDTO dto = new AccesoDTO();
		dto.setId(entity.getId());
		dto.setIdUsuario(entity.getUsuario().getId());
		dto.setPerfilNombre(entity.getPerfil().getNombre());
		dto.setIdGrupo(entity.getPerfil().getId());	return dto;
	}

	@Resource(name = "accesoService")
	protected void setService(AccesoService service) {
		this.service = service;
	}

	@Override
	public List<AccesoDTO> list(Long usuarioId) {
		List<Acceso> list = this.service.list(usuarioId);
		List<AccesoDTO> dtos = new ArrayList<AccesoDTO>();
		list.forEach(a -> dtos.add(convertToDto(a)));
		return dtos;
	}

	@Override
	public void delete(Long id, Long idUsuario) {
		Usuario usuario = this.usuarioService.get(idUsuario);
		Perfil perfil = this.perfilService.get(id);
		usuario.getAccesos().remove(perfil);
		this.usuarioService.update(usuario);
	}

}
