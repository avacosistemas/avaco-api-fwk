package ar.com.avaco.fwk.security.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import ar.com.avaco.fwk.security.domain.Acceso;
import ar.com.avaco.fwk.security.domain.Perfil;
import ar.com.avaco.fwk.security.domain.Permiso;
import ar.com.avaco.fwk.security.domain.Usuario;

/**
 * 
 */
public class JwtAuthenticationResponse implements Serializable {

	private static final long serialVersionUID = 1250166508152483573L;

	private final String token;

	private String name;

	private String lastname;

	private Set<PermisoDTO> permissions;

	private String email;

	private String role;

	private String guid;

	private String permisos;

	private String username;

	private Boolean passwordExpired;

	public JwtAuthenticationResponse(String token) {
		this.token = token;
	}

	public JwtAuthenticationResponse(String token, Usuario usuario, Boolean passwordExpired) {
	    this.token = token;
	    this.name = usuario.getNombre();
	    this.lastname = usuario.getApellido();
	    this.email = usuario.getEmail();
	    this.role = "Administrators";

	    if (!passwordExpired) {
	        this.permissions = new HashSet<PermisoDTO>();

	        if (usuario.getAccesos() != null) {
	            for (Acceso acceso : usuario.getAccesos()) {
	                Perfil perfil = acceso.getPerfil();

	                if (perfil != null && perfil.isActivo() && perfil.getPermisos() != null) {
	                    for (Permiso permiso : perfil.getPermisos()) {
	                        if (permiso != null && permiso.getAuthority() != null) {
	                            PermisoDTO permission = new PermisoDTO();
	                            permission.setCode(permiso.getCodigo());
	                            this.permissions.add(permission);
	                        }
	                    }
	                }
	            }
	        }

	        this.permisos = this.permissions.stream()
	                .map(PermisoDTO::getCode)
	                .collect(Collectors.joining(";"));
	    }

	    this.guid = UUID.randomUUID().toString();
	    this.username = usuario.getUsername();
	    this.passwordExpired = passwordExpired;
	}

	public String getPermisos() {
		return permisos;
	}

	public void setPermisos(String permisos) {
		this.permisos = permisos;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getToken() {
		return this.token;
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

	public Set<PermisoDTO> getPermissions() {
		return permissions;
	}

	public void setPermissions(Set<PermisoDTO> permissions) {
		permissions = permissions;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Boolean getPasswordExpired() {
		return passwordExpired;
	}

	public void setPasswordExpired(Boolean passwordExpired) {
		this.passwordExpired = passwordExpired;
	}

}