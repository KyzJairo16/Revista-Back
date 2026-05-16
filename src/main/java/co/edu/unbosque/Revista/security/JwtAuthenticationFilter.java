package co.edu.unbosque.Revista.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * Filtro de autenticación JWT que intercepta las solicitudes HTTP. Valida los
 * tokens JWT en las solicitudes y establece la autenticación en el contexto de
 * seguridad. Se ejecuta una vez por cada solicitud.
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	/** Utilidad para operaciones con tokens JWT. */
	private final JwtUtil jwtUtil;

	/** Servicio para cargar los detalles del usuario desde la base de datos. */
	private final UserDetailsService userDetailsService;

	public JwtAuthenticationFilter(JwtUtil jwtUtil, UserDetailsService userDetailsService) {
		this.jwtUtil = jwtUtil;
		this.userDetailsService = userDetailsService;
	}

	/**
	 * Método principal del filtro. Extrae el JWT del encabezado "Authorization". Si
	 * es válido, avisa a Spring Security que el usuario está autenticado.
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		// 1. Busca el encabezado Authorization
		final String authorizationHeader = request.getHeader("Authorization");

		String username = null;
		String jwt = null;

		// 2. Verifica si el encabezado existe y empieza con "Bearer "
		if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
			jwt = authorizationHeader.substring(7); // Extrae el token quitando la palabra "Bearer "
			try {
				username = jwtUtil.extractUsername(jwt);
			} catch (Exception e) {
				logger.error("Error al extraer el nombre de usuario del token JWT", e);
			}
		}

		// 3. Si se extrajo un usuario y no hay una autenticación previa en el contexto
		// actual
		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

			// Carga los datos del usuario desde la base de datos
			UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

			// Valida matemáticamente que el token no esté expirado y corresponda al usuario
			if (jwtUtil.validateToken(jwt, userDetails)) {

			
				String roleFromToken = jwtUtil.extractRole(jwt);
				List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_" + roleFromToken));
				
				// Crea el objeto de autenticación que Spring Security entiende
				UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
						userDetails, null, authorities);

				authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

				// 4. Establece la autenticación exitosa en el contexto de seguridad global
				SecurityContextHolder.getContext().setAuthentication(authenticationToken);
			}
		}

		// 5. Permite que la petición continúe su viaje hacia los controladores
		filterChain.doFilter(request, response);
	}
}