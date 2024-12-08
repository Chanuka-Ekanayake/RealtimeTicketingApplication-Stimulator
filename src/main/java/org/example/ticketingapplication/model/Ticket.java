package org.example.ticketingapplication.model;



import java.math.BigDecimal;

import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 -  Represents tickets for events.

 */

public class Ticket extends Event {

    private final String ticketId;
    private BigDecimal price;
    private LocalDateTime expireDateTime;
    private Event event;

    public Ticket(Event event, String ticketId, BigDecimal price, LocalDateTime expireDateTime, LocalTime expireTime) {
        super(event.getEventId(), event.getEventName(), event.getEventDate(), event.getEventTime(),event.getEventCategory() ,event.getEventVenue());
        this.ticketId = ticketId;
        this.price = price;
        this.expireDateTime = expireDateTime;
        this.event = event;
    }

    public String getTicketId() {
        return ticketId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public LocalDateTime getExpireDateTime() {
        return expireDateTime;
    }

    public void setExpireDateTime(LocalDateTime expireDateTime) {
        this.expireDateTime = expireDateTime;
    }



    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
}
