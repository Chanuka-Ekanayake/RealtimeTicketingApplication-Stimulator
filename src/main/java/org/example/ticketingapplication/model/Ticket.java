package org.example.ticketingapplication.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 -  Represents tickets for events.

 */

public class Ticket extends Event {

    private final String ticketId;
    private BigDecimal price;
    private LocalDate expireDate;
    private LocalTime expireTime;
    private Event event;

    public Ticket(Event event, String ticketId, BigDecimal price, LocalDate expireDate, LocalTime expireTime) {
        super(event.getEventId(), event.getEventName(), event.getEventDate(), event.getEventTime(),event.getEventCategory() ,event.getEventVenue());
        this.ticketId = ticketId;
        this.price = price;
        this.expireDate = expireDate;
        this.expireTime = expireTime;
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

    public LocalDate getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(LocalDate expireDate) {
        this.expireDate = expireDate;
    }

    public LocalTime getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(LocalTime expireTime) {
        this.expireTime = expireTime;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
}
