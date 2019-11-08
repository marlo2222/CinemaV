package com.TestCinema.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.validation.Valid;

import com.TestCinema.model.Filme;
import com.TestCinema.model.Sessao;
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
			}else {
				
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
	public ModelAndView cadastraNovaSecao(@RequestParam("filme") long id,@RequestParam("horarioExibicao") String horarioExibicao, @RequestParam("tipoSala") long tipoSala,@RequestParam("data") String data){
		ModelAndView mv = new ModelAndView();
		Sessao sessao = filmeService.salvarSessao(id, horarioExibicao, tipoSala, data);
		String err = "danger";
		if(sessao == null) {
			mv.addObject("err", err);
			mv.addObject("menssagem", "sessao não cadastrado");
		}else {
			
			mv.addObject("menssagem", "sessao cadastrado com sucesso");
		}
		mv.setViewName("home/secao");
		return mv;
		
	}
	
	@GetMapping("/venda/ingresso")
	public ModelAndView venderTicked() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("home/venda");
		return mv;
	}
	
	
}
