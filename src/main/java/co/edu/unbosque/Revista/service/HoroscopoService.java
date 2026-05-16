package co.edu.unbosque.Revista.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unbosque.Revista.dto.HoroscopoDTO;
import co.edu.unbosque.Revista.entity.Horoscopo;
import co.edu.unbosque.Revista.repository.HoroscopoRepository;

@Service
public class HoroscopoService implements CRUDOperation<HoroscopoDTO> {

	@Autowired
	private HoroscopoRepository horoscopoRepo;

	@Autowired
	private ModelMapper modelMapper;

	public HoroscopoService() {
	}

	@Override
	public int create(HoroscopoDTO data) {

		Horoscopo entity = modelMapper.map(data, Horoscopo.class);

		Optional<Horoscopo> encontrado = horoscopoRepo.findByTitulo(entity.getTitulo());

		if (encontrado.isPresent()) {
			return 1;
		} else {

			if (entity.getFechaPublicacion() == null) {
				entity.setFechaPublicacion(LocalDateTime.now());
			}
			horoscopoRepo.save(entity);
			return 0;
		}
	}

	@Override
	public List<HoroscopoDTO> getAll() {
		List<Horoscopo> entityList = (List<Horoscopo>)horoscopoRepo.findAll();
		List<HoroscopoDTO> dtoList = new ArrayList<>();

		entityList.forEach(entity -> 
			dtoList.add(modelMapper.map(entity, HoroscopoDTO.class)));
		
		return dtoList;
	}

	@Override
	public int deleteById(Long id) {
		Optional<Horoscopo> encontrado = horoscopoRepo.findById(id);
		if (encontrado.isPresent()) {
			horoscopoRepo.delete(encontrado.get());
			return 0;
		} else {
			return 1;
		}

	}

	@Override
	public int updateById(Long id, HoroscopoDTO newData) {
		Optional<Horoscopo> existente = horoscopoRepo.findById(id);

		if (existente.isPresent()) {
			Horoscopo temp = existente.get();

			temp.setTitulo(newData.getTitulo());
			temp.setContenido(newData.getContenido());
			temp.setSignoZodiacal(newData.getSignoZodiacal());
			temp.setPrediccion(newData.getPrediccion());

			horoscopoRepo.save(temp);
			return 0;
		}
		return 1;
	}

	@Override
	public long count() {
		return horoscopoRepo.count();
	}

	@Override
	public boolean exist(Long id) {
		return horoscopoRepo.existsById(id);
	}

	public HoroscopoDTO getById(Long id) {
		Optional<Horoscopo> encontrado = horoscopoRepo.findById(id);
		if (encontrado.isPresent()) {
			return modelMapper.map(encontrado.get(), HoroscopoDTO.class);
		} else {
			return null;
		}
	}

	public List<HoroscopoDTO> getBySigno(String signo) {
		List<Horoscopo> entidades = horoscopoRepo.findBySignoZodiacal(signo);
		List<HoroscopoDTO> dtos = new ArrayList<>();

		for (Horoscopo h : entidades) {
			dtos.add(modelMapper.map(h, HoroscopoDTO.class));
		}
		return dtos;
	}
}