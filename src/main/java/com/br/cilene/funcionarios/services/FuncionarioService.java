package com.br.cilene.funcionarios.services;

import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.joda.time.DateTime;
import org.joda.time.Years;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.cilene.funcionarios.dto.funcionario.AdicionaFunciorioRequestDto;
import com.br.cilene.funcionarios.dto.funcionario.FuncionarioDto;
import com.br.cilene.funcionarios.models.Cargo;
import com.br.cilene.funcionarios.models.Departamento;
import com.br.cilene.funcionarios.models.Funcionario;
import com.br.cilene.funcionarios.repositories.CargoRepository;
import com.br.cilene.funcionarios.repositories.DepartamentoRepository;
import com.br.cilene.funcionarios.repositories.FuncionarioRepository;

@Service
public class FuncionarioService {

	@Autowired
	private FuncionarioRepository funcionarioRepository;
	@Autowired
	private DepartamentoRepository departamentoRepository;
	@Autowired
	private CargoRepository cargoRepository;
	@Autowired
	private ModelMapper modelMapper;
	
	public FuncionarioDto AdicionaFuncionario(AdicionaFunciorioRequestDto request) {
		
		if(existeFuncionarioComDocumento(request.getDocumento()))
			return null;
		
		Funcionario funcionario = modelMapper.map(request, Funcionario.class);
		
		//O mapeamento por algum motivo esta trazendo o valor do cargoId
		//Por isso estou zerando ele aqui.
		funcionario.setId(0);
		
		funcionario.setIdade(this.getIdade(request.getDataNascimento()));
		
	    Set<Departamento> departamentos = new HashSet<>();
		
		for(Integer id : request.getDepartamentosId())
		{
			Optional<Departamento> departamento = departamentoRepository.findById(id);
			
			if(!departamento.isPresent())
				return null;
			
			departamentos.add(departamento.get());
		}
		
		Optional<Cargo> cargo = cargoRepository.findById(request.getCargoId());
		
		if(!cargo.isPresent())
			return null;
		
		funcionario.setDepartamentos(departamentos);
		funcionario.setCargo(cargo.get());
		
		funcionarioRepository.save(funcionario);
		return modelMapper.map(funcionario, FuncionarioDto.class);
	}
	
	private int getIdade(Date dataNascimento) {
		DateTime dateTime = new DateTime(dataNascimento);
		DateTime agora = new DateTime();

		int idade = Math.abs(Years.yearsBetween(agora, dateTime).getYears());
		  
		return idade;
	}
	
	private Boolean existeFuncionarioComDocumento(String documento) {
		
		return funcionarioRepository.existsByDocumento(documento);
		
	}
}
