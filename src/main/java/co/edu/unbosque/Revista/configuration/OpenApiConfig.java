package co.edu.unbosque.Revista.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.examples.Example;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

	@Bean
	public OpenAPI customOpenAPI() {
		
		String mainDescription = "<h2>Guía de la API - Revista Digital Unbosque</h2>"
				+ "<p>Esta API gestiona el flujo de publicaciones (Noticias y Horóscopos), Comentarios y Usuarios.</p>"
				+ "<h3>Permisos por Rol:</h3>" + "<ul>"
				+ "  <li><strong>EDITOR:</strong> Control total. Puede crear, modificar y eliminar noticias y horóscopos.</li>"
				+ "  <li><strong>COMENTADOR:</strong> Puede listar publicaciones y escribir comentarios.</li>"
				+ "  <li><strong>USUARIO:</strong> Lector básico. Puede listar y leer noticias.</li>"
				+ "  <li><strong>ADMINISTRATIVO:</strong> Gestión administrativa de usuarios.</li>" + "</ul>"
				+ "<h3>Autenticación JWT:</h3>"
				+ "<p>Usa el botón <strong>Authorize</strong> arriba a la derecha después de obtener tu token en el login.</p>";

		
		String securityDescription = "Autenticación Bearer mediante JWT." + "<p>Pasos para probar los endpoints:</p>"
				+ "<ol>" + "  <li>Haz login en el controlador de Auth para recibir tu token.</li>"
				+ "  <li>Copia el valor del token.</li>"
				+ "  <li>Presiona el botón <strong>Authorize</strong> (el candado).</li>"
				+ "  <li>Escribe exactamente: <code>Bearer TU_TOKEN_AQUI</code></li>" + "</ol>";

		
		Info info = new Info().title("API Revista Digital - JAIRO ESTEBAN - NATALIA DIAZ").version("1.0").description(mainDescription)
				.contact(new Contact().name("Equipo de Desarrollo Revista").email("jairo.esteban@unbosque.edu.co"))
				.license(new License().name("Licencia MIT").url("https://opensource.org/licenses/MIT"));

		
		SecurityScheme securityScheme = new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer")
				.bearerFormat("JWT").description(securityDescription);

		return new OpenAPI().info(info).components(new Components().addSecuritySchemes("bearerAuth", securityScheme)
				
				.addResponses("UnauthorizedError", new ApiResponse().description("No autenticado - Token inválido")
						.content(new Content().addMediaType("application/json",
								new MediaType().addExamples("error", new Example().value(
										"{\"error\": \"No autorizado\", \"mensaje\": \"Token inválido o expirado\"}")))))
				.addResponses("ForbiddenError", new ApiResponse()
						.description("Acceso denegado - No tienes el rol necesario")
						.content(new Content().addMediaType("application/json",
								new MediaType().addExamples("error", new Example().value(
										"{\"error\": \"Acceso prohibido\", \"mensaje\": \"Tu rol no permite esta acción\"}"))))));
	}
}