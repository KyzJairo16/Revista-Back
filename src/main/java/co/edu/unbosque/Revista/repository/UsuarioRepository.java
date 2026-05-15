package co.edu.unbosque.Revista.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.unbosque.Revista.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	public Optional<Usuario> findByUsername(String username);

	public void deleteByUsername(String username);
	
}
