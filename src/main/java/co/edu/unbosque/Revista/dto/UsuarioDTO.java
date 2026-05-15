package co.edu.unbosque.Revista.dto;

import java.util.Objects;

import co.edu.unbosque.Revista.entity.Rol;


public class UsuarioDTO {
	
	private Long id;
	private String username; 
	private String password;
	private Rol rol;
	
	public UsuarioDTO() {
		// TODO Auto-generated constructor stub
	}
	
	public UsuarioDTO(String username, String password) {
	    this.username = username;
	    this.password = password;
	  }
	
	public UsuarioDTO(String username, String password, Rol rol) {
	    this.username = username;
	    this.password = password;
	    this.rol = rol;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, password, rol, username);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UsuarioDTO other = (UsuarioDTO) obj;
		return Objects.equals(id, other.id) && Objects.equals(password, other.password) && rol == other.rol
				&& Objects.equals(username, other.username);
	}

	@Override
	public String toString() {
		return "UsuarioDTO [id=" + id + ", username=" + username + ", password=" + password + ", rol=" + rol + "]";
	}
	

}
