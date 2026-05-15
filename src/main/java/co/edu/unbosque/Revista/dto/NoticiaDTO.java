package co.edu.unbosque.Revista.dto;

import java.time.LocalDateTime;
import java.util.Objects;

import co.edu.unbosque.Revista.entity.Publicacion;
import co.edu.unbosque.Revista.entity.Usuario;

public class NoticiaDTO extends Publicacion {

	private String categoria;
	private String fuente;
	private String imagenUrl;

	public NoticiaDTO() {
		// TODO Auto-generated constructor stub
	}

	public NoticiaDTO(String titulo, String contenido, LocalDateTime fechaPublicacion, String autor, String categoria,
			String fuente, String imagenUrl) {
		super(titulo, contenido, fechaPublicacion, autor);
		this.categoria = categoria;
		this.fuente = fuente;
		this.imagenUrl = imagenUrl;
	}

	public NoticiaDTO(String titulo, String contenido, LocalDateTime fechaPublicacion, String autor) {
		super(titulo, contenido, fechaPublicacion, autor);
	}

	public NoticiaDTO(String titulo, String contenido, String autor, String categoria, String fuente) {
		super(titulo, contenido, LocalDateTime.now(), autor);
		this.categoria = categoria;
		this.fuente = fuente;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getFuente() {
		return fuente;
	}

	public void setFuente(String fuente) {
		this.fuente = fuente;
	}

	public String getImagenUrl() {
		return imagenUrl;
	}

	public void setImagenUrl(String imagenUrl) {
		this.imagenUrl = imagenUrl;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(categoria, fuente, imagenUrl);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		NoticiaDTO other = (NoticiaDTO) obj;
		return Objects.equals(categoria, other.categoria) && Objects.equals(fuente, other.fuente)
				&& Objects.equals(imagenUrl, other.imagenUrl);
	}

	@Override
	public String toString() {
		return "NoticiaDTO [categoria=" + categoria + ", fuente=" + fuente + ", imagenUrl=" + imagenUrl + "]";
	}

}
