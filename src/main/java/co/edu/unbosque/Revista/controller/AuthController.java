package co.edu.unbosque.Revista.controller;

import co.edu.unbosque.Revista.dto.UsuarioDTO;
import co.edu.unbosque.Revista.entity.Usuario;
import co.edu.unbosque.Revista.security.JwtUtil;
import co.edu.unbosque.Revista.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Autenticación", description = "Endpoints para el acceso al sistema (Login y Registro)")
public class AuthController {

	private final AuthenticationManager authenticationManager;
	private final JwtUtil jwtUtil;
	private final UsuarioService usuarioService;

	public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UsuarioService usuarioService) {
		this.authenticationManager = authenticationManager;
		this.jwtUtil = jwtUtil;
		this.usuarioService = usuarioService;
	}

	@Operation(summary = "Iniciar sesión (Login)", description = "Autentica al usuario y devuelve un token JWT junto con su rol. Este token debe enviarse en las cabeceras de todas las peticiones protegidas como: `Authorization: Bearer <token>`.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Login exitoso"),
			@ApiResponse(responseCode = "401", description = "Credenciales incorrectas") })
	@PostMapping("/login")
	public ResponseEntity<?> login(
			@Parameter(description = "Nombre de usuario y contraseña", required = true, schema = @Schema(implementation = UsuarioDTO.class)) @RequestBody UsuarioDTO loginRequest) {
		try {
			
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

			
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			String jwt = jwtUtil.generateToken(userDetails);

			
			String role = null;
			if (userDetails instanceof Usuario) {
				role = ((Usuario) userDetails).getRol().name();
			}

			return ResponseEntity.ok(new AuthResponse(jwt, role));

		} catch (AuthenticationException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body("Error: Nombre de usuario o contraseña incorrectos");
		}
	}

	@Operation(summary = "Registrar nuevo lector", description = "Permite a nuevos usuarios registrarse en la revista. Por defecto, se les asigna el rol de **USUARIO** (Lector).")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Registro exitoso"),
			@ApiResponse(responseCode = "409", description = "El nombre de usuario ya existe"),
			@ApiResponse(responseCode = "400", description = "Datos de registro inválidos") })
	@PostMapping("/register")
	public ResponseEntity<String> register(
			@Parameter(description = "Datos de registro", required = true, schema = @Schema(implementation = UsuarioDTO.class)) @RequestBody UsuarioDTO registerRequest) {

		
		if (usuarioService.findUsernameAlreadyTaken(registerRequest.getUsername())) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: El nombre de usuario ya está en uso");
		}

		
		int result = usuarioService.create(registerRequest);

		if (result == 0) {
			return ResponseEntity.status(HttpStatus.CREATED).body("Registro exitoso. Ya puedes iniciar sesión.");
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al procesar el registro");
		}
	}


	private static class AuthResponse {
		private final String token;
		private final String rol;

		public AuthResponse(String token, String rol) {
			this.token = token;
			this.rol = rol;
		}

		public String getToken() {
			return token;
		}

		public String getRol() {
			return rol;
		}

		
	}
}