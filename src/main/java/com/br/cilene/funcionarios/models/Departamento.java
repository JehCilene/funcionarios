package com.br.cilene.funcionarios.models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "departamento")
public class Departamento {

	@GeneratedValue
	@Id
	@Column(name = "departamento_id")
	private int id;
	
	@Column(name = "funcionario_name")
	@NotEmpty
	@Length(max = 50)
	private String nome;	
	
    @ManyToMany(mappedBy = "departamentos", fetch = FetchType.LAZY, 
    		cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    private Set<Funcionario> funcionarios = new HashSet<>();

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

	public Set<Funcionario> getStudents() {
		return funcionarios;
	}

	public void setStudents(Set<Funcionario> students) {
		this.funcionarios = students;
	}
    
    
}
