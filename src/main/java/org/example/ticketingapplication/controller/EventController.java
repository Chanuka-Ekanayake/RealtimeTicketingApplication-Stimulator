package org.example.ticketingapplication.controller;



import org.example.ticketingapplication.model.Event;
import org.example.ticketingapplication.repository.EventRepository;
import org.example.ticketingapplication.util.SystemLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

        import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventController {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private SystemLogger systemLogger;

    // Get all events
    @GetMapping
    public List<Event> getAllEvents() {
        systemLogger.logInfo("Fetching all events.");
        return eventRepository.findAll();
    }

    // Get event by ID
    @GetMapping("/{id}")
    public ResponseEntity<Event> getEventById(@PathVariable String id) {
        systemLogger.logInfo("Fetching event with ID: " + id);
        return eventRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Create a new event
    @PostMapping
    public Event createEvent(@RequestBody Event event) {
        systemLogger.logInfo("Creating a new event: " + event.getEventName());
        return eventRepository.save(event);
    }

    // Update an event
    @PutMapping("/{id}")
    public ResponseEntity<Event> updateEvent(@PathVariable String id, @RequestBody Event updatedEvent) {
        systemLogger.logInfo("Updating event with ID: " + id);
        return eventRepository.findById(id)
                .map(event -> {
                    event.setEventName(updatedEvent.getEventName());
                    event.setEventDateTime(updatedEvent.getEventDateTime());
                    event.setEventVenue(updatedEvent.getEventVenue());
                    event.setMaxTickets(updatedEvent.getMaxTickets());
                    return ResponseEntity.ok(eventRepository.save(event));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Delete an event
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable String id) {
        systemLogger.logInfo("Deleting event with ID: " + id);
        if (eventRepository.existsById(id)) {
            eventRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
