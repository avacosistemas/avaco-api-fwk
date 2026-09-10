package ar.com.avaco.fwk.security.repository;

import java.util.List;

import ar.com.avaco.fwk.core.component.repository.NJRepository;
import ar.com.avaco.fwk.security.domain.Usuario;

public interface UsuarioRepository extends NJRepository<Long, Usuario>, UsuarioRepositoryCustom {
	
	Usuario findByUsername(String username);

	Usuario findByEmail(String email);
	
	boolean isUserExistWithEmail(String email);

	List<Usuario> findByLegajoIn(List<String> legajos);

	Usuario findByLegajo(int legajo);

	List<Usuario> findByIdIn(List<Long> lista);
		
}