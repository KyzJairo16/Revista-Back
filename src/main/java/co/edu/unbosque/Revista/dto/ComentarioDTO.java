package co.edu.unbosque.Revista.dto;

import java.util.Objects;

import co.edu.unbosque.Revista.entity.Publicacion;
import co.edu.unbosque.Revista.entity.Usuario;

public class ComentarioDTO {

	private Long id;
	private String contenido;
	private Usuario autor;
	private Publicacion publicacion;
	
	public ComentarioDTO() {
		// TODO Auto-generated constructor stub
	}

	public ComentarioDTO(Long id, String contenido, Usuario autor, Publicacion publicacion) {
		super();
		this.id = id;
		this.contenido = contenido;
		this.autor = autor;
		this.publicacion = publicacion;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getContenido() {
		return contenido;
	}

	public void setContenido(String contenido) {
		this.contenido = contenido;
	}

	public Usuario getAutor() {
		return autor;
	}

	public void setAutor(Usuario autor) {
		this.autor = autor;
	}

	public Publicacion getPublicacion() {
		return publicacion;
	}

	public void setPublicacion(Publicacion publicacion) {
		this.publicacion = publicacion;
	}

	@Override
	public int hashCode() {
		return Objects.hash(autor, contenido, id, publicacion);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ComentarioDTO other = (ComentarioDTO) obj;
		return Objects.equals(autor, other.autor) && Objects.equals(contenido, other.contenido)
				&& Objects.equals(id, other.id) && Objects.equals(publicacion, other.publicacion);
	}

	@Override
	public String toString() {
		return "ComentarioDTO [id=" + id + ", contenido=" + contenido + ", autor=" + autor + ", publicacion="
				+ publicacion + "]";
	}
	
}
