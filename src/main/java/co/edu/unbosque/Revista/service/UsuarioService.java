package co.edu.unbosque.Revista.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import co.edu.unbosque.Revista.dto.UsuarioDTO;
import co.edu.unbosque.Revista.entity.Usuario;
import co.edu.unbosque.Revista.repository.UsuarioRepository;
import co.edu.unbosque.Revista.util.LanzadorDeException;

import org.modelmapper.ModelMapper;

@Service
public class UsuarioService implements CRUDOperation<UsuarioDTO> {

	@Autowired
	private UsuarioRepository usuarioRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public UsuarioService() {

	}

	@Override
	public int create(UsuarioDTO data) {

		LanzadorDeException.verifyPassword(data.getPassword());
		boolean existe = findUsernameAlreadyTaken(data.getUsername());
		LanzadorDeException.verifyUniqueUsername(existe, data.getUsername());

		Usuario entity = modelMapper.map(data, Usuario.class);
		entity.setPassword(passwordEncoder.encode(entity.getPassword()));

		if (data.getRol() != null) {
			entity.setRol(data.getRol());
		}

		usuarioRepo.save(entity);
		return 0;
	}

	@Override
	public List<UsuarioDTO> getAll() {
		List<Usuario> entityList = usuarioRepo.findAll();
		List<UsuarioDTO> dtoList = new ArrayList<>();

		entityList.forEach((entity) -> {
			UsuarioDTO dto = modelMapper.map(entity, UsuarioDTO.class);
			dtoList.add(dto);
		});
		return dtoList;
	}

	@Override
	public int deleteById(Long id) {
		Optional<Usuario> encontrado = usuarioRepo.findById(id);
		LanzadorDeException.verifyResourceFound(encontrado.isPresent(), "Usuario");
		usuarioRepo.delete(encontrado.get());
		return 0;
	}

	@Override
	public int updateById(Long id, UsuarioDTO newData) {

		Optional<Usuario> encontrado = usuarioRepo.findById(id);

		LanzadorDeException.verifyResourceFound(encontrado.isPresent(), "Usuario");

		Usuario usuarioExistente = encontrado.get();
		usuarioExistente.setUsername(newData.getUsername());
		usuarioExistente.setRol(newData.getRol());

		if (newData.getPassword() != null && !newData.getPassword().isEmpty()) {
			LanzadorDeException.verifyPassword(newData.getPassword());
			usuarioExistente.setPassword(passwordEncoder.encode(newData.getPassword()));
		}

		usuarioRepo.save(usuarioExistente);
		return 0;
	}

	@Override
	public long count() {
		return usuarioRepo.count();
	}

	@Override
	public boolean exist(Long id) {
		return usuarioRepo.existsById(id);
	}

	public int deleteByUsername(String username) {
		Optional<Usuario> encontrado = usuarioRepo.findByUsername(username);
		LanzadorDeException.verifyResourceFound(encontrado.isPresent(), "Usuario: " + username);

		usuarioRepo.delete(encontrado.get());
		return 0;
	}

	public UsuarioDTO getById(Long id) {
		Optional<Usuario> encontrado = usuarioRepo.findById(id);
		LanzadorDeException.verifyResourceFound(encontrado.isPresent(), "Usuario");

		return modelMapper.map(encontrado.get(), UsuarioDTO.class);
	}

	public boolean findUserAlreadyTaken(Usuario newUsuario) {
		Optional<Usuario> encontrado = usuarioRepo.findByUsername(newUsuario.getUsername());
		if (encontrado.isPresent()) {
			return true;
		} else {
			return false;
		}

	}

	public boolean findUsernameAlreadyTaken(String username) {
		Optional<Usuario> encontrado = usuarioRepo.findByUsername(username);
		return encontrado.isPresent();
	}

	public int validateCredentials(String username, String password) {
		Optional<Usuario> usuarioOpt = usuarioRepo.findByUsername(username);

		if (usuarioOpt.isPresent()) {
			Usuario usuario = usuarioOpt.get();
			if (passwordEncoder.matches(password, usuario.getPassword())) {
				return 0;
			}
		}

		return 1;
	}

}
