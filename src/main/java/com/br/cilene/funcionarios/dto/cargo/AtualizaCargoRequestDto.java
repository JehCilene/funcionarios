package com.br.cilene.funcionarios.dto.cargo;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

public class AtualizaCargoRequestDto {
	
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
