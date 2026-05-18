package co.edu.unbosque.Revista.controller;

import co.edu.unbosque.Revista.dto.UsuarioDTO;
import co.edu.unbosque.Revista.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = { "http://localhost:8080", "http://localhost:4200" })
@Transactional
@Tag(name = "Gestión de Usuarios", description = "Endpoints para administrar los usuarios de la Revista")
@SecurityRequirement(name = "bearerAuth")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioServ;

	public UsuarioController() {
	}

	@Operation(summary = "Crear usuario", description = "Crea un nuevo usuario. Requiere rol ADMINISTRATIVO.")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Usuario creado exitosamente"),
			@ApiResponse(responseCode = "406", description = "El nombre de usuario ya existe") })
	@PreAuthorize("hasRole('ADMINISTRATIVO')")
	@PostMapping(path = "/crear", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> crear(
			@Parameter(description = "Datos del nuevo usuario", required = true, schema = @Schema(implementation = UsuarioDTO.class)) @RequestBody UsuarioDTO newUsuario) {

		usuarioServ.create(newUsuario);

		return new ResponseEntity<>("Usuario creado exitosamente", HttpStatus.CREATED);
	}

	@Operation(summary = "Obtener todos los usuarios", description = "Recupera la lista de usuarios. Accesible para ADMINISTRATIVO y EDITOR.")
	@PreAuthorize("hasAnyRole('ADMINISTRATIVO', 'EDITOR')")
	@GetMapping("/listar")
	public ResponseEntity<List<UsuarioDTO>> listarTodos() {
		List<UsuarioDTO> users = usuarioServ.getAll();
		if (users.isEmpty()) {
			return new ResponseEntity<>(users, HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(users, HttpStatus.OK);
	}

	@Operation(summary = "Obtener usuario por ID", description = "Busca un usuario específico.")
	@PreAuthorize("hasAnyRole('ADMINISTRATIVO', 'EDITOR')")
	@GetMapping("/buscar/{id}")
	public ResponseEntity<UsuarioDTO> buscarPorId(@PathVariable Long id) {
		UsuarioDTO found = usuarioServ.getById(id);

		return new ResponseEntity<>(found, HttpStatus.OK);
	}

	@Operation(summary = "Actualizar usuario", description = "Actualiza los datos de un usuario existente. Requiere rol ADMINISTRATIVO.")
	@PreAuthorize("hasRole('ADMINISTRATIVO')")
	@PutMapping(path = "/actualizar/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> actualizar(@PathVariable Long id, @RequestBody UsuarioDTO newData) {

		usuarioServ.updateById(id, newData);

		return new ResponseEntity<>("Usuario actualizado correctamente", HttpStatus.OK);

	}

	@Operation(summary = "Eliminar usuario por ID", description = "Elimina permanentemente un usuario. Requiere rol ADMINISTRATIVO.")
	@PreAuthorize("hasRole('ADMINISTRATIVO')")
	@DeleteMapping("/eliminar/{id}")
	public ResponseEntity<String> eliminarPorId(@PathVariable Long id) {
		usuarioServ.deleteById(id);
		return new ResponseEntity<>("Usuario eliminado exitosamente", HttpStatus.OK);

	}
}