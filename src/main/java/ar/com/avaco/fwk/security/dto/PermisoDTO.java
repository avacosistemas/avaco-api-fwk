/**
 * 
 */
package ar.com.avaco.fwk.security.dto;

import java.io.Serializable;

import ar.com.avaco.fwk.core.component.dto.DTOEntity;

/**
 * @author avaco
 *
 */
public class PermisoDTO extends DTOEntity<Long> implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4646168645540186169L;
	private Long id;
	private String code;
	private String description;
	private Boolean enabled;
	
	public PermisoDTO() {
		
	}
	
	public PermisoDTO(Long id, String code, String description, Boolean enabled) {
		super();
		this.id = id;
		this.code = code;
		this.description = description;
		this.enabled = enabled;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	
}
