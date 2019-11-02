package com.TestCinema.services;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.TestCinema.model.Filme;
import com.TestCinema.model.Sessao;
import com.TestCinema.repository.FilmeRepository;
import com.TestCinema.repository.SessaoRepository;

@Service
public class FilmeService {
	
	@Autowired
	FilmeRepository filmeRepository;
	
	@Autowired
	SessaoRepository sessaoRepository;
	
	

	public String salvarFilme(String nomeFilme, String dataEstreia, int sala, String horarioExibicao, String duracao) {
		
		if (!validarEntradas(nomeFilme, dataEstreia, sala, horarioExibicao, duracao)) {
			return "não cadastrado: A6";
		}
		
		LocalDate data = LocalDate.parse(dataEstreia);
		LocalTime exibicao = LocalTime.parse(horarioExibicao);
		LocalTime duracaoFilme = LocalTime.parse(duracao);
		
		LocalDate criacao = LocalDate.now();
		Filme filme = new Filme(nomeFilme, data, duracaoFilme);
		
		Sessao sessao = new Sessao(exibicao, sala);
		
		sessao.setFilme(filme);
		
		if(!(filme.getDataEstreia().plusDays(-7).isBefore(criacao))) {
			return "não cadastrado: A1";
		}
		
		if(!filme.getDataEstreia().getDayOfWeek().equals(DayOfWeek.THURSDAY)) {
			return "não cadastrado: A2";
		}
		if (!(exibicao.isBefore(LocalTime.of(22, 01)) && exibicao.isAfter(LocalTime.of(11, 59)))){
			return "não cadastrado: A3";
		}
		if(sessaoJaExistente(filme, sessao)) {
			return "não cadastrado: A5";
		}
		
//		filmeRepository.save(filme);
//		sessaoRepository.save(sessao);

		return "";
	}
	
	

	private boolean validarEntradas(String nomeFilme, String dataEstreia, int sala, String horarioExibicao, String duracao) {
		if (nomeFilme.isEmpty() || dataEstreia.isEmpty() || horarioExibicao.isEmpty() || duracao.isEmpty())
			return false;
		if(sala == 0)
			return false;
		return true;
	}

	public boolean sessaoJaExistente(Filme filme, Sessao sessao){
		
		List<Filme> filmes = filmeRepository.findByDataEstreia(filme.getDataEstreia());
		
		for (Filme f : filmes){
			List<Sessao> sessoes = sessaoRepository.findByTipoSalaAndFilme(sessao.getTipoSala(),f);
			for (Sessao s : sessoes) {
			
				LocalTime termino = sessao.getHorarioExibicao().plusSeconds(f.getDuracao().toSecondOfDay());
				if(s.getHorarioExibicao().equals(sessao.getHorarioExibicao()) || s.getHorarioExibicao().equals(termino)) {
					return true;
				}
				if(s.getHorarioExibicao().isAfter(sessao.getHorarioExibicao()) && s.getHorarioExibicao().isBefore(termino)) {
					return true;
				}
			}
		}
		
		return false;
	}
}
