package org.example.ticketingapplication.controller;

/**
 - Expose APIs for CRUD operations and actions

 */


import org.example.ticketingapplication.model.Ticket;
import org.example.ticketingapplication.repository.TicketRepository;
import org.example.ticketingapplication.util.SystemLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

        import java.util.List;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private SystemLogger systemLogger;

    // Get all tickets
    @GetMapping
    public List<Ticket> getAllTickets() {
        systemLogger.logInfo("Fetching all tickets.");
        return ticketRepository.findAll();
    }

    // Get ticket by ID
    @GetMapping("/{id}")
    public ResponseEntity<Ticket> getTicketById(@PathVariable String id) {
        systemLogger.logInfo("Fetching ticket with ID: " + id);
        return ticketRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Create a new ticket
    @PostMapping
    public Ticket createTicket(@RequestBody Ticket ticket) {
        systemLogger.logInfo("Creating a new ticket.");
        return ticketRepository.save(ticket);
    }

    // Update a ticket
    @PutMapping("/{id}")
    public ResponseEntity<Ticket> updateTicket(@PathVariable String id, @RequestBody Ticket updatedTicket) {
        systemLogger.logInfo("Updating ticket with ID: " + id);
        return ticketRepository.findById(id)
                .map(ticket -> {
                    ticket.setPrice(updatedTicket.getPrice());
                    ticket.setCustomer(updatedTicket.getCustomer());
                    ticket.setEvent(updatedTicket.getEvent());
                    return ResponseEntity.ok(ticketRepository.save(ticket));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Delete a ticket
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTicket(@PathVariable String id) {
        systemLogger.logInfo("Deleting ticket with ID: " + id);
        if (ticketRepository.existsById(id)) {
            ticketRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}

