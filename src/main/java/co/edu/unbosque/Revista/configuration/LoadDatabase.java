package co.edu.unbosque.Revista.configuration;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import co.edu.unbosque.Revista.entity.Usuario;
import co.edu.unbosque.Revista.entity.Rol;
import co.edu.unbosque.Revista.repository.UsuarioRepository;

@Configuration
public class LoadDatabase {

	private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

	@Bean
	CommandLineRunner initDatabase(UsuarioRepository usuarioRepo, PasswordEncoder passwordEncoder) {

		return args -> {

			Optional<Usuario> foundAdmin = usuarioRepo.findByUsername("admin");
			if (foundAdmin.isPresent()) {
				log.info("El ADMINISTRATIVO 'admin' ya existe, omitiendo...");
			} else {
				Usuario admin = new Usuario();
				admin.setUsername("admin");
				admin.setPassword(passwordEncoder.encode("admin123"));
				admin.setRol(Rol.ADMINISTRATIVO);
				usuarioRepo.save(admin);
				log.info(">>> Precargando usuario EDITOR: admin / admin123");
			}

			Optional<Usuario> foundEditor = usuarioRepo.findByUsername("editor");
			if (foundEditor.isPresent()) {
				log.info("El EDITOR 'editor' ya existe, omitiendo...");
			} else {
				Usuario editor = new Usuario();
				editor.setUsername("editor");
				editor.setPassword(passwordEncoder.encode("editor123"));
				editor.setRol(Rol.EDITOR); 
				usuarioRepo.save(editor);
				log.info(">>> Precargando usuario EDITOR: editor / editor123");
			}

			Optional<Usuario> foundUser = usuarioRepo.findByUsername("jairo");
			if (foundUser.isPresent()) {
				log.info("El USUARIO 'jairo' ya existe, omitiendo...");
			} else {
				Usuario lector = new Usuario();
				lector.setUsername("jairo");
				lector.setPassword(passwordEncoder.encode("jairo123"));
				lector.setRol(Rol.USUARIO);
				usuarioRepo.save(lector);
				log.info(">>> Precargando usuario LECTOR: jairo / jairo123");
			}

			if (usuarioRepo.findByUsername("comentador").isEmpty()) {
				Usuario comentador = new Usuario();
				comentador.setUsername("comentador");
				comentador.setPassword(passwordEncoder.encode("coment123"));
				comentador.setRol(Rol.COMENTADOR);
				usuarioRepo.save(comentador);
				log.info(">>> Precargando usuario COMENTADOR: comentador / coment123");
			}
		};
	}
}
