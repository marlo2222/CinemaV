package com.TestCinema.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.TestCinema.model.Filme;
import com.TestCinema.model.Sessao;

@Repository
public interface SessaoRepository extends JpaRepository<Sessao, Long> {
	

	List<Sessao> findByTipoSalaAndFilme(long tipoSala, Filme filme);

}
