package com.br.cilene.funcionarios.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "cargo")
public class Cargo {

	@GeneratedValue
	@Id
	@Column(name = "cargo_id")
	private int id;
	
	@Column(name = "cargo_name")
	@NotEmpty
	@Length(max = 50)	
	private String nome;
	
	@OneToMany(mappedBy = "cargo", cascade =  {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH},fetch = FetchType.LAZY)
	private List<Funcionario> funcionarios;
	
	public Cargo() {
		
	}

	public Cargo(String nome) {
		super();
		this.nome = nome;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<Funcionario> getFuncionarios() {
		return funcionarios;
	}

	public void setFuncionarios(List<Funcionario> funcionario) {
		this.funcionarios = funcionario;
	}
	
	
	
}
