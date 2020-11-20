package com.br.cilene.funcionarios.dto.departamento;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

public class AdicionaDepartamentoRequestDto {
	
	@NotNull
	@Length(max = 50)	
	private String nome;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	
}
