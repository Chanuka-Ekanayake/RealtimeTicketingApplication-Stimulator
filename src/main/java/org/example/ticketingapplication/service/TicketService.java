package org.example.ticketingapplication.service;

import org.example.ticketingapplication.model.Ticket;
import org.example.ticketingapplication.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 - Manages ticket pool and sales.

 */

@Service
public class TicketService {

    private final TicketRepository ticketRepository;

    @Autowired
    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public Ticket saveTicket(Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    public Ticket getTicketById(int id) {
        return ticketRepository.findById(id).orElseThrow(() -> new RuntimeException("Ticket with id " + id + " not found"));
    }

    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }

    public void deleteTicketById(int id) {
        ticketRepository.deleteById(id);
    }

    public List<Ticket> getExpiredTickets() {
        return ticketRepository.findByExpireDateTimeBefore(LocalDateTime.now());
    }

    public void deleteExpiredTickets(List<Ticket> expiredTickets) {
        ticketRepository.deleteAll(expiredTickets);
    }
}
