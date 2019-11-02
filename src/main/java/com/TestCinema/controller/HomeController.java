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
		
		
		String rep = filmeService.salvarFilme(nomeFilme,dataEstreia,sala,horarioExibicao,duracao);
		System.out.println(rep);
		String err = "danger";
		
		if (!rep.isEmpty()){
			mv.addObject("err", err);
		} else {
			rep = "Cadastrado com sucesso!";
		}
		
		
		mv.addObject("menssagem", rep);	
		mv.setViewName("home/filme");
		return mv;
		
		
	}
	
	@GetMapping("/venda/ingresso")
	public ModelAndView venderTicked() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("home/venda");
		return mv;
	}
	
	
}
