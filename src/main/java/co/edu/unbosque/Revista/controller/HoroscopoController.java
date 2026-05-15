package co.edu.unbosque.Revista.controller;

import co.edu.unbosque.Revista.dto.HoroscopoDTO;
import co.edu.unbosque.Revista.service.HoroscopoService;
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
@RequestMapping("/api/horoscopos")
@CrossOrigin(origins = { "http://localhost:8080", "http://localhost:4200" })
@Transactional
@Tag(name = "Gestión de Horóscopos", description = "Endpoints para administrar los horóscopos de la Revista digital")
@SecurityRequirement(name = "bearerAuth") 
public class HoroscopoController {

	@Autowired
	private HoroscopoService horoscopoServ;

	public HoroscopoController() {
	}

	@Operation(summary = "Crear horóscopo", description = "Crea un nuevo horóscopo enviando los datos en formato JSON. **Requiere rol EDITOR.**")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Horóscopo creado exitosamente"),
			@ApiResponse(responseCode = "406", description = "Error: El título del horóscopo ya existe") })
	@PreAuthorize("hasRole('EDITOR')")
	@PostMapping(path = "/crear", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> crear(
			@Parameter(description = "Datos del nuevo horóscopo", required = true, schema = @Schema(implementation = HoroscopoDTO.class)) @RequestBody HoroscopoDTO newHoroscopo) {

		int status = horoscopoServ.create(newHoroscopo);

		if (status == 0) {
			return new ResponseEntity<>("Horóscopo creado exitosamente", HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>("Error: Ya existe un horóscopo con ese título", HttpStatus.NOT_ACCEPTABLE);
		}
	}

	@Operation(summary = "Obtener todos los horóscopos", description = "Recupera la lista de todos los horóscopos publicados. **Accesible para TODOS los roles.**")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Lista recuperada exitosamente"),
			@ApiResponse(responseCode = "204", description = "No hay horóscopos registrados") })
	@PreAuthorize("hasAnyRole('USUARIO', 'COMENTADOR', 'EDITOR', 'ADMINISTRATIVO')")
	@GetMapping("/listar")
	public ResponseEntity<List<HoroscopoDTO>> listarTodos() {
		List<HoroscopoDTO> horoscopos = horoscopoServ.getAll();
		if (horoscopos.isEmpty()) {
			return new ResponseEntity<>(horoscopos, HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(horoscopos, HttpStatus.OK);
	}

	@Operation(summary = "Obtener horóscopo por ID", description = "Busca los detalles de un horóscopo específico mediante su identificador. **Accesible para TODOS los roles.**")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Horóscopo encontrado"),
			@ApiResponse(responseCode = "404", description = "Horóscopo no encontrado") })
	@PreAuthorize("hasAnyRole('USUARIO', 'COMENTADOR', 'EDITOR', 'ADMINISTRATIVO')")
	@GetMapping("/buscar/{id}")
	public ResponseEntity<HoroscopoDTO> buscarPorId(@PathVariable Long id) {
		// NOTA: Asegúrate de tener el método getById(Long id) en tu HoroscopoService
		HoroscopoDTO found = horoscopoServ.getById(id);
		if (found != null) {
			return new ResponseEntity<>(found, HttpStatus.OK);
		}
		return new ResponseEntity<>(new HoroscopoDTO(), HttpStatus.NOT_FOUND);
	}

	@Operation(summary = "Buscar horóscopos por signo zodiacal", description = "Filtra y devuelve todos los horóscopos pertenecientes a un signo específico (Ej. 'Aries'). **Accesible para TODOS los roles.**")
	@PreAuthorize("hasAnyRole('USUARIO', 'COMENTADOR', 'EDITOR', 'ADMINISTRATIVO')")
	@GetMapping("/signo/{signo}")
	public ResponseEntity<List<HoroscopoDTO>> buscarPorSigno(
			@Parameter(description = "Nombre del signo zodiacal", required = true, example = "Aries") @PathVariable String signo) {
		// NOTA: Asegúrate de tener el método getBySigno(String signo) en tu
		// HoroscopoService
		List<HoroscopoDTO> filtrados = horoscopoServ.getBySigno(signo);
		if (filtrados.isEmpty()) {
			return new ResponseEntity<>(filtrados, HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(filtrados, HttpStatus.OK);
	}

	@Operation(summary = "Actualizar horóscopo", description = "Actualiza los campos de un horóscopo existente (Predicción, compatibilidad, etc.). **Requiere rol EDITOR.**")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Horóscopo actualizado exitosamente"),
			@ApiResponse(responseCode = "404", description = "Horóscopo no encontrado") })
	@PreAuthorize("hasRole('EDITOR')")
	@PutMapping(path = "/actualizar/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> actualizar(
			@Parameter(description = "ID del horóscopo a actualizar", required = true) @PathVariable Long id,
			@Parameter(description = "Nuevos datos", required = true) @RequestBody HoroscopoDTO newData) {

		int status = horoscopoServ.updateById(id, newData);

		if (status == 0) {
			return new ResponseEntity<>("Horóscopo actualizado exitosamente", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Error: Horóscopo no encontrado", HttpStatus.NOT_FOUND);
		}
	}

	@Operation(summary = "Eliminar horóscopo por ID", description = "Elimina permanentemente un horóscopo de la base de datos. **Requiere rol EDITOR.**")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Horóscopo eliminado exitosamente"),
			@ApiResponse(responseCode = "404", description = "Horóscopo no encontrado") })
	@PreAuthorize("hasRole('EDITOR')")
	@DeleteMapping("/eliminar/{id}")
	public ResponseEntity<String> eliminarPorId(
			@Parameter(description = "ID del horóscopo a eliminar", required = true) @PathVariable Long id) {

		int status = horoscopoServ.deleteById(id);

		if (status == 0) {
			return new ResponseEntity<>("Horóscopo eliminado exitosamente", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Error: Horóscopo no encontrado", HttpStatus.NOT_FOUND);
		}
	}
}