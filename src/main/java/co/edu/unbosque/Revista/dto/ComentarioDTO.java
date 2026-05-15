package co.edu.unbosque.Revista.dto;

import java.util.Objects;

import co.edu.unbosque.Revista.entity.Publicacion;
import co.edu.unbosque.Revista.entity.Usuario;

public class ComentarioDTO {

	private Long id;
	private String contenido;
	private String autor;
	private Long publicacionId;
	
	public ComentarioDTO() {
		// TODO Auto-generated constructor stub
	}

	public ComentarioDTO(String contenido, String autor, Long publicacionId) {
		super();
		this.contenido = contenido;
		this.autor = autor;
		this.publicacionId = publicacionId;
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

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public Long getPublicacionId() {
		return publicacionId;
	}

	public void setPublicacionId(Long publicacionId) {
		this.publicacionId = publicacionId;
	}

	

	@Override
	public int hashCode() {
		return Objects.hash(autor, contenido, id, publicacionId);
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
				&& Objects.equals(id, other.id) && Objects.equals(publicacionId, other.publicacionId);
	}

	@Override
	public String toString() {
		return "ComentarioDTO [id=" + id + ", contenido=" + contenido + ", autor=" + autor + ", publicacionId="
				+ publicacionId + "]";
	}


}
