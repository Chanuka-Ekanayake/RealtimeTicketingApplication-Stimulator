package org.example.ticketingapplication.repository;

import org.example.ticketingapplication.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

/**
 - Interface for Ticket entity

 */



public interface TicketRepository extends JpaRepository<Ticket, Integer> {

    List<Ticket> findByExpireDateTimeBefore(LocalDateTime now);
}
