package org.example.ticketingapplication.model;


import java.time.LocalDate;
import java.time.LocalTime;

/**
 -  Represents an event.

 */

public class Event {
    private final String eventId;
    private String eventName;
    private LocalDate eventDate;
    private LocalTime eventTime;
    private String eventVenue;
    private String eventCategory;
    private int maxTickets;
    private int ticketAvailable = maxTickets;

    public Event(String eventId, String eventName, LocalDate eventDate, LocalTime eventTime, String eventVenue, String eventCategory, int maxTickets) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.eventTime = eventTime;
        this.eventVenue = eventVenue;
        this.eventCategory = eventCategory;
        this.maxTickets = maxTickets;
    }

    public Event(String eventId, String eventName, LocalDate eventDate, LocalTime eventTime, String eventVenue, String eventCategory) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.eventTime = eventTime;
        this.eventVenue = eventVenue;
        this.eventCategory = eventCategory;
    }

    public String getEventId() {
        return eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public LocalDate getEventDate() {
        return eventDate;
    }

    public void setEventDate(LocalDate eventDate) {
        this.eventDate = eventDate;
        System.out.println("Event Data Has been Updated");
    }

    public LocalTime getEventTime() {
        return eventTime;
    }

    public void setEventTime(LocalTime eventTime) {
        this.eventTime = eventTime;
        System.out.println("Event Time has been Updated");
    }

    public String getEventVenue() {
        return eventVenue;
    }

    public void setEventVenue(String eventVenue) {
        this.eventVenue = eventVenue;
        System.out.println("Event Venue has been Updated");
    }

    public String getEventCategory() {
        return eventCategory;
    }

    public void setEventCategory(String eventCategory) {
        this.eventCategory = eventCategory;
    }

    public int getMaxTickets() {
        return maxTickets;
    }

    public void setMaxTickets(int maxTickets) {
        this.maxTickets = maxTickets;
    }

    public int getTicketAvailable() {
        return ticketAvailable;
    }

    public void setTicketAvailable(int ticketAvailable) {
        this.ticketAvailable = ticketAvailable;
    }


    @Override
    public String toString() {
        return "Event{" +
                "eventId='" + eventId + '\'' +
                ", eventName='" + eventName + '\'' +
                ", eventDate=" + eventDate +
                ", eventTime=" + eventTime +
                ", eventVenue='" + eventVenue + '\'' +
                ", eventDescription='" + eventCategory + '\'' +
                ", maxTickets=" + maxTickets +
                ", ticketAvailable=" + ticketAvailable +
                '}';
    }
}
