package com.TestCinema.model;

import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Sessao")
public class Sessao {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "horarioExibicao")
	private LocalTime horarioExibicao;
	
	@Column(name = "encerrado")
	private boolean encerrado;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_filme")
	private Filme filme;
	
	@Column(name = "capacidade")
	private long capacidade;
	
	@Column(name = "tipoSala")
	private long tipoSala;
	
	public Sessao() {
		
	}
	
	public Sessao(LocalTime exibicao, int sala) {
		this.horarioExibicao = exibicao;
		this.tipoSala = sala;
		this.capacidade = this.tipoSala == 1 ? 100 : 50; 
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public LocalTime getHorarioExibicao() {
		return horarioExibicao;
	}

	public void setHorarioExibicao(LocalTime horarioExibicao) {
		this.horarioExibicao = horarioExibicao;
	}

	public boolean isEncerrado() {
		return encerrado;
	}

	public void setEncerrado(boolean encerrado) {
		this.encerrado = encerrado;
	}

	public Filme getFilme() {
		return filme;
	}

	public void setFilme(Filme filme) {
		this.filme = filme;
	}
	
	public long getCapacidade() {
		return capacidade;
	}

	public void setCapacidade(long capacidade) {
		this.capacidade = capacidade;
	}

	public long getTipoSala() {
		return tipoSala;
	}

	public void setTipoSala(long tipoSala) {
		this.tipoSala = tipoSala;
	}
}
