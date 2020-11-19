package com.br.cilene.funcionarios.dto.funcionario;

import java.util.Date;

import com.br.cilene.funcionarios.dto.CargoDto;
import com.fasterxml.jackson.annotation.JsonFormat;

public class AdicionaFunciorioRequestDto {
	
	public String nome;
	private int idade;
	
	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date dataNascimento;
	private String documento;
	private CargoDto cargo;

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

	public CargoDto getCargo() {
		return cargo;
	}

	public void setCargo(CargoDto cargo) {
		this.cargo = cargo;
	}
	
	
	

}
