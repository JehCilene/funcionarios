package com.br.cilene.funcionarios.dto.funcionario;

import java.util.Date;
import java.util.Set;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;

public class AtualizaFunciorioRequestDto {
	
	@NotEmpty
	@Length(min = 5, max = 50)
	public String nome;
	
	@NotNull
	private int idade;
	
	@JsonFormat(pattern = "dd/MM/yyyy")
	@NotNull
	private Date dataNascimento;
	
	@NotNull
	@Length(min = 5, max = 50)
	private String documento;
	
	@NotNull
	private int cargoId;
	
	@NotNull
	private Set<Integer> departamentosId;
		
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getIdade() {
		return idade;
	}

	public void setIdade(int idade) {
		this.idade = idade;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public int getCargoId() {
		return cargoId;
	}

	public void setCargoId(int cargoId) {
		this.cargoId = cargoId;
	}

	public Set<Integer> getDepartamentosId() {
		return departamentosId;
	}

	public void setDepartamentosId(Set<Integer> departamentosId) {
		this.departamentosId = departamentosId;
	}

	
	

}
