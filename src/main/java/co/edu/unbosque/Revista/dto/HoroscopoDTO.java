package co.edu.unbosque.Revista.dto;

import java.time.LocalDateTime;
import java.util.Objects;

import co.edu.unbosque.Revista.entity.Publicacion;
import co.edu.unbosque.Revista.entity.Usuario;

public class HoroscopoDTO extends Publicacion {
	
	private String signoZodiacal;
	private String prediccion;
	
	public HoroscopoDTO() {
	}
	
	public HoroscopoDTO(String titulo, String contenido, LocalDateTime fechaPublicacion, Usuario autor,
			String signoZodiacal, String prediccion) {
		super(titulo, contenido, fechaPublicacion, autor);
		this.signoZodiacal = signoZodiacal;
		this.prediccion = prediccion;
	}

	public HoroscopoDTO(String titulo, String contenido, LocalDateTime fechaPublicacion, Usuario autor) {
		super(titulo, contenido, fechaPublicacion, autor);
	}

	public HoroscopoDTO(String titulo, String contenido, Usuario autor, String signoZodiacal, String prediccion) {
		super(titulo, contenido, LocalDateTime.now(), autor);
		this.signoZodiacal = signoZodiacal;
		this.prediccion = prediccion;
	}

	public String getSignoZodiacal() {
		return signoZodiacal;
	}

	public void setSignoZodiacal(String signoZodiacal) {
		this.signoZodiacal = signoZodiacal;
	}

	public String getPrediccion() {
		return prediccion;
	}

	public void setPrediccion(String prediccion) {
		this.prediccion = prediccion;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(prediccion, signoZodiacal);
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
		HoroscopoDTO other = (HoroscopoDTO) obj;
		return Objects.equals(prediccion, other.prediccion) && Objects.equals(signoZodiacal, other.signoZodiacal);
	}

	@Override
	public String toString() {
		return "HoroscopoDTO [signoZodiacal=" + signoZodiacal + ", prediccion=" + prediccion + "]";
	}
	

}
