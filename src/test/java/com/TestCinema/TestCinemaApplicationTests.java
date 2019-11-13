package com.TestCinema;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThat;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.TestCinema.model.Filme;
import com.TestCinema.model.Sessao;
import com.TestCinema.model.Ticket;
import com.TestCinema.repository.FilmeRepository;
import com.TestCinema.repository.SessaoRepository;
import com.TestCinema.repository.TicketRepository;
import com.TestCinema.services.FilmeService;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
class TestCinemaApplicationTests {

	@Mock
	private FilmeRepository fr;

	@Mock
	private SessaoRepository sr;
	
	@Mock
	private TicketRepository tr;

	@Test
	void contextLoads() {
	}

	@Test
	public void salvarFilmeTest() {
		String nomeFilme = "Filme1", dataEstreia = "2019-11-14", duracao = "02:00";
		LocalDate data = LocalDate.parse(dataEstreia);
		LocalTime duracaoFilme = LocalTime.parse(duracao);
		Filme filme = new Filme(nomeFilme, data, duracaoFilme);

		fr.save(filme);
		verify(fr).save(filme);
	}

	@Test
	public void salvarSessaoTest() {
		long idFilme = 1, tipoSala = 1;
		String dataExibicao = "2019-11-10", horarioExibicao = "13:30";
		Filme filme = new Filme("Filme1", LocalDate.of(2019, 11, 7), LocalTime.of(2, 0));

		filme.setId(idFilme);

		LocalTime exibicao = LocalTime.parse(horarioExibicao);
		LocalDate data = LocalDate.parse(dataExibicao);
		Sessao sessao = new Sessao(exibicao, tipoSala, data);

		sessao.setFilme(filme);
		sr.save(sessao);
		verify(sr).save(sessao);
	}
	
	@Test
	public void salvarTicketTest() {
		Sessao s = new Sessao(LocalTime.of(13, 30), 1, LocalDate.of(2019, 11, 17));
		Ticket ticket = new Ticket(s, 19);
		
		tr.save(ticket);
		verify(tr).save(ticket);
	}

	@Test
	public void cadastrarFilme() {
		FilmeService fs = Mockito.mock(FilmeService.class);
		when(fs.salvarFilme("Filme1", "2019-11-14", "02:00")).thenReturn(new Filme("Filme1", LocalDate.of(2019, 11, 14), LocalTime.of(2, 0)));
		Filme ret = fs.salvarFilme("Filme1", "2019-11-14", "02:00");
		
		verify(fs).salvarFilme("Filme1", "2019-11-14", "02:00");
		assertNotEquals(null, ret);
		assertThat(ret.getNomeFilme(), is("Filme1"));
		assertThat(ret.getDataEstreia(), is(LocalDate.of(2019, 11, 14)));
		assertThat(ret.getDuracao(), is(LocalTime.of(2, 0)));
	}
	
	@Test
	public void cadastrarFilmeVazio() {
		FilmeService fs = Mockito.mock(FilmeService.class);
		when(fs.salvarFilme(null, null, null)).thenReturn(null);
		Filme ret = fs.salvarFilme(null, null, null);

		verify(fs).salvarFilme(null, null, null);
		assertThat(ret, is(nullValue()));
	}
	
	@Test
	public void cadastrarFilmeSemNome() {
		FilmeService fs = Mockito.mock(FilmeService.class);
		when(fs.salvarFilme(null, "2019-11-14", "02:00")).thenReturn(new Filme(null, LocalDate.of(2019, 11, 14), LocalTime.of(2, 0)));
		Filme ret = fs.salvarFilme(null, "2019-11-07", "02:00");
		
		verify(fs).salvarFilme(null, "2019-11-07", "02:00");
		assertThat(ret, is(nullValue()));
	}
	
	@Test
	public void cadastrarFilmeSemDataEstreia() {
		FilmeService fs = Mockito.mock(FilmeService.class);
		when(fs.salvarFilme("Filme1", null, "02:00")).thenReturn(null);
		Filme ret = fs.salvarFilme("Filme1", null, "02:00");

		verify(fs).salvarFilme("Filme1", null, "02:00");
		assertThat(ret, is(nullValue()));
	}

	@Test
	public void cadastrarFilmeDuracao() {
		FilmeService fs = Mockito.mock(FilmeService.class);
		when(fs.salvarFilme("Filme1", "2019-11-14", null)).thenReturn(new Filme("Filme1", LocalDate.of(2019, 11, 14), null));
		Filme ret = fs.salvarFilme("Filme1", "2019-11-07", null);

		verify(fs).salvarFilme("Filme1", "2019-11-07", null);
		assertThat(ret, is(nullValue()));
	}

	@Test
	public void cadastrarSessao() {
		FilmeService fs = Mockito.mock(FilmeService.class);
		when(fs.salvarSessao(1, "13:30", 1, "2019-11-11")).thenReturn(new Sessao(LocalTime.of(13, 30), 1, LocalDate.of(2019, 11, 11)));
		Sessao ret = fs.salvarSessao(1, "13:30", 1, "2019-11-11");
		
		verify(fs).salvarSessao(1, "13:30", 1, "2019-11-11");
		assertNotEquals(nullValue(), ret);
		assertThat(ret.getHorarioExibicao(), is(LocalTime.of(13, 30)));
		assertThat(ret.getTipoSala(), is((long)1));
		assertThat(ret.getData(), is(LocalDate.of(2019, 11, 11)));
	}
	
	@Test
	public void cadastrarSessaoVazia() {
		FilmeService fs = Mockito.mock(FilmeService.class);
		when(fs.salvarSessao(0, null, 0, null)).thenReturn(null);
		Sessao ret = fs.salvarSessao(0, null, 0, null);
		
		verify(fs).salvarSessao(0, null, 0, null);
		assertThat(ret, is(nullValue()));
	}

	@Test
	public void cadastrarSessaoSemHorarioExibicao() {
		FilmeService fs = Mockito.mock(FilmeService.class);
		when(fs.salvarSessao(1, null, 1, "2019-11-11")).thenReturn(null);
		Sessao ret = fs.salvarSessao(1, null, 1, "2019-11-11");
		
		verify(fs).salvarSessao(1, null, 1, "2019-11-11");
		assertThat(ret, is(nullValue()));
	}
	
	@Test
	public void cadastrarSessaoSemTipoSala() {
		FilmeService fs = Mockito.mock(FilmeService.class);
		when(fs.salvarSessao(1, "13:30", 0, "2019-11-11")).thenReturn(null);
		Sessao ret = fs.salvarSessao(1, "13:30", 0, "2019-11-11");
		
		verify(fs).salvarSessao(1, "13:30", 0, "2019-11-11");
		assertThat(ret, is(nullValue()));
	}
	
	@Test
	public void cadastrarSessaoSemDataExibicao() {
		FilmeService fs = Mockito.mock(FilmeService.class);
		when(fs.salvarSessao(1, "13:30", 1, null)).thenReturn(null);
		Sessao ret = fs.salvarSessao(1, "13:30", 1, null);
		
		verify(fs).salvarSessao(1, "13:30", 1, null);
		assertThat(ret, is(nullValue()));
	}

	@Test
	public void cadastrarFilme7DiasAposATual() {
		FilmeService fs = Mockito.mock(FilmeService.class);
		when(fs.salvarFilme("Filme1", "2019-11-17", "02:00")).thenReturn(new Filme("Filme1", LocalDate.of(2019, 11, 17), LocalTime.of(2, 0)));
		Filme ret = fs.salvarFilme("Filme1", "2019-11-17", "02:00");

		verify(fs).salvarFilme("Filme1", "2019-11-17", "02:00");
		assertNotEquals(null, ret);
	}

	@Test
	public void cadastrarFilmeDataEstreiaDiferenteQuinta() {
		FilmeService fs = Mockito.mock(FilmeService.class);
		when(fs.salvarFilme("Filme1", "2019-11-10", "02:00")).thenReturn(null);
		Filme ret = fs.salvarFilme("Filme1", "2019-11-10", "02:00");

		verify(fs).salvarFilme("Filme1", "2019-11-10", "02:00");
		assertThat(ret, is(nullValue()));
	}

	@Test
	public void cadastrarSessaoComHorarioDiferenteDoPertimido() {
		FilmeService fs = Mockito.mock(FilmeService.class);
		when(fs.salvarSessao(1, "10:00", 1, "2019-11-10")).thenReturn(null);
		Sessao s = fs.salvarSessao(1, "10:00", 1, "2019-11-14");

		verify(fs).salvarSessao(1, "10:00", 1, "2019-11-14");
		assertThat(s, is(nullValue()));
	}
	
	@Test
	public void precoTicketTest() {
		FilmeService fs = Mockito.mock(FilmeService.class);
		when(fs.precoTicket(1)).thenReturn((double)100);
		when(fs.precoTicket(2)).thenReturn((double)50);
		double precoImax = fs.precoTicket(1);
		double precoN = fs.precoTicket(2);
		
		verify(fs).precoTicket(1);
		verify(fs).precoTicket(2);
		assertThat(precoImax, is(100.0));
		assertThat(precoN, is(50.0));
	}
	
	@Test
	public void poltronaLivreTest() {
		FilmeService fs = Mockito.mock(FilmeService.class);
		when(fs.poltronaLivre(1, 19)).thenReturn(true);
		boolean free = fs.poltronaLivre(1, 19);
		when(fs.poltronaLivre(1, 19)).thenReturn(false);
		boolean notFree = fs.poltronaLivre(1, 19);
		
		verify(fs, times(2)).poltronaLivre(1, 19);
		assertThat(free, is(true));
		assertThat(notFree, is(false));		
	}
	
	@Test
	public void venderTicketTest() {
		FilmeService fs = Mockito.mock(FilmeService.class);
		Sessao s = new Sessao(LocalTime.of(13, 30), 1, LocalDate.of(2019, 11, 17));
		s.setId(1);
		when(fs.venderTicket(1, 1, 19, 1)).thenReturn(new Ticket(s, 19));
		Ticket ticket = fs.venderTicket(1, 1, 19, 1);
		
		verify(fs).venderTicket(1, 1, 19, 1);
		assertThat(ticket.getPoltrona(), is((long)19));
		assertThat(ticket.getSessao().getId(), is(s.getId()));
	}
	
	@Test
	public void venderTicketSemFilme(){
		FilmeService fs = Mockito.mock(FilmeService.class);
		Sessao s = new Sessao(LocalTime.of(13, 30), 1, LocalDate.of(2019, 11, 17));
		s.setId(1);
		when(fs.venderTicket(-1, 1, 19, 1)).thenReturn(null);
		Ticket ticket = fs.venderTicket(-1, 1, 19, 1);
		
		verify(fs).venderTicket(-1, 1, 19, 1);
		assertThat(ticket, is(nullValue()));
	}
	
	@Test
	public void venderTicketSemSessao() {
		FilmeService fs = Mockito.mock(FilmeService.class);
		Sessao s = new Sessao(LocalTime.of(13, 30), 1, LocalDate.of(2019, 11, 17));
		s.setId(1);
		when(fs.venderTicket(1, -1, 19, 1)).thenReturn(null);
		Ticket ticket = fs.venderTicket(1, -1, 19, 1);
		
		verify(fs).venderTicket(1, -1, 19, 1);
		assertThat(ticket, is(nullValue()));
	}
	
	@Test
	public void venderTicketSemPoltrona() {
		FilmeService fs = Mockito.mock(FilmeService.class);
		Sessao s = new Sessao(LocalTime.of(13, 30), 1, LocalDate.of(2019, 11, 17));
		s.setId(1);
		when(fs.venderTicket(1, 1, -1, 1)).thenReturn(null);
		Ticket ticket = fs.venderTicket(1, 1, -1, 1);
		
		verify(fs).venderTicket(1, 1, -1, 1);
		assertThat(ticket, is(nullValue()));
	}
	
	@Test
	public void venderTicketSemTipoTicket() {
		FilmeService fs = Mockito.mock(FilmeService.class);
		Sessao s = new Sessao(LocalTime.of(13, 30), 1, LocalDate.of(2019, 11, 17));
		s.setId(1);
		when(fs.venderTicket(1, 1, 19, -1)).thenReturn(null);
		Ticket ticket = fs.venderTicket(1, 1, 19, -1);
		
		verify(fs).venderTicket(1, 1, 19, -1);
		assertThat(ticket, is(nullValue()));
	}
}