package ar.com.avaco.fwk.security.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ar.com.avaco.fwk.core.component.repository.NJRepository;
import ar.com.avaco.fwk.security.domain.Usuario;

public interface UsuarioRepository extends NJRepository<Long, Usuario>, UsuarioRepositoryCustom {
	
	Usuario findByUsername(String username);

	Usuario findByEmail(String email);

	@Query("SELECT CASE WHEN COUNT(u) > 0 THEN TRUE ELSE FALSE END " +
		       "FROM Usuario u WHERE u.email = :email")
	boolean isUserExistWithEmail(@Param("email") String email);

	List<Usuario> findByIdIn(List<Long> lista);
		
}