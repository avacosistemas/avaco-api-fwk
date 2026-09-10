package ar.com.avaco.fwk.security.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.com.avaco.fwk.core.component.service.NJBaseService;
import ar.com.avaco.fwk.security.domain.Acceso;
import ar.com.avaco.fwk.security.domain.Usuario;
import ar.com.avaco.fwk.security.repository.AccesoRepository;
import ar.com.avaco.fwk.security.service.AccesoService;
import ar.com.avaco.fwk.security.service.UsuarioService;

@Transactional
@Service("accesoService")
public class AccesoServiceImpl extends NJBaseService<Long, Acceso, AccesoRepository> implements AccesoService {

	
	@Autowired
	private UsuarioService usuarioService;
	
	@Resource(name = "accesoRepository")
	void setAccesoRepository(AccesoRepository accesoRepository) {
		this.repository = accesoRepository;
	}

	@Override
	public List<Acceso> list(Long usuarioId) {
		return this.getRepository().findByUsuarioId(usuarioId);
	}
	
	@Override
	public void remove(Long id) {
		Acceso one = this.repository.getOne(id);
		Usuario usuario = usuarioService.get(one.getUsuario().getId());
		usuario.getAccesos().remove(one);
		usuarioService.update(usuario);
	}

}
