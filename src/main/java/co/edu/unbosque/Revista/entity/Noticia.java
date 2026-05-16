package co.edu.unbosque.Revista.entity;

import java.time.LocalDateTime;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "noticia")
public class Noticia extends Publicacion {

	private String categoria;
	private String fuente;
	private String imagenUrl;

	public Noticia() {
      super();
	}

	public Noticia(String titulo, String contenido, LocalDateTime fechaPublicacion, String autor, String categoria,
			String fuente, String imagenUrl) {
		super(titulo, contenido, fechaPublicacion, autor);
		this.categoria = categoria;
		this.fuente = fuente;
		this.imagenUrl = imagenUrl;
	}

	public Noticia(String titulo, String contenido, LocalDateTime fechaPublicacion, String autor) {
		super(titulo, contenido, fechaPublicacion, autor);
	}

	public Noticia(String titulo, String contenido, String autor, String categoria, String fuente) {
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
	public String toString() {
		return "Noticia [categoria=" + categoria + ", fuente=" + fuente + ", imagenUrl=" + imagenUrl + "]";
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
		Noticia other = (Noticia) obj;
		return Objects.equals(categoria, other.categoria) && Objects.equals(fuente, other.fuente)
				&& Objects.equals(imagenUrl, other.imagenUrl);
	}

}
