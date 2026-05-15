package co.edu.unbosque.Revista.controller;

import co.edu.unbosque.Revista.dto.NoticiaDTO;
import co.edu.unbosque.Revista.service.NoticiaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
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
@RequestMapping("/api/noticias")
@CrossOrigin(origins = { "http://localhost:8080", "http://localhost:4200" })
@Transactional
@Tag(name = "Gestión de Noticias", description = "Endpoints para administrar las noticias de la Revista digital")
@SecurityRequirement(name = "bearerAuth") 
public class NoticiaController {

	@Autowired
	private NoticiaService noticiaServ;

	public NoticiaController() {
	}

	@Operation(summary = "Crear noticia", description = "Crea una nueva noticia enviando los datos en formato JSON. Si no se envía fecha, el sistema asignará la actual automáticamente. **Requiere rol EDITOR.**")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Noticia creada exitosamente"),
			@ApiResponse(responseCode = "406", description = "Error: El título de la noticia ya existe") })
	@PreAuthorize("hasRole('EDITOR')")
	@PostMapping(path = "/crear", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> crear(
			@Parameter(description = "Datos de la nueva noticia", required = true, schema = @Schema(implementation = NoticiaDTO.class)) @RequestBody NoticiaDTO newNoticia) {

		int status = noticiaServ.create(newNoticia);

		if (status == 0) {
			return new ResponseEntity<>("Noticia creada exitosamente", HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>("Error: Ya existe una noticia con ese título", HttpStatus.NOT_ACCEPTABLE);
		}
	}

	@Operation(summary = "Obtener todas las noticias", description = "Recupera la lista de todas las noticias publicadas en la revista. **Accesible para TODOS los roles.**")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Lista recuperada exitosamente"),
			@ApiResponse(responseCode = "204", description = "No hay noticias registradas") })
	@PreAuthorize("hasAnyRole('USUARIO', 'COMENTADOR', 'EDITOR', 'ADMINISTRATIVO')")
	@GetMapping("/listar")
	public ResponseEntity<List<NoticiaDTO>> listarTodas() {
		List<NoticiaDTO> noticias = noticiaServ.getAll();
		if (noticias.isEmpty()) {
			return new ResponseEntity<>(noticias, HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(noticias, HttpStatus.OK);
	}

	@Operation(summary = "Obtener noticia por ID", description = "Busca los detalles de una noticia específica mediante su identificador único. **Accesible para TODOS los roles.**")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Noticia encontrada"),
			@ApiResponse(responseCode = "404", description = "Noticia no encontrada") })
	@PreAuthorize("hasAnyRole('USUARIO', 'COMENTADOR', 'EDITOR', 'ADMINISTRATIVO')")
	@GetMapping("/buscar/{id}")
	public ResponseEntity<NoticiaDTO> buscarPorId(@PathVariable Long id) {
		NoticiaDTO found = noticiaServ.getById(id);
		if (found != null) {
			return new ResponseEntity<>(found, HttpStatus.OK);
		}
		return new ResponseEntity<>(new NoticiaDTO(), HttpStatus.NOT_FOUND);
	}

	@Operation(summary = "Actualizar noticia", description = "Actualiza los campos de una noticia existente (Título, contenido, categoría, fuente, etc). **Requiere rol EDITOR.**")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Noticia actualizada exitosamente"),
			@ApiResponse(responseCode = "404", description = "Noticia no encontrada") })
	@PreAuthorize("hasRole('EDITOR')")
	@PutMapping(path = "/actualizar/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> actualizar(
			@Parameter(description = "ID de la noticia a actualizar", required = true) @PathVariable Long id,
			@Parameter(description = "Nuevos datos de la noticia", required = true) @RequestBody NoticiaDTO newData) {

		int status = noticiaServ.updateById(id, newData);

		if (status == 0) {
			return new ResponseEntity<>("Noticia actualizada exitosamente", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Error: Noticia no encontrada", HttpStatus.NOT_FOUND);
		}
	}

	@Operation(summary = "Eliminar noticia por ID", description = "Elimina permanentemente una noticia de la base de datos. **Requiere rol EDITOR.**")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Noticia eliminada exitosamente"),
			@ApiResponse(responseCode = "404", description = "Noticia no encontrada") })
	@PreAuthorize("hasRole('EDITOR')")
	@DeleteMapping("/eliminar/{id}")
	public ResponseEntity<String> eliminarPorId(
			@Parameter(description = "ID de la noticia a eliminar", required = true) @PathVariable Long id) {

		int status = noticiaServ.deleteById(id);

		if (status == 0) {
			return new ResponseEntity<>("Noticia eliminada exitosamente", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Error: Noticia no encontrada", HttpStatus.NOT_FOUND);
		}
	}
}