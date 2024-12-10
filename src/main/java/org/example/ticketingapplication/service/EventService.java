package org.example.ticketingapplication.service;

import org.example.ticketingapplication.model.Event;
import org.example.ticketingapplication.model.Ticket;
import org.example.ticketingapplication.model.Vendor;
import org.example.ticketingapplication.repository.EventRepository;
import org.example.ticketingapplication.repository.TicketRepository;
import org.example.ticketingapplication.repository.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EventService {

    private final EventRepository eventRepository;
    private final TicketRepository ticketRepository;
    private final VendorRepository vendorRepository;

    @Autowired
    public EventService(EventRepository eventRepository, TicketService ticketService, TicketRepository ticketRepository, VendorRepository vendorRepository) {
        this.eventRepository = eventRepository;
        this.ticketRepository = ticketRepository;
        this.vendorRepository = vendorRepository;
    }

    public Event saveEvent(Event event) {
        event.getVendor().removeEvent(event);
        event.getVendor().addEvent(event);
        return eventRepository.save(event);
    }

    public void addEvent(Event event) {
        eventRepository.save(event);
        event.getVendor().addEvent(event);
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public Event getEventById(String id) {
        return eventRepository.findById(id).orElseThrow(() -> new RuntimeException("Event with id " + id + " not found"));
    }

    public void deleteEvent(String id) {
        eventRepository.deleteById(id);
        getEventById(id).getVendor().removeEvent(getEventById(id));

        for(Ticket ticket : getEventById(id).getTickets()) {
            ticketRepository.deleteById(ticket.getTicketId()); //Delete tickets related to the event
        }
    }

    public void deleteAllEvents() {
        for(Event event : getAllEvents()) {
            event.getVendor().removeEvent(event);
        }
        ticketRepository.deleteAll();
        eventRepository.deleteAll();

    }

    public List<Event> getExpiredEvents() {
        return eventRepository.findByEventDateTimeBefore(LocalDateTime.now());
    }

    public void deleteExpiredEvents() {
        eventRepository.deleteAll(getExpiredEvents());
        for (Event event : getExpiredEvents()) {
            event.getVendor().removeEvent(event);
            for(Ticket ticket : event.getTickets()) {
                ticketRepository.deleteById(ticket.getTicketId()); //Delete tickets related to the event
            }
        }
    }

    public Event createEvent(String eventName, LocalDateTime eventDateTime, String eventVenue, String eventCategory, int maxTickets, Vendor vendor) {
        Event event = new Event(eventName, eventDateTime, eventVenue, eventCategory, maxTickets, vendor);
        event.createEventID(eventRepository.findAll());
        vendor.addEvent(event);
        vendorRepository.save(vendor);
        return eventRepository.save(event);
    }

    public void clearEventData(){
        deleteExpiredEvents();
        for(Event event : getAllEvents()) {
            if(event.getVendor() == null){
                eventRepository.delete(event);
                for(Ticket ticket : event.getTickets()) {
                    ticketRepository.deleteById(ticket.getTicketId()); //Delete tickets related to the event
                }
            }
        }
    }
}
