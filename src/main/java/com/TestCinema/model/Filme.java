package com.TestCinema.model;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "filme")
public class Filme {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private long id;
	
	@Column(name="nomeFilme", unique = true)
	private String nomeFilme;
	
	@Column(name = "data_estreia")
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private LocalDate dataEstreia;
	
	
	@Column(name = "duracao")
	private LocalTime duracao;

	
	@OneToMany(mappedBy = "filme", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Sessao> sessoes;
	
	public Filme(){
		super();
	}

	public Filme(String nomeFilme, LocalDate data, LocalTime duracaoFilme) {
		this.nomeFilme = nomeFilme;
		this.dataEstreia = data;
		this.duracao = duracaoFilme;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNomeFilme() {
		return nomeFilme;
	}

	public void setNomeFilme(String nomeFilme) {
		this.nomeFilme = nomeFilme;
	}

	public LocalDate getDataEstreia() {
		return dataEstreia;
	}

	public void setDataEstreia(LocalDate dataEstreia) {
		this.dataEstreia = dataEstreia;
	}

	public LocalTime getDuracao() {
		return duracao;
	}

	public void setDuracao(LocalTime duracao) {
		this.duracao = duracao;
	}

	public List<Sessao> getSessoes() {
		return sessoes;
	}

	public void setSessoes(List<Sessao> sessoes) {
		this.sessoes = sessoes;
	}



}