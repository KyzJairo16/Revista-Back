package co.edu.unbosque.Revista.entity;



import java.util.Collection;
import java.util.List;
import java.util.Objects;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuario")
public class Usuario implements UserDetails {
	
	private static final long serialVersionUID = 1L;
	
	private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) long id;
	
	@Column(unique=true)
	private String username;

	private String password;
	
	@Enumerated(EnumType.STRING)
	private Rol rol;
	
	private boolean cuentaNoExpirada;
	private boolean cuentaNoBloqueada;
	private boolean credencialNoExpirada;
	private boolean cuentaHabilitada;
	
	public Usuario() {
		this.cuentaNoExpirada = true;
		this.cuentaNoBloqueada = true;
		this.credencialNoExpirada = true;
		this.cuentaHabilitada = true;
		this.rol = Rol.USUARIO;
	}
	
	
	public Usuario(String username, String password) {
		this();
		this.username = username;
		this.password = password;
	}
	
	public Usuario(String username, String password, Rol rol) {
		super();
		this.username = username;
		this.password = password;
		this.rol = rol;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority("ROLE_" + this.rol.name()));
	}
	
	@Override
	public boolean isAccountNonExpired() {
		return cuentaNoExpirada;
	}
	
	@Override
	public boolean isAccountNonLocked() {
		return cuentaNoBloqueada;
	}
	
	@Override
	public boolean isCredentialsNonExpired() {
		return credencialNoExpirada;
	}
	
	@Override
	public boolean isEnabled() {
		return cuentaHabilitada;
	}

	public long getId() {
		return id;
	}


	public void setId(long id) {
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


	public boolean isCuentaNoExpirada() {
		return cuentaNoExpirada;
	}


	public void setCuentaNoExpirada(boolean cuentaNoExpirada) {
		this.cuentaNoExpirada = cuentaNoExpirada;
	}


	public boolean isCuentaNoBloqueada() {
		return cuentaNoBloqueada;
	}


	public void setCuentaNoBloqueada(boolean cuentaNoBloqueada) {
		this.cuentaNoBloqueada = cuentaNoBloqueada;
	}


	public boolean isCredencialNoExpirada() {
		return credencialNoExpirada;
	}


	public void setCredencialNoExpirada(boolean credencialNoExpirada) {
		this.credencialNoExpirada = credencialNoExpirada;
	}


	public boolean isCuentaHabilitada() {
		return cuentaHabilitada;
	}


	public void setCuentaHabilitada(boolean cuentaHabilitada) {
		this.cuentaHabilitada = cuentaHabilitada;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	@Override
	public int hashCode() {
		return Objects.hash(id, password, username);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		return id == other.id && Objects.equals(password, other.password) && Objects.equals(username, other.username);
	}


	@Override
	public String toString() {
		return "Usuario [id=" + id + ", username=" + username + ", password=" + password + "]";
	}

}
