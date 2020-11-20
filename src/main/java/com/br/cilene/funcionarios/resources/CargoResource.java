package com.br.cilene.funcionarios.resources;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.cilene.funcionarios.dto.cargo.AdicionaCargoRequestDto;
import com.br.cilene.funcionarios.dto.cargo.AtualizaCargoRequestDto;
import com.br.cilene.funcionarios.dto.cargo.CargoDto;
import com.br.cilene.funcionarios.models.Cargo;
import com.br.cilene.funcionarios.repositories.CargoRepository;

@RestController
@RequestMapping(path="/cargo")
public class CargoResource {

	private CargoRepository cargoRepository;
	
	@Autowired
	private ModelMapper modelMapper;	

	public CargoResource(CargoRepository cargoRepository) {
		super();
		this.cargoRepository = cargoRepository;
	}
	
	@PostMapping
	public ResponseEntity<CargoDto> save(@Valid @RequestBody AdicionaCargoRequestDto request){
		
		Cargo cargo = modelMapper.map(request, Cargo.class);
		cargoRepository.save(cargo);
		return new ResponseEntity<>(modelMapper.map(cargo, CargoDto.class), HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity<List<CargoDto>> getAll() {

		List<Cargo> cargos = cargoRepository.findAll();
		List<CargoDto> mapeado = cargos.stream()
				.map(cargo -> modelMapper.map(cargo, CargoDto.class))
				.collect(Collectors.toList());

		return new ResponseEntity<>(mapeado, HttpStatus.OK);
	}

	@GetMapping(path = "/{id}")
	public ResponseEntity<CargoDto> getById(@PathVariable Integer id) {
		Optional<Cargo> cargo = cargoRepository.findById(id);

		if (cargo.isPresent())
			return new ResponseEntity<>(modelMapper.map(cargo.get(), CargoDto.class), HttpStatus.OK);
		else
			return new ResponseEntity<CargoDto>(HttpStatus.NOT_FOUND);
	}

	@DeleteMapping(path = "/{id}")
	public ResponseEntity<CargoDto> deleteById(@PathVariable Integer id) {

		try {
			cargoRepository.deleteById(id);
			return new ResponseEntity<CargoDto>(HttpStatus.NO_CONTENT);
		} catch (EmptyResultDataAccessException e) {
			return new ResponseEntity<CargoDto>(HttpStatus.NOT_FOUND);
		}
	}

	@PutMapping(path = "/{id}")
	public ResponseEntity<CargoDto> update(@PathVariable Integer id, @RequestBody AtualizaCargoRequestDto newCargo) {
		return cargoRepository.findById(id).map(cargo -> {
			cargo.setNome(newCargo.getNome());

			Cargo atualizado = cargoRepository.save(cargo);
			return ResponseEntity.ok().body(modelMapper.map(atualizado, CargoDto.class));
		}).orElse(ResponseEntity.notFound().build());
	}	
}
