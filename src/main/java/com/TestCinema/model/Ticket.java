package com.TestCinema.model;

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
@Table(name = "ticket")
public class Ticket {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_sessao")
	private Sessao sessao;
	
	@Column(name = "poltrona")
	private long poltrona;


	public Ticket(Sessao sessao, long poltrona) {
		this.sessao = sessao;
		this.poltrona = poltrona;
	}


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Sessao getSessao() {
		return sessao;
	}

	public void setSessao(Sessao sessao) {
		this.sessao = sessao;
	}

	public long getPoltrona() {
		return poltrona;
	}

	public void setPoltrona(long poltrona) {
		this.poltrona = poltrona;
	}

	
}
