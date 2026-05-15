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
@Table(name = "comentario")
@Inheritance(strategy = InheritanceType.JOINED)
public class Comentario {

	private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) long id;
	private String contenido;
	private LocalDateTime fecha;
	private String autor;
	private Long publicacionId;
	
	public Comentario() {
		// TODO Auto-generated constructor stub
	}


	public Comentario(String contenido, LocalDateTime fecha, String autor, Long publicacionId) {
		super();
		this.contenido = contenido;
		this.fecha = fecha;
		this.autor = autor;
		this.publicacionId = publicacionId;
	}



	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public String getContenido() {
		return contenido;
	}


	public void setContenido(String contenido) {
		this.contenido = contenido;
	}


	public LocalDateTime getFecha() {
		return fecha;
	}


	public void setFecha(LocalDateTime fecha) {
		this.fecha = fecha;
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
		return Objects.hash(autor, contenido, fecha, id, publicacionId);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Comentario other = (Comentario) obj;
		return Objects.equals(autor, other.autor) && Objects.equals(contenido, other.contenido)
				&& Objects.equals(fecha, other.fecha) && id == other.id
				&& Objects.equals(publicacionId, other.publicacionId);
	}


	@Override
	public String toString() {
		return "Comentario [id=" + id + ", contenido=" + contenido + ", fecha=" + fecha + ", autor=" + autor
				+ ", publicacionId=" + publicacionId + "]";
	}

}
