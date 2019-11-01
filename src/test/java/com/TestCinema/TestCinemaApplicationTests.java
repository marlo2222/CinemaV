package com.TestCinema;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.TestCinema.model.Filme;
import com.TestCinema.services.FilmeService;

@SpringBootTest
class TestCinemaApplicationTests {

	@Autowired
	FilmeService fs;
	
	@Test
	void contextLoads() {
	}

	@Test
	public void cadastrarFilme() {
		String ret = fs.salvarFilme("Filme1", "2019-11-07", 1, "13:30", "02:00");
		
		assertEquals(":)", ret);
	}
	
	@Test
	public void cadastrarFilmeVazio() {
		String ret = fs.salvarFilme("", "", 0, "", "");
	
		assertEquals("não cadastrado: A6", ret);
	}
	
	@Test
	public void cadastrarFilmeSemNome() {
		String ret = fs.salvarFilme("", "2019-11-07", 1, "13:30", "02:00");
		
		assertEquals("não cadastrado: A6", ret);
	}
	
	@Test
	public void cadastrarFilmeSemDataEstreia() {
		String ret = fs.salvarFilme("Filme1", "", 1, "13:30", "02:00");
		
		assertEquals("não cadastrado: A6", ret);
	}
	
	@Test
	public void cadastrarFilmeSala() {
		String ret = fs.salvarFilme("", "2019-11-07", 0, "13:30", "02:00");
		
		assertEquals("não cadastrado: A6", ret);
	}
	
	@Test
	public void cadastrarFilmeHorarioExibicao() {
		String ret = fs.salvarFilme("", "2019-11-07", 1, "", "02:00");
		
		assertEquals("não cadastrado: A6", ret);
	}
	
	@Test
	public void cadastrarFilmeDuracao() {
		String ret = fs.salvarFilme("", "2019-11-07", 1, "13:30", "");
		
		assertEquals("não cadastrado: A6", ret);
	}
	
	@Test
	public void cadastrarFilme7DiasAposATual() {
		String ret = fs.salvarFilme("Filme1", "2019-11-09", 1, "13:30", "02:00");
		
		assertEquals("não cadastrado: A1", ret);
	}
	
	@Test
	public void cadastrarFilmeDataEstreiaDiferenteQuinta() {
		String ret = fs.salvarFilme("Filme1", "2019-11-06", 1, "13:30", "02:00");
		
		assertEquals("não cadastrado: A2", ret);
	}
	
	@Test
	public void cadastrarSessaoComHorarioDiferenteDoPertimido() {
		String ret = fs.salvarFilme("Filme1", "2019-11-07", 1, "11:30", "02:00");
		
		assertEquals("não cadastrado: A3", ret);
	}
	
//	@Test
//	public void cadastrarSessaoComHorarioMenorQueDuaracao() {
//		String ret = fs.salvarFilme("Filme1", "2019-11-07", 1, "13:30", "02:00");
//		
//		assertEquals("não cadastrado: A3", ret);
//	}
}
