package co.edu.unbosque.Revista.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unbosque.Revista.dto.ComentarioDTO;

import co.edu.unbosque.Revista.entity.Comentario;

import co.edu.unbosque.Revista.repository.ComentarioRepository;

@Service
public class ComentarioService implements CRUDOperation<ComentarioDTO> {

	@Autowired
	private ComentarioRepository comentarioRepo;

	@Autowired
	private ModelMapper modelMapper;

	public ComentarioService() {
	}

	@Override
	public int create(ComentarioDTO data) {
		Comentario entity = modelMapper.map(data, Comentario.class);
		entity.setFecha(LocalDateTime.now());
		comentarioRepo.save(entity);
		return 0;
	}

	@Override
	public List<ComentarioDTO> getAll() {
		List<Comentario> entityList = comentarioRepo.findAll();
		List<ComentarioDTO> dtoList = new ArrayList<>();

		entityList.forEach((entity) -> {
			ComentarioDTO dto = modelMapper.map(entity, ComentarioDTO.class);
			dtoList.add(dto);
		});
		return dtoList;
	}

	@Override
	public int deleteById(Long id) {
		Optional<Comentario> encontrado = comentarioRepo.findById(id);
		if (encontrado.isPresent()) {
			comentarioRepo.delete(encontrado.get());
			return 0;
		} else {
			return 1;
		}
	}

	@Override
	public int updateById(Long id, ComentarioDTO newData) {
		Optional<Comentario> existente = comentarioRepo.findById(id);

		if (existente.isPresent()) {
			Comentario temp = existente.get();

			temp.setContenido(newData.getContenido());
			temp.setFecha(LocalDateTime.now());

			comentarioRepo.save(temp);
			return 0;
		}
		return 1;
	}

	@Override
	public long count() {
		return comentarioRepo.count();
	}

	@Override
	public boolean exist(Long id) {
		return comentarioRepo.existsById(id);
	}


	public List<ComentarioDTO> getByPublicacion(Long publicacionId) {
		
		List<Comentario> entityList = comentarioRepo.findByPublicacionId(publicacionId);
		List<ComentarioDTO> dtoList = new ArrayList<>();

		for (Comentario c : entityList) {
			dtoList.add(modelMapper.map(c, ComentarioDTO.class));
		}
		return dtoList;
	}

	public List<ComentarioDTO> getByAutor(String username) {
		List<Comentario> entityList= comentarioRepo.findByAutor(username);
		List<ComentarioDTO> dtoList = new ArrayList<>();

		for (Comentario comentario : entityList) {
			dtoList.add(modelMapper.map(comentario, ComentarioDTO.class));
		}
		return dtoList;
	}
}
