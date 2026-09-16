/**
 * 
 */
package ar.com.avaco.fwk.security.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import ar.com.avaco.fwk.core.component.dto.DTOEntity;

/**
 * @author avaco
 *
 */
public class PerfilDTO extends DTOEntity<Long> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1484703680205099614L;

	private Long id;
	private String name;
	private RolDTO role;
	private List<PermisoDTO> permissions = new ArrayList<PermisoDTO>();
	private Boolean enabled;

	public PerfilDTO() {

	}

	public PerfilDTO(Long id, String name, RolDTO role, List<PermisoDTO> permissions, Boolean enabled) {
		super();
		this.id = id;
		this.name = name;
		this.role = role;
		this.permissions = permissions;
		this.enabled = enabled;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public RolDTO getRole() {
		return role;
	}

	public void setRole(RolDTO role) {
		this.role = role;
	}

	public List<PermisoDTO> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<PermisoDTO> permissions) {
		this.permissions = permissions;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
