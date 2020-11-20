package com.br.cilene.funcionarios.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.br.cilene.funcionarios.models.Departamento;

@Repository
public interface DepartamentoRepository  extends JpaRepository<Departamento, Integer>{

}
