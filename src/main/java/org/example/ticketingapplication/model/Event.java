package org.example.ticketingapplication.model;



import jakarta.persistence.*;
import org.example.ticketingapplication.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;


/**
 -  Represents an event.

 */

@Entity
@Table(name = "event")
public class Event {

    @Id
    private String eventId;

    @Column(nullable = false)
    private String eventName;

    @Column(nullable = false)
    private LocalDateTime eventDateTime;

    @Column(nullable = false)
    private String eventVenue;

    @Column(nullable = false)
    private String eventCategory;

    @Column(nullable = false)
    private int maxTickets;

    @Column(nullable = false)
    private int ticketAvailable = maxTickets;

    @ManyToOne()
    @JoinColumn(name = "vendor_id", referencedColumnName = "vendorId")
    private Vendor vendor;

    @Transient
    private LinkedList<Ticket> tickets = new LinkedList<>();

    @Transient
    private TicketService ticketService;

    public Event(String eventName, LocalDateTime eventDateTime, String eventVenue, String eventCategory, int maxTickets, Vendor vendor) {
        this.eventName = eventName;
        this.eventDateTime = eventDateTime;
        this.eventVenue = eventVenue;
        this.eventCategory = eventCategory;
        this.maxTickets = maxTickets;
        this.vendor = vendor;

    }

    public Event(String eventId, String eventName, LocalDateTime eventDateTime,String eventVenue, String eventCategory, int maxTickets, Vendor vendor) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.eventDateTime = eventDateTime;
        this.eventVenue = eventVenue;
        this.eventCategory = eventCategory;
        this.maxTickets = maxTickets;
        this.vendor = vendor;
    }

    public Event(String eventId, String eventName, LocalDateTime eventDateTime, String eventVenue, String eventCategory, int maxTickets, int ticketAvailable, Vendor vendor, LinkedList<Ticket> tickets) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.eventDateTime = eventDateTime;
        this.eventVenue = eventVenue;
        this.eventCategory = eventCategory;
        this.maxTickets = maxTickets;
        this.ticketAvailable = ticketAvailable;
        this.vendor = vendor;
        this.tickets = tickets;
    }

    //Default Constructor
    public Event() {

    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public LocalDateTime getEventDateTime() {
        return eventDateTime;
    }

    public void setEventDateTime(LocalDateTime eventDate) {
        this.eventDateTime = eventDate;
        System.out.println("Event Data Has been Updated");
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


    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
    }

    public Vendor getVendor() {
        return vendor;
    }

    public LinkedList<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(LinkedList<Ticket> tickets) {
        this.tickets = tickets;
    }

    public void addTicket(Ticket ticket) {
        tickets.add(ticket);
        ++ticketAvailable;
    }

    public void removeTicket(Ticket ticket) {
        tickets.remove(ticket);
        --ticketAvailable;
    }



    @Override
    public String toString() {
        return "Event{" +
                "eventId='" + eventId + '\'' +
                ", eventName='" + eventName + '\'' +
                ", eventDate=" + eventDateTime +
                ", eventVenue='" + eventVenue + '\'' +
                ", eventCategory='" + eventCategory + '\'' +
                ", maxTickets=" + maxTickets + "\"" +
                ", ticketAvailable=" + ticketAvailable +
                ", vendor=" + vendor.getVendorName() +
                '}';
    }


    public void createEventID(List<Event> eventsList){

        int lastIndex = 0;
        String GeneratedEventID;

        if(eventsList.isEmpty()){

            GeneratedEventID = vendor.getVendorId() + "-E-0001";

            this.eventId = GeneratedEventID;

        } else {
            String lastDigits = eventsList.getLast().getEventId().substring(eventsList.getLast().getEventId().length() - 4); //Extracts the last 5 digits from the EventID

            //Convert the string to integer
            try{
                lastIndex = Integer.parseInt(lastDigits);
            }
            catch (NumberFormatException nfe){
                System.out.println("Invalid Event ID");
            }
            String eventLastIndex = String.format("%04d", ++lastIndex); //Convert the integer into 4 decimal Number
            GeneratedEventID = vendor.getVendorId() + "-E-"+eventLastIndex;
            this.eventId = GeneratedEventID;
        }
    }

}
