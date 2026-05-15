package co.edu.unbosque.Revista.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unbosque.Revista.dto.NoticiaDTO;
import co.edu.unbosque.Revista.entity.Noticia;
import co.edu.unbosque.Revista.repository.NoticiaRepository;

@Service
public class NoticiaService implements CRUDOperation<NoticiaDTO> {

	@Autowired
	private NoticiaRepository noticiaRepo;

	@Autowired
	private ModelMapper modelMapper;

	public NoticiaService() {

	}

	@Override
	public int create(NoticiaDTO data) {
		Noticia entity = modelMapper.map(data, Noticia.class);

		Optional<Noticia> encontrada = noticiaRepo.findByTitulo(entity.getTitulo());

		if (encontrada.isPresent()) {
			return 1;
		} else {

			if (entity.getFechaPublicacion() == null) {
				entity.setFechaPublicacion(LocalDateTime.now());
			}
			noticiaRepo.save(entity);
			return 0;
		}
	}

	@Override
	public List<NoticiaDTO> getAll() {
		List<Noticia> entityList = noticiaRepo.findAll();
		List<NoticiaDTO> dtoList= new ArrayList<>();

		entityList.forEach((entity) -> {
			NoticiaDTO dto = modelMapper.map(entity, NoticiaDTO.class);
			dtoList.add(dto);
		});
		return dtoList;
	}

	@Override
	public int deleteById(Long id) {
		Optional<Noticia> encontrado = noticiaRepo.findById(id);
		if (encontrado.isPresent()) {
			noticiaRepo.delete(encontrado.get());
			return 0;
		} else {
			return 1;
		}
	}

	@Override
	public int updateById(Long id, NoticiaDTO newData) {
		Optional<Noticia> encontrado = noticiaRepo.findById(id);

		if (encontrado.isPresent()) {
			Noticia noticiaExistente = encontrado.get();

			noticiaExistente.setTitulo(newData.getTitulo());
			noticiaExistente.setContenido(newData.getContenido());
			noticiaExistente.setCategoria(newData.getCategoria());
			noticiaExistente.setFuente(newData.getFuente());
			noticiaExistente.setImagenUrl(newData.getImagenUrl());

			noticiaRepo.save(noticiaExistente);
			return 0;
		}
		return 1;
	}

	@Override
	public long count() {
		return noticiaRepo.count();
	}

	@Override
	public boolean exist(Long id) {
		return noticiaRepo.existsById(id);
	}

	public NoticiaDTO getById(Long id) {
		Optional<Noticia> encontrado = noticiaRepo.findById(id);
		if (encontrado.isPresent()) {
			return modelMapper.map(encontrado.get(), NoticiaDTO.class);
		} else {
			return null;
		}
	}

	public List<NoticiaDTO> getByCategoria(String categoria) {
		List<Noticia> entityList = noticiaRepo.findByCategoria(categoria);
		List<NoticiaDTO> dtoList = new ArrayList<>();

		for (Noticia noticia : entityList) {
			dtoList.add(modelMapper.map(noticia, NoticiaDTO.class));
		}
		return dtoList;
	}

}
