package com.br.cilene.funcionarios.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.br.cilene.funcionarios.models.Cargo;

@Repository
public interface CargoRepository  extends JpaRepository<Cargo, Integer>{

}
