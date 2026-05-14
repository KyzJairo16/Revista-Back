package co.edu.unbosque.Revista.entity;

import java.time.LocalDateTime;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "publicacion")
public abstract class Publicacion {
	
	private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) long id;
	private String titulo;
	private String contenido;
	private LocalDateTime fechaPublicacion;
	
	@ManyToOne
	private Usuario autor;
	
	public Publicacion(String titulo, String contenido, LocalDateTime fechaPublicacion, Usuario autor) {
		super();
		this.titulo = titulo;
		this.contenido = contenido;
		this.fechaPublicacion = fechaPublicacion;
		this.autor = autor;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getContenido() {
		return contenido;
	}

	public void setContenido(String contenido) {
		this.contenido = contenido;
	}

	public LocalDateTime getFechaPublicacion() {
		return fechaPublicacion;
	}

	public void setFechaPublicacion(LocalDateTime fechaPublicacion) {
		this.fechaPublicacion = fechaPublicacion;
	}

	public Usuario getAutor() {
		return autor;
	}

	public void setAutor(Usuario autor) {
		this.autor = autor;
	}

	@Override
	public String toString() {
		return "Publicacion [id=" + id + ", titulo=" + titulo + ", contenido=" + contenido + ", fechaPublicacion="
				+ fechaPublicacion + ", autor=" + autor + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(autor, contenido, fechaPublicacion, id, titulo);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Publicacion other = (Publicacion) obj;
		return Objects.equals(autor, other.autor) && Objects.equals(contenido, other.contenido)
				&& Objects.equals(fechaPublicacion, other.fechaPublicacion) && id == other.id
				&& Objects.equals(titulo, other.titulo);
	}
	
	

}
