package co.edu.unbosque.Revista.entity;



import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuario")
@Inheritance(strategy = InheritanceType.JOINED)
public class Usuario {
	
	private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) long id;
	private String nombre;
	private String contrasenia;
	private boolean autenticado;
	
	@Enumerated(EnumType.STRING)
	private Rol rol;
	
	public Usuario() {
	}

	public Usuario(String nombre, String contrasenia, boolean autenticado, Rol rol) {
		super();
		this.nombre = nombre;
		this.contrasenia = contrasenia;
		this.autenticado = autenticado;
		this.rol = rol;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getContrasenia() {
		return contrasenia;
	}

	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}

	public boolean isAutenticado() {
		return autenticado;
	}

	public void setAutenticado(boolean autenticado) {
		this.autenticado = autenticado;
	}

	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", nombre=" + nombre + ", contrasenia=" + contrasenia + ", autenticado="
				+ autenticado + ", rol=" + rol + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(autenticado, contrasenia, id, nombre, rol);
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
		return autenticado == other.autenticado && Objects.equals(contrasenia, other.contrasenia) && id == other.id
				&& Objects.equals(nombre, other.nombre) && rol == other.rol;
	}

}
