package com.br.cilene.funcionarios.resources;

import java.util.ArrayList;
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

import com.br.cilene.funcionarios.dto.FuncionarioDto;
import com.br.cilene.funcionarios.models.Funcionario;
import com.br.cilene.funcionarios.repositories.FuncionarioRepository;

@RestController
@RequestMapping(path="/funcionario")
public class FuncionarioResource {
	
	private FuncionarioRepository funcionarioRepository;
	
    @Autowired
    private ModelMapper modelMapper;

	public FuncionarioResource(FuncionarioRepository funcionarioRepository) {
		super();
		this.funcionarioRepository = funcionarioRepository;
	}
	
	@PostMapping
	public ResponseEntity<Funcionario> save(@Valid @RequestBody Funcionario funcionario){
		funcionarioRepository.save(funcionario);
		return new ResponseEntity<>(funcionario, HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity<List<FuncionarioDto>> getAll(){
		
		List<Funcionario> funcionarios = funcionarioRepository.findAll();
        List<FuncionarioDto> mapeado = funcionarios.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());	
        
		/*
		 * List<FuncionarioDto> mapeado2 = new ArrayList<>();
		 * 
		 * for(Funcionario funcionario : funcionarios) {
		 * mapeado2.add(modelMapper.map(funcionario, FuncionarioDto.class)); }
		 */
		return new ResponseEntity<>(mapeado, HttpStatus.OK);
	}
	
	@GetMapping(path="/{id}")
	public ResponseEntity<Optional<Funcionario>> getById(@PathVariable Integer id){
		Optional<Funcionario> funcionario = funcionarioRepository.findById(id);
		
		if(funcionario.isPresent())
			return new ResponseEntity<>(funcionario,HttpStatus.OK);
		else
			return new ResponseEntity<Optional<Funcionario>>(HttpStatus.NOT_FOUND);
	}
	
	@DeleteMapping(path="/{id}")
	public ResponseEntity<Optional<Funcionario>> deleteById(@PathVariable Integer id){
		
		try {
			funcionarioRepository.deleteById(id);
			return new ResponseEntity<Optional<Funcionario>>(HttpStatus.OK);
		} catch (EmptyResultDataAccessException e) {
			return new ResponseEntity<Optional<Funcionario>>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PutMapping(path="/{id}")
	public ResponseEntity<Funcionario> update(@PathVariable Integer id, @RequestBody Funcionario newFuncionario){
		return funcionarioRepository.findById(id)
				.map(funcionario -> {
					funcionario.setNome(newFuncionario.getNome());
					funcionario.setIdade(newFuncionario.getIdade());
					funcionario.setDataNascimento(newFuncionario.getDataNascimento());
					funcionario.setDocumento(newFuncionario.getDocumento());
					funcionario.setCargo(newFuncionario.getCargo());
					Funcionario atualizado = funcionarioRepository.save(funcionario);
					return ResponseEntity.ok().body(atualizado);
				}).orElse(ResponseEntity.notFound().build());
	}
	
	private FuncionarioDto convertToDto(Funcionario funcionario) {
		FuncionarioDto postDto = modelMapper.map(funcionario, FuncionarioDto.class);

	    return postDto;	
	}
	
}
