package com.TestCinema.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.TestCinema.model.Filme;

@Repository
public interface FilmeRepository extends JpaRepository<Filme, Long>{
	
	List<Filme> findByDataEstreia(LocalDate data);
	
	Filme findByNomeFilme(String nomeFilme);

}
