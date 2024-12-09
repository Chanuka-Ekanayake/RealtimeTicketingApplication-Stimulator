package org.example.ticketingapplication.service;

import org.example.ticketingapplication.model.Event;
import org.example.ticketingapplication.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EventService {

    private final EventRepository eventRepository;

    @Autowired
    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public Event saveEvent(Event event) {
        return eventRepository.save(event);
    }

    public void addEvent(Event event) {
        eventRepository.save(event);
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public Event getEventById(String id) {
        return eventRepository.findById(id).orElseThrow(() -> new RuntimeException("Event with id " + id + " not found"));
    }

    public void deleteEvent(String id) {
        eventRepository.deleteById(id);
    }

    public void deleteAllEvents() {
        eventRepository.deleteAll();
    }

    public List<Event> getExpiredEvents() {
        return eventRepository.findByEventDateTimeBefore(LocalDateTime.now());
    }

    public void deleteExpiredEvents(List<Event> expiredEvents) {
        eventRepository.deleteAll(expiredEvents);
    }
}
