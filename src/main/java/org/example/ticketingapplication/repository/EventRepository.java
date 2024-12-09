package org.example.ticketingapplication.repository;

import org.example.ticketingapplication.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

/**
 - Interface for Vendor entity

 */


public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByEventDateTimeBefore(LocalDateTime now);
}

