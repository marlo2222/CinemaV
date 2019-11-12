package com.TestCinema.services;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.TestCinema.model.Filme;
import com.TestCinema.model.Sessao;
import com.TestCinema.model.Ticket;
import com.TestCinema.repository.FilmeRepository;
import com.TestCinema.repository.SessaoRepository;
import com.TestCinema.repository.TicketRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.ExampleMatcher.NoOpPropertyValueTransformer;
import org.springframework.stereotype.Service;

@Service
public class FilmeService {

	private long TICKET_TIPO_INTEIRO = 0;
	private long TICKET_TIPO_MEIO = 1;
	
	private long SALA_IMAX = 1;
	
	@Autowired
	FilmeRepository filmeRepository;
	
	@Autowired
	SessaoRepository sessaoRepository;

	@Autowired
	TicketRepository ticketRepository;
	
	public List<Filme> filmes() {
		return filmeRepository.findAll();
	}

	public Filme filme(long filmeId) {
		return filmeRepository.findById(filmeId).get();
	}
	
	public Sessao sessao(long sessaoId) {
		return sessaoRepository.findById(sessaoId).get();
	}

	public List<Sessao> sessoes(long filmeId) {
		Filme filme = filmeRepository.findById(filmeId).get();
		
		return filme.getSessoes();
	}

	public double precoTicket(long sessaoId){
		Sessao sessao = sessaoRepository.getOne(sessaoId);
		double preco;
		
		if(sessao.getTipoSala() == SALA_IMAX){
			preco = 100;
		} else {
			preco = 50;
		}
		return preco;
	}

	public Map<Object, Object> poltronasLivres(long sessaoId) {
		Sessao sessao = sessaoRepository.getOne(sessaoId);
		
		List<Ticket> vendidos = ticketRepository.findBySessao(sessao);
		
		Map<Object, Object> poltronasLivres = new HashMap<>();

		for(Integer i=1; i <= sessao.getCapacidade(); i++){
			poltronasLivres.put(i, true);
		}
		
		for (Ticket ticket : vendidos) {
			poltronasLivres.put(ticket.getPoltrona(), false);
		}
		return poltronasLivres;
	}



	public Filme salvarFilme(String nomeFilme, String dataEstreia, String duracao) {
		
		if (!validarEntradasFilme(nomeFilme, dataEstreia, duracao)) {
			System.out.println("entradas filme invalida");
			return null;
		}
		
		LocalDate data = LocalDate.parse(dataEstreia);
		
		LocalTime duracaoFilme = LocalTime.parse(duracao);
		
		LocalDate criacao = LocalDate.now();
		Filme filme = new Filme(nomeFilme, data, duracaoFilme);
		
		Filme filmeAux = filmeRepository.findByNomeFilme(nomeFilme);
		
		if (filmeAux != null) {
			System.out.println("já existe esse filme amigo");
			return null;
		}
		
		if(!(filme.getDataEstreia().plusDays(-7).isBefore(criacao))) {
			System.out.println("passou de 7 dias");
			return null;
		}
		
		if(!filme.getDataEstreia().getDayOfWeek().equals(DayOfWeek.THURSDAY)) {
			System.out.println("precisa estreiar em uma quinta");
			return null;
		}
		
		
		filmeRepository.save(filme);


		return filme;
	}
	
	public Sessao salvarSessao(long idFilme, String horarioExibicao, long tipoSala, String dataExibicao) {
		if (!validarEntradasSessao(idFilme, horarioExibicao, tipoSala, dataExibicao)) {
			System.out.println("entradas sessao invalida");
			return null;
		}
		Filme filme = filmeRepository.getOne(idFilme);
		
		LocalTime exibicao = LocalTime.parse(horarioExibicao);
		LocalDate data = LocalDate.parse(dataExibicao);
		LocalDate criacao = LocalDate.now();
	
		Sessao sessao = new Sessao(exibicao, tipoSala, data);
		
		sessao.setFilme(filme);
		
		if (!(exibicao.isBefore(LocalTime.of(22, 01)) && exibicao.isAfter(LocalTime.of(11, 59)))){
			System.out.println("nao pode ser depois das 22 antes da 12");
			return null;
		}
		if (data.isBefore(criacao)) {
			System.out.println("nao pode ser data no passado amigo");
			return null;
		}
		if(sessaoJaExistente(filme, sessao)) {
			System.out.println("sessao ja existentes");
			return null;
		}
		
		sessaoRepository.save(sessao);
		
		return sessao;
	}
	
	public boolean poltronaLivre(long sessaoId, long poltrona) {
		Sessao sessao = sessaoRepository.getOne(sessaoId);
		Ticket ticket = ticketRepository.findBySessaoAndPoltrona(sessao, poltrona);
		if (ticket != null){
			return false;
		}
		return true;
	}

	public 	Ticket venderTicket(long filmeId, long sessaoId, long poltrona, long tipoTicket) {
		if(!validarEntradasTicket(filmeId, sessaoId, poltrona, tipoTicket)) {
			System.out.println("entradas ticket invalida");
			return null;
		}
		
		if(!poltronaLivre(sessaoId, poltrona)) {
			System.out.println("poltrona ocupada");
			return null;
		}

		// // poltronas proximas vazias
		// if() {
			
		// }

		Ticket novoTicket = new Ticket(sessao(sessaoId), poltrona);
		ticketRepository.save(novoTicket);
		return null;
	}

	private boolean validarEntradasFilme(String nomeFilme, String dataEstreia, String duracao) {
		if(nomeFilme == null || dataEstreia == null || duracao == null)
			return false;
		if (nomeFilme.isEmpty() || dataEstreia.isEmpty() || duracao.isEmpty())
			return false;
	
		return true;
	
	}
	
	private boolean validarEntradasSessao(long idFilme, String horarioExibicao, long tipoSala, String data) {

		if(idFilme <= 0 || horarioExibicao == null || tipoSala <= 0 || data == null)
			return false;
		if (horarioExibicao.isEmpty() || data.isEmpty())
			return false;
		if (!filmeRepository.existsById(idFilme))
			return false;
	
		return true;
	
	}

	private boolean validarEntradasTicket(long filmeId, long sessaoId, long poltrona, long tipoTicket) {
		if(filmeId == 0 || poltrona == 0 || sessaoId == 0 || tipoTicket == 0) {
			return false;
		}
		return true;
	}

	public boolean sessaoJaExistente(Filme filme, Sessao novaSessao) {
		List<Sessao> sessoes = sessaoRepository.findByDataAndTipoSala(novaSessao.getData(), novaSessao.getTipoSala());
		if(sessoes == null) {
			System.out.println("NULLLLLLLLL");
		}else {
			System.out.println("OKKKKKKKKKK");			
		}

		LocalTime termino = novaSessao.getHorarioExibicao().plusSeconds(novaSessao.getFilme().getDuracao().toSecondOfDay());
	
		for (Sessao s : sessoes) {	
			LocalTime terminoS = s.getHorarioExibicao().plusSeconds(s.getFilme().getDuracao().toSecondOfDay());
			
			if(s.getHorarioExibicao().equals(novaSessao.getHorarioExibicao()) || s.getHorarioExibicao().equals(termino)) {
				System.out.println("examente iguais ou inicio ou termino");
				return true;
			}
			
			if(s.getHorarioExibicao().isBefore(termino) && s.getHorarioExibicao().isBefore(novaSessao.getHorarioExibicao())) {
				System.out.println("esta entre os intervalos 1");
				return true;
			}
			
			if(termino.isBefore(terminoS) && termino.isAfter(novaSessao.getHorarioExibicao())) {
				System.out.println("esta entre os intervalos 2");
				return true;
			}
		}
		
		
		return false;
	}

	
}
