package com.TestCinema.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

import com.TestCinema.model.Sessao;
import com.TestCinema.model.Ticket;;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findBySessao(Sessao sessao);
    Ticket findBySessaoAndPoltrona(Sessao sessao, long poltrona);
}
