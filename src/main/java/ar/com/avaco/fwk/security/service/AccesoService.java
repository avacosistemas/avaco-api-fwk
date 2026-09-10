package ar.com.avaco.fwk.security.service;

import java.util.List;

import ar.com.avaco.fwk.core.component.service.NJService;
import ar.com.avaco.fwk.security.domain.Acceso;


public interface AccesoService extends NJService<Long, Acceso> {

	List<Acceso> list(Long usuarioId);


}
