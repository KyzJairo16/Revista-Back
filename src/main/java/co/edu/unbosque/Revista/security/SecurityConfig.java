/*package co.edu.unbosque.Revista.security;

import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
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



 * Clase de configuración maestra de seguridad para la Revista. 
 * Configura la autenticación, CORS, y autorización basada en JWT.
 
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
            .cors(Customizer.withDefaults()) // Habilita CORS para que Angular/React puedan conectarse
            .csrf(csrf -> csrf.disable())    // Deshabilita CSRF (innecesario al usar tokens JWT)
            .authorizeHttpRequests(auth -> auth
                // 1. Rutas totalmente públicas (No requieren token)
                .requestMatchers("/api/auth/**").permitAll()
                
                // 2. Rutas de Swagger UI y documentación pública
                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html").permitAll()
                
                // 3. Cualquier otra ruta requiere estar autenticado (El rol específico lo decide el @PreAuthorize de cada Controller)
                .anyRequest().authenticated()
            )
            // 4. Establecer manejo de sesión sin estado (Stateless) porque el JWT maneja la sesión
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            
            // 5. Configurar el proveedor de autenticación
            .authenticationProvider(authenticationProvider())
            
            // 6. Añadir nuestro filtro JWT antes del filtro de usuario/contraseña estándar de Spring
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder()); // Usa BCrypt para comparar contraseñas
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
*/