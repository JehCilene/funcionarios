package com.br.cilene.funcionarios.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

@Entity
public class Departamento {

	@GeneratedValue
	@Id
	private int id;
	
	@Column(name = "funcionario_name")
	@NotEmpty
	@Length(min = 5, max = 50)
	private String nome;	
}
