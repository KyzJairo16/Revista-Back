package co.edu.unbosque.Revista.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.unbosque.Revista.entity.Noticia;

public interface NoticiaRepository extends JpaRepository<Noticia, Long> {
	
	public Optional<Noticia> findByTitulo(String titulo);
	public List<Noticia> findByCategoria(String categoria);
	public void deleteByTitulo(String titulo);
	

}
