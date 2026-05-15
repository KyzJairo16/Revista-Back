package co.edu.unbosque.Revista.security;

import co.edu.unbosque.Revista.entity.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

/**
 * Clase utilitaria para operaciones con JSON Web Tokens (JWT) adaptada para la
 * Revista. Proporciona métodos para generar, validar y extraer información de
 * tokens JWT.
 */
@Component
public class JwtUtil {

	/** Tiempo de validez del token JWT en milisegundos (24 horas). */
	private static final long JWT_TOKEN_VALIDITY = 24 * 60 * 60 * 1000;

	/**
	 * Clave secreta configurada en application.properties. Debe tener al menos 32
	 * caracteres para ser segura.
	 */
	@Value("${jwt.secret:claveSecretaSuperSeguraParaLaRevistaDigital2026}")
	private String secret;

	/**
	 * Obtiene la clave de firma para los tokens JWT.
	 */
	private Key getSigningKey() {
		byte[] keyBytes = secret.getBytes();
		return Keys.hmacShaKeyFor(keyBytes);
	}

	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	public Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	/**
	 * Extrae el rol del usuario del token JWT.
	 */
	public String extractRole(String token) {
		return extractClaim(token, claims -> claims.get("role", String.class));
	}

	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	private Claims extractAllClaims(String token) {
		return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody();
	}

	private Boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	/**
	 * Genera un token JWT para un usuario de la Revista. Incluye el rol dentro de
	 * los claims del token.
	 */
	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();

		// Añadir rol a las reclamaciones si userDetails es de nuestra clase Usuario
		if (userDetails instanceof Usuario) {
			Usuario usuario = (Usuario) userDetails;
			claims.put("role", usuario.getRol().name());
		}

		return createToken(claims, userDetails.getUsername());
	}

	private String createToken(Map<String, Object> claims, String subject) {
		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY))
				.signWith(getSigningKey(), SignatureAlgorithm.HS256).compact();
	}

	/**
	 * Valida el token verificando que el username coincida y no haya expirado.
	 */
	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username = extractUsername(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}
}