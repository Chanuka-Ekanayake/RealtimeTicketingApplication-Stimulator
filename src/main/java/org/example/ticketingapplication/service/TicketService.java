package org.example.ticketingapplication.service;

import org.example.ticketingapplication.model.Event;
import org.example.ticketingapplication.model.Ticket;
import org.example.ticketingapplication.repository.EventRepository;
import org.example.ticketingapplication.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 - Manages ticket pool and sales.

 */

@Service
public class TicketService {

    private final TicketRepository ticketRepository;
    private final EventRepository eventRepository;

    @Autowired
    public TicketService(TicketRepository ticketRepository, EventRepository eventRepository) {
        this.ticketRepository = ticketRepository;
        this.eventRepository = eventRepository;
    }

    public Ticket saveTicket(Ticket ticket) {
        ticket.getEvent().removeTicket(ticket);
        ticket.getEvent().addTicket(ticket);
        return ticketRepository.save(ticket);

    }

    public void addTicket(Ticket ticket) {
        ticketRepository.save(ticket);
        ticket.getEvent().addTicket(ticket);
    }

    public Ticket getTicketById(String id) {
        return ticketRepository.findById(id).orElseThrow(() -> new RuntimeException("Ticket with id " + id + " not found"));
    }

    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }

    public void deleteTicketById(String id) {
        ticketRepository.deleteById(id);
        getTicketById(id).getEvent().removeTicket(getTicketById(id));
    }

    public List<Ticket> getExpiredTickets() {
        return ticketRepository.findByExpireDateTimeBefore(LocalDateTime.now());
    }

    public void deleteExpiredTickets(List<Ticket> expiredTickets) {
        for(Ticket ticket : expiredTickets) {
            ticket.getEvent().removeTicket(ticket);
        }

        ticketRepository.deleteAll(expiredTickets);
    }

    public void deleteAllTickets() {
        ticketRepository.deleteAll();
        for (Ticket ticket : ticketRepository.findAll()) {
            ticket.getEvent().removeTicket(ticket);
        }
    }

    public Ticket createTicket(Event event, BigDecimal price, LocalDateTime expireDateTime) {
        Ticket ticket = new Ticket(event, price, expireDateTime);
        ticket.createTicketID(ticketRepository.findAll());
        event.addTicket(ticket);
        return ticketRepository.save(ticket);
    }
}
