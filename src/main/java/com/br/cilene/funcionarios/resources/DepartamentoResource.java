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

import com.br.cilene.funcionarios.dto.departamento.AdicionaDepartamentoRequestDto;
import com.br.cilene.funcionarios.dto.departamento.AtualizaDepartamentoRequestDto;
import com.br.cilene.funcionarios.dto.departamento.DepartamentoDto;
import com.br.cilene.funcionarios.models.Departamento;
import com.br.cilene.funcionarios.repositories.DepartamentoRepository;

@RestController
@RequestMapping(path="/departamento")
public class DepartamentoResource {

	private DepartamentoRepository departamentoRepository;
	
	@Autowired
	private ModelMapper modelMapper;	

	public DepartamentoResource(DepartamentoRepository departamentoRepository) {
		super();
		this.departamentoRepository = departamentoRepository;
	}
	
	@PostMapping
	public ResponseEntity<DepartamentoDto> save(@Valid @RequestBody AdicionaDepartamentoRequestDto request){
		
		Departamento departamento = modelMapper.map(request, Departamento.class);
		departamentoRepository.save(departamento);
		return new ResponseEntity<>(modelMapper.map(departamento, DepartamentoDto.class), HttpStatus.OK);
	}	
	
	@GetMapping
	public ResponseEntity<List<DepartamentoDto>> getAll() {

		List<Departamento> departamentos = departamentoRepository.findAll();
		List<DepartamentoDto> mapeado = departamentos.stream()
				.map(departamento -> modelMapper.map(departamento, DepartamentoDto.class))
				.collect(Collectors.toList());

		return new ResponseEntity<>(mapeado, HttpStatus.OK);
	}

	@GetMapping(path = "/{id}")
	public ResponseEntity<DepartamentoDto> getById(@PathVariable Integer id) {
		Optional<Departamento> departamento = departamentoRepository.findById(id);

		if (departamento.isPresent())
			return new ResponseEntity<>(modelMapper.map(departamento.get(), DepartamentoDto.class), HttpStatus.OK);
		else
			return new ResponseEntity<DepartamentoDto>(HttpStatus.NOT_FOUND);
	}

	@DeleteMapping(path = "/{id}")
	public ResponseEntity<DepartamentoDto> deleteById(@PathVariable Integer id) {

		try {
			departamentoRepository.deleteById(id);
			return new ResponseEntity<DepartamentoDto>(HttpStatus.NO_CONTENT);
		} catch (EmptyResultDataAccessException e) {
			return new ResponseEntity<DepartamentoDto>(HttpStatus.NOT_FOUND);
		}
	}

	@PutMapping(path = "/{id}")
	public ResponseEntity<DepartamentoDto> update(@PathVariable Integer id, @RequestBody AtualizaDepartamentoRequestDto newDepartamento) {
		return departamentoRepository.findById(id).map(departamento -> {
			departamento.setNome(newDepartamento.getNome());

			Departamento atualizado = departamentoRepository.save(departamento);
			return ResponseEntity.ok().body(modelMapper.map(atualizado, DepartamentoDto.class));
		}).orElse(ResponseEntity.notFound().build());
	}	
}
