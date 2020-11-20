package com.br.cilene.funcionarios.resources;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolationException;
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

import com.br.cilene.funcionarios.dto.funcionario.AdicionaFunciorioRequestDto;
import com.br.cilene.funcionarios.dto.funcionario.AtualizaFunciorioRequestDto;
import com.br.cilene.funcionarios.dto.funcionario.FuncionarioDto;
import com.br.cilene.funcionarios.exceptions.CargoInexsistenteException;
import com.br.cilene.funcionarios.exceptions.DepartamentoInexsistenteException;
import com.br.cilene.funcionarios.exceptions.DocumentoRepetidoException;
import com.br.cilene.funcionarios.exceptions.UsuarioInexsistenteException;
import com.br.cilene.funcionarios.models.Funcionario;
import com.br.cilene.funcionarios.repositories.CargoRepository;
import com.br.cilene.funcionarios.repositories.DepartamentoRepository;
import com.br.cilene.funcionarios.repositories.FuncionarioRepository;
import com.br.cilene.funcionarios.services.FuncionarioService;

@RestController
@RequestMapping(path = "/funcionario")
public class FuncionarioResource {

	private FuncionarioRepository funcionarioRepository;
	
	@Autowired
	private FuncionarioService funcionarioService;

	@Autowired
	private ModelMapper modelMapper;

	public FuncionarioResource(FuncionarioRepository funcionarioRepository,
			DepartamentoRepository departamentoRepository,
			CargoRepository cargoRepository) {
		super();
		this.funcionarioRepository = funcionarioRepository;
	}

	@PostMapping
	public ResponseEntity<FuncionarioDto> save(@Valid @RequestBody AdicionaFunciorioRequestDto request) {
		try {
			FuncionarioDto funcionario = funcionarioService.AdicionaFuncionario(request);
			
			if(funcionario != null)
				return new ResponseEntity<>(modelMapper.map(funcionario, FuncionarioDto.class), HttpStatus.OK);
			else
				return new ResponseEntity<FuncionarioDto>(HttpStatus.BAD_REQUEST);
			
		} 
		catch (CargoInexsistenteException e) {

			return new ResponseEntity<FuncionarioDto>(HttpStatus.CONFLICT);
		}		
		catch (DepartamentoInexsistenteException e) {

			return new ResponseEntity<FuncionarioDto>(HttpStatus.CONFLICT);
		}		
		catch (DocumentoRepetidoException e) {

			return new ResponseEntity<FuncionarioDto>(HttpStatus.UNPROCESSABLE_ENTITY);
		}
		catch (ConstraintViolationException e) {

			return new ResponseEntity<FuncionarioDto>(HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping
	public ResponseEntity<List<FuncionarioDto>> getAll() {

		List<Funcionario> funcionarios = funcionarioRepository.findAll();
		List<FuncionarioDto> mapeado = funcionarios.stream()
				.map(funcionario -> modelMapper.map(funcionario, FuncionarioDto.class))
				.collect(Collectors.toList());

		return new ResponseEntity<>(mapeado, HttpStatus.OK);
	}

	@GetMapping(path = "/{id}")
	public ResponseEntity<FuncionarioDto> getById(@PathVariable Integer id) {
		Optional<Funcionario> funcionario = funcionarioRepository.findById(id);

		if (funcionario.isPresent())
			return new ResponseEntity<>(modelMapper.map(funcionario.get(), FuncionarioDto.class), HttpStatus.OK);
		else
			return new ResponseEntity<FuncionarioDto>(HttpStatus.NOT_FOUND);
	}

	@DeleteMapping(path = "/{id}")
	public ResponseEntity<FuncionarioDto> deleteById(@PathVariable Integer id) {

		try {
			funcionarioRepository.deleteById(id);
			return new ResponseEntity<FuncionarioDto>(HttpStatus.OK);
		} catch (EmptyResultDataAccessException e) {
			return new ResponseEntity<FuncionarioDto>(HttpStatus.NOT_FOUND);
		}
	}

	@PutMapping(path = "/{id}")
	public ResponseEntity<FuncionarioDto> update(@PathVariable Integer id, @RequestBody AtualizaFunciorioRequestDto newFuncionario) {
		try {
			FuncionarioDto funcionario = funcionarioService.AtualizaFuncionario(id, newFuncionario);
			
			if(funcionario != null)
				return new ResponseEntity<>(modelMapper.map(funcionario, FuncionarioDto.class), HttpStatus.OK);
			else
				return new ResponseEntity<FuncionarioDto>(HttpStatus.BAD_REQUEST);
			
		} 
		catch (UsuarioInexsistenteException e) {

			return new ResponseEntity<FuncionarioDto>(HttpStatus.NOT_FOUND);
		}		
		catch (DepartamentoInexsistenteException e) {

			return new ResponseEntity<FuncionarioDto>(HttpStatus.CONFLICT);
		}	
		catch (CargoInexsistenteException e) {

			return new ResponseEntity<FuncionarioDto>(HttpStatus.CONFLICT);
		}			
		catch (ConstraintViolationException e) {

			return new ResponseEntity<FuncionarioDto>(HttpStatus.BAD_REQUEST);
		}
	}

}
