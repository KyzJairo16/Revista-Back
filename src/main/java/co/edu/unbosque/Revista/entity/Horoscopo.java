package co.edu.unbosque.Revista.entity;

import java.time.LocalDateTime;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "horoscopo")
public class Horoscopo extends Publicacion {

	private String signoZodiacal;
	private String prediccion;
	private String compatibilidad;
	
	

	public Horoscopo(String titulo, String contenido, LocalDateTime fechaPublicacion, Usuario autor,
			String signoZodiacal, String prediccion, String compatibilidad) {

		super(titulo, contenido, fechaPublicacion, autor);
		this.signoZodiacal = signoZodiacal;
		this.prediccion = prediccion;
		this.compatibilidad = compatibilidad;
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

	public String getCompatibilidad() {
		return compatibilidad;
	}

	public void setCompatibilidad(String compatibilidad) {
		this.compatibilidad = compatibilidad;
	}

	@Override
	public String toString() {
		return "Horoscopo [signoZodiacal=" + signoZodiacal + ", prediccion=" + prediccion + ", compatibilidad="
				+ compatibilidad + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(compatibilidad, prediccion, signoZodiacal);
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
		Horoscopo other = (Horoscopo) obj;
		return Objects.equals(compatibilidad, other.compatibilidad) && Objects.equals(prediccion, other.prediccion)
				&& Objects.equals(signoZodiacal, other.signoZodiacal);
	}
	
	

}
