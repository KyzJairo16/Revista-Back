package co.edu.unbosque.Revista.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.unbosque.Revista.entity.Comentario;

public interface ComentarioRepository extends JpaRepository<Comentario, Long> {
	
	public List<Comentario> findByPublicacionId(Long publicacionId);
	
	public List<Comentario> findByAutorUsername(String username);

}
