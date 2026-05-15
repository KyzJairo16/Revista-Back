package co.edu.unbosque.Revista.security;

import co.edu.unbosque.Revista.repository.UsuarioRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Implementación del servicio de detalles de usuario para la autenticación.
 * Esta clase proporciona la funcionalidad necesaria para cargar los datos del
 * usuario desde el repositorio durante el proceso de autenticación en la
 * Revista.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	/**
	 * Repositorio de usuarios utilizado para buscar información de usuarios.
	 */
	private final UsuarioRepository usuarioRepository;

	/**
	 * Constructor que inicializa el repositorio de usuarios. * @param
	 * usuarioRepository El repositorio de usuarios a utilizar para las consultas
	 */
	public UserDetailsServiceImpl(UsuarioRepository usuarioRepository) {
		this.usuarioRepository = usuarioRepository;
	}

	/**
	 * Carga los detalles del usuario por su nombre de usuario. * @param username El
	 * nombre de usuario para buscar
	 * 
	 * @return Los detalles del usuario encontrado
	 * @throws UsernameNotFoundException Si no se encuentra el usuario con el nombre
	 *                                   proporcionado
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// Busca el usuario en el repositorio y lanza una excepción si no se encuentra
		return usuarioRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con el username: " + username));
	}
}