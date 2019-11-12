package com.TestCinema.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.TestCinema.model.Filme;
import com.TestCinema.model.Sessao;
import com.TestCinema.model.Ticket;
import com.TestCinema.services.FilmeService;

@Controller
public class HomeController {
	
	@Autowired
	FilmeService filmeService;

	@GetMapping("/")
	public ModelAndView home() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("home/index");
		return mv;
	}
	
	@GetMapping("/cadastro/filme")
	public ModelAndView cadastrarFilme(){
		ModelAndView mv = new ModelAndView();
		mv.addObject("filme", new Filme());
		
		mv.setViewName("home/filme");
		return mv;
	}
	
	@PostMapping("/cadastro/filme")
	public ModelAndView cadastrarFilme(@RequestParam("nomeFilme") String nomeFilme, @RequestParam("dataEstreia") String dataEstreia, 
		@RequestParam("tipoSala") int sala, @RequestParam("horarioExibicao") String horarioExibicao, @RequestParam("duracao") String duracao){
		ModelAndView mv = new ModelAndView();
		
		
		Filme filme = filmeService.salvarFilme(nomeFilme,dataEstreia,duracao);
		
		String err = "danger";
		
		if (filme == null){
			mv.addObject("err", err);
			mv.addObject("menssagem", "filme não cadastrado");
		} else {
			Sessao sessao = filmeService.salvarSessao(filme.getId(), horarioExibicao, sala, dataEstreia);
			if(sessao == null) {
				mv.addObject("err", err);
				mv.addObject("menssagem", "filme não cadastrado");
			} else {
				mv.addObject("menssagem", "filme cadastrado com sucesso");
			}
		}

		mv.setViewName("home/filme");
		return mv;
	}
	
	@GetMapping("/cadastra/novaSecao")
	public ModelAndView cadastraNovaSecao() {
		ModelAndView mv = new ModelAndView();
		mv.addObject("filmes", filmeService.filmes());
		mv.addObject("secao", new Sessao());
		mv.setViewName("home/secao");
		return mv;
	}
	
	@PostMapping("/cadastra/novaSecao")
	public ModelAndView cadastraNovaSecao(
		@RequestParam("filme") long id,
		@RequestParam("horarioExibicao") String horarioExibicao,
		@RequestParam("tipoSala") long tipoSala,
		@RequestParam("data") String data){

		ModelAndView mv = new ModelAndView();
		Sessao sessao = filmeService.salvarSessao(id, horarioExibicao, tipoSala, data);
		
		String err = "danger";
		if(sessao == null) {
			mv.addObject("err", err);
			mv.addObject("menssagem", "sessao não cadastrado");
		} else {
			mv.addObject("menssagem", "sessao cadastrado com sucesso");
		}
		mv.setViewName("home/secao");
		return mv;		
	}
	
	@GetMapping("/venda/ticket")
	public ModelAndView venderTicket() {
		ModelAndView mv = new ModelAndView();
		mv.addObject("filmes", filmeService.filmes());
		mv.setViewName("home/ticket-filme");
		return mv;	
	}
	
	@PostMapping("/venda/ticket/sessao")
	public ModelAndView venderTicket(
		@RequestParam("filme") long filmeId) {
		
		ModelAndView mv = new ModelAndView();

		mv.addObject("filme", filmeService.filme(filmeId));
		mv.addObject("sessoes", filmeService.sessoes(filmeId));
		mv.setViewName("home/ticket-sessao");
		return mv;
	}

	@PostMapping("/venda/ticket/poltrona")
	public ModelAndView venderTicket(
		@RequestParam("filme") long filmeId,
		@RequestParam("sessao") long sessaoId) {
	
		ModelAndView mv = new ModelAndView();

		mv.addObject("filme", filmeService.filme(filmeId));
		mv.addObject("sessao", filmeService.sessao(sessaoId));
		mv.addObject("preco", filmeService.precoTicket(sessaoId));
		mv.addObject("poltronas", filmeService.poltronasLivres(sessaoId));
		mv.setViewName("home/ticket-poltrona");
		return mv;
	}

	@PostMapping("/venda/ticket")
	public ModelAndView venderTicket(
		@RequestParam("filme") long filmeId, 
		@RequestParam("sessao") long sessaoId,
		@RequestParam("poltrona") long poltrona,
		@RequestParam("tipo") long tipoTicket) {

		ModelAndView mv = new ModelAndView();
		
		Ticket novoTicket = filmeService.venderTicket(filmeId, sessaoId, poltrona, tipoTicket);
		String err = "danger";
		
		if(novoTicket == null) {
			mv.addObject("err", err);
			mv.addObject("menssagem", "Erro ao efetuar venda.");
		} else {
			mv.addObject("menssagem", "Venda realizada com sucesso!");
		}
		mv.setViewName("home/ticket-filme");
		return mv;
	}
}
