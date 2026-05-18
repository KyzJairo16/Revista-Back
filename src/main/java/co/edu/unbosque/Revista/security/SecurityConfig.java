package co.edu.unbosque.Revista.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration; // Importación añadida para configurar CORS

import java.util.List; // Importación añadida para manejar las listas de configuración

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

	private final JwtAuthenticationFilter jwtAuthFilter;
	private final UserDetailsService userDetailsService;

	public SecurityConfig(JwtAuthenticationFilter jwtAuthFilter, UserDetailsService userDetailsService) {
		this.jwtAuthFilter = jwtAuthFilter;
		this.userDetailsService = userDetailsService;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
				
				.cors(cors -> cors.configurationSource(request -> {
					CorsConfiguration config = new CorsConfiguration();
					config.setAllowedOrigins(List.of("http://localhost:4200"));
					config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
					config.setAllowedHeaders(List.of("Authorization", "Content-Type", "Cache-Control"));
					config.setAllowCredentials(true);
					return config;
				}))
				
				.csrf(csrf -> csrf.disable()).authorizeHttpRequests(auth -> auth

						.requestMatchers("/api/auth/**").permitAll()
						.requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html").permitAll()

						.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

						.requestMatchers("/api/usuarios/listar", "/api/usuarios/count", "/api/usuarios/exists/**",
								"/api/usuarios/buscar/**")
						.hasAnyRole("USUARIO", "COMENTADOR", "EDITOR", "ADMINISTRATIVO")
						.requestMatchers("/api/usuarios/**").hasRole("ADMINISTRATIVO")
						
						.requestMatchers("/api/noticias/listar", "/api/noticias/buscar/**", "/api/noticias/count").permitAll() 
						.requestMatchers("/api/noticias/**").hasAnyRole("EDITOR", "ADMINISTRATIVO") 

						
						.requestMatchers("/api/horoscopos/listar", "/api/horoscopos/buscar/**", "/api/horoscopos/count").permitAll() 
						.requestMatchers("/api/horoscopos/**").hasAnyRole("EDITOR", "ADMINISTRATIVO")

						.requestMatchers("/api/comentarios/listar", "/api/comentarios/publicacion/**", "/api/comentarios/buscar/**").permitAll()
						.requestMatchers("/api/comentarios/**").hasAnyRole("USUARIO", "COMENTADOR", "EDITOR", "ADMINISTRATIVO")

						.anyRequest().authenticated())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authenticationProvider(authenticationProvider())
				.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService);
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}