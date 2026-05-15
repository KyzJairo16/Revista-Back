package co.edu.unbosque.Revista.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.unbosque.Revista.entity.Horoscopo;

public interface HoroscopoRepository extends JpaRepository<Horoscopo, Long> {

	public Optional<Horoscopo> findByTitulo(String titulo);

	public List<Horoscopo> findBySignoZodiacal(String signoZodiacal);

	public void deleteByTitulo(String titulo);

}
