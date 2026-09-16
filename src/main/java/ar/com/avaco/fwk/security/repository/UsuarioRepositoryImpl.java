package ar.com.avaco.fwk.security.repository;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import ar.com.avaco.fwk.core.component.repository.NJBaseRepository;
import ar.com.avaco.fwk.security.domain.Usuario;

@Repository("usuarioRepository")
public class UsuarioRepositoryImpl extends NJBaseRepository<Long, Usuario> implements UsuarioRepositoryCustom {

	protected UsuarioRepositoryImpl(EntityManager em) {
		super(Usuario.class, em);
	}

}