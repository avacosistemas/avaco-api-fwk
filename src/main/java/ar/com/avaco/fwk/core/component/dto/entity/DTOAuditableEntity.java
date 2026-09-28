package ar.com.avaco.fwk.core.component.dto.entity;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import ar.com.avaco.fwk.core.domain.AuditableEntity;

public abstract class DTOAuditableEntity<ID extends Serializable> extends DTOEntity<ID> {

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String usuarioCreacion;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date fechaCreacion;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String usuarioActualizacion;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date fechaActualizacion;

	public DTOAuditableEntity() {
		// TODO Auto-generated constructor stub
	}

	public DTOAuditableEntity(AuditableEntity<ID> entity) {
		this.usuarioCreacion = entity.getUsuarioCreacion();
		this.fechaCreacion = entity.getFechaCreacion();
		this.usuarioActualizacion = entity.getUsuarioActualizacion();
		this.fechaActualizacion = entity.getFechaActualizacion();
	}
	
	public String getUsuarioCreacion() {
		return usuarioCreacion;
	}

	public void setUsuarioCreacion(String usuarioCreacion) {
		this.usuarioCreacion = usuarioCreacion;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public String getUsuarioActualizacion() {
		return usuarioActualizacion;
	}

	public void setUsuarioActualizacion(String usuarioActualizacion) {
		this.usuarioActualizacion = usuarioActualizacion;
	}

	public Date getFechaActualizacion() {
		return fechaActualizacion;
	}

	public void setFechaActualizacion(Date fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}
	
}
