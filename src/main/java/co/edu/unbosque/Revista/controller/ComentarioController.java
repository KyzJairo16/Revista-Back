package co.edu.unbosque.Revista.controller;

import co.edu.unbosque.Revista.dto.ComentarioDTO;
import co.edu.unbosque.Revista.service.ComentarioService;
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
@RequestMapping("/api/comentarios")
@CrossOrigin(origins = { "http://localhost:8080", "http://localhost:4200" })
@Transactional
@Tag(name = "Gestión de Comentarios", description = "Endpoints para la interacción y moderación de comentarios")
@SecurityRequirement(name = "bearerAuth") // Exige el JWT en Swagger
public class ComentarioController {

	@Autowired
	private ComentarioService comentarioServ;

	public ComentarioController() {
	}

	@Operation(summary = "Escribir un comentario", description = "Añade un nuevo comentario a una publicación. **Requiere rol COMENTADOR o EDITOR.**")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Comentario publicado exitosamente") })
	@PreAuthorize("hasAnyRole('COMENTADOR', 'EDITOR')")
	@PostMapping(path = "/crear", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> crear(
			@Parameter(description = "Datos del comentario", required = true, schema = @Schema(implementation = ComentarioDTO.class)) @RequestBody ComentarioDTO newComentario) {

		int status = comentarioServ.create(newComentario);

		if (status == 0) {
			return new ResponseEntity<>("Comentario publicado exitosamente", HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>("Error al publicar el comentario", HttpStatus.BAD_REQUEST);
		}
	}

	@Operation(summary = "Listar todos los comentarios", description = "Recupera todo el historial de comentarios del sistema. **Accesible para TODOS los roles.**")
	@PreAuthorize("hasAnyRole('USUARIO', 'COMENTADOR', 'EDITOR', 'ADMINISTRATIVO')")
	@GetMapping("/listar")
	public ResponseEntity<List<ComentarioDTO>> listarTodos() {
		List<ComentarioDTO> comentarios = comentarioServ.getAll();
		if (comentarios.isEmpty()) {
			return new ResponseEntity<>(comentarios, HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(comentarios, HttpStatus.OK);
	}

	@Operation(summary = "Obtener comentarios de una publicación", description = "Filtra y devuelve todos los comentarios asociados a una Noticia u Horóscopo específico. **Accesible para TODOS los roles.**")
	@PreAuthorize("hasAnyRole('USUARIO', 'COMENTADOR', 'EDITOR', 'ADMINISTRATIVO')")
	@GetMapping("/publicacion/{publicacionId}")
	public ResponseEntity<List<ComentarioDTO>> buscarPorPublicacion(
			@Parameter(description = "ID de la publicación (Noticia u Horóscopo)", required = true) @PathVariable Long publicacionId) {

		List<ComentarioDTO> filtrados = comentarioServ.getByPublicacion(publicacionId);
		if (filtrados.isEmpty()) {
			return new ResponseEntity<>(filtrados, HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(filtrados, HttpStatus.OK);
	}

	@Operation(summary = "Obtener comentarios de un autor", description = "Filtra y devuelve todos los comentarios escritos por un usuario específico. **Accesible para TODOS los roles.**")
	@PreAuthorize("hasAnyRole('USUARIO', 'COMENTADOR', 'EDITOR', 'ADMINISTRATIVO')")
	@GetMapping("/autor/{username}")
	public ResponseEntity<List<ComentarioDTO>> buscarPorAutor(
			@Parameter(description = "Nombre de usuario (Username) del autor", required = true) @PathVariable String username) {

		List<ComentarioDTO> filtrados = comentarioServ.getByAutor(username);
		if (filtrados.isEmpty()) {
			return new ResponseEntity<>(filtrados, HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(filtrados, HttpStatus.OK);
	}

	@Operation(summary = "Actualizar comentario", description = "Modifica el texto de un comentario existente. **Requiere rol COMENTADOR o EDITOR.**")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Comentario actualizado exitosamente"),
			@ApiResponse(responseCode = "404", description = "Comentario no encontrado") })
	@PreAuthorize("hasAnyRole('COMENTADOR', 'EDITOR')")
	@PutMapping(path = "/actualizar/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> actualizar(
			@Parameter(description = "ID del comentario a actualizar", required = true) @PathVariable Long id,
			@Parameter(description = "Nuevos datos del comentario", required = true) @RequestBody ComentarioDTO newData) {

		int status = comentarioServ.updateById(id, newData);

		if (status == 0) {
			return new ResponseEntity<>("Comentario actualizado exitosamente", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Error: Comentario no encontrado", HttpStatus.NOT_FOUND);
		}
	}

	@Operation(summary = "Eliminar comentario (Moderación)", description = "Elimina permanentemente un comentario. Diseñado para moderación. **Requiere rol EDITOR.**")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Comentario eliminado exitosamente"),
			@ApiResponse(responseCode = "404", description = "Comentario no encontrado") })
	@PreAuthorize("hasRole('EDITOR')")
	@DeleteMapping("/eliminar/{id}")
	public ResponseEntity<String> eliminarPorId(
			@Parameter(description = "ID del comentario a eliminar", required = true) @PathVariable Long id) {

		int status = comentarioServ.deleteById(id);

		if (status == 0) {
			return new ResponseEntity<>("Comentario eliminado exitosamente por moderación", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Error: Comentario no encontrado", HttpStatus.NOT_FOUND);
		}
	}
}