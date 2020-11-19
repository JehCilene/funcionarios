package com.br.cilene.funcionarios.resources;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.cilene.funcionarios.models.Cargo;
import com.br.cilene.funcionarios.repositories.CargoRepository;

@RestController
@RequestMapping(path="/cargo")
public class CargoResource {

	private CargoRepository cargoRepository;

	public CargoResource(CargoRepository cargoRepository) {
		super();
		this.cargoRepository = cargoRepository;
	}
	
	@PostMapping
	public ResponseEntity<Cargo> save(@Valid @RequestBody Cargo cargo){
		cargoRepository.save(cargo);
		return new ResponseEntity<>(cargo, HttpStatus.OK);
	}	
}
