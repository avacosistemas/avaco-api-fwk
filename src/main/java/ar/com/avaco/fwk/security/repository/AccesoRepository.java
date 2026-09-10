package ar.com.avaco.fwk.security.repository;

import java.util.List;

import ar.com.avaco.fwk.core.component.repository.NJRepository;
import ar.com.avaco.fwk.security.domain.Acceso;

public interface AccesoRepository extends NJRepository<Long, Acceso>, AccesoRepositoryCustom {

	List<Acceso> findByUsuarioId(Long usuarioId);

}
