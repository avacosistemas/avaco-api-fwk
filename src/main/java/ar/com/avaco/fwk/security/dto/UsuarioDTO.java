/**
 * 
 */
package ar.com.avaco.fwk.security.dto;

import java.io.Serializable;
import java.util.Set;

import ar.com.avaco.fwk.core.component.dto.entity.DTOEntity;

/**
 * @author avaco
 *
 */
public class UsuarioDTO extends DTOEntity<Long> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3712576319173757829L;
	private Long id;
	private String username;
	private String name;
	private String lastname;
	private Set<PerfilDTO> profiles;
	private String email;
	private boolean enabled;
	private Boolean admin;

	public UsuarioDTO() {

	}

	public UsuarioDTO(Long id, String username, String name, String lastname, Set<PerfilDTO> profiles, String email,
			boolean enabled, Boolean admin) {
		super();
		this.id = id;
		this.username = username;
		this.name = name;
		this.lastname = lastname;
		this.profiles = profiles;
		this.email = email;
		this.enabled = enabled;
		this.admin = admin;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Set<PerfilDTO> getProfiles() {
		return profiles;
	}

	public void setProfiles(Set<PerfilDTO> profiles) {
		this.profiles = profiles;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Boolean getAdmin() {
		return admin;
	}

	public void setAdmin(Boolean admin) {
		this.admin = admin;
	}

}
