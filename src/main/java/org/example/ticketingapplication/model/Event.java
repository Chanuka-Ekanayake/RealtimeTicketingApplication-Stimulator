package org.example.ticketingapplication.model;





import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.LinkedList;


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

//    @OneToOne
//    @JoinColumn(name = "vendor_id")
    private Long vendorID;


//    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    @Transient
    private LinkedList<Ticket> tickets = new LinkedList<>();

    public Event( String eventName, LocalDateTime eventDateTime, String eventVenue, String eventCategory, int maxTickets) {
        this.eventName = eventName;
        this.eventDateTime = eventDateTime;
        this.eventVenue = eventVenue;
        this.eventCategory = eventCategory;
        this.maxTickets = maxTickets;
    }

    public Event(String eventId, String eventName, LocalDateTime eventDateTime,String eventVenue, String eventCategory, int maxTickets, Long vendorID) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.eventDateTime = eventDateTime;
        this.eventVenue = eventVenue;
        this.eventCategory = eventCategory;
        this.maxTickets = maxTickets;
        this.vendorID = vendorID;
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

    public Long getVendorID() {
        return vendorID;
    }

    public void setVendorID(Long vendorID) {
        this.vendorID = vendorID;
    }

    public LinkedList<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(LinkedList<Ticket> tickets) {
        this.tickets = tickets;
    }

    @Override
    public String toString() {
        return "Event{" +
                "eventId='" + eventId + '\'' +
                ", eventName='" + eventName + '\'' +
                ", eventDate=" + eventDateTime +
                ", eventVenue='" + eventVenue + '\'' +
                ", eventDescription='" + eventCategory + '\'' +
                ", maxTickets=" + maxTickets +
                ", ticketAvailable=" + ticketAvailable +
                '}';
    }


    public void createEventID(LinkedList<Event> eventsList){

        int lastIndex = 0;
        String GeneratedEventID;

        if(eventsList.isEmpty()){
            if(vendorID != null) {
                GeneratedEventID = vendorID + "-E-0001";

            } else {
                GeneratedEventID = "0000-E-0001";
            }

            setEventId(GeneratedEventID);

        } else {

            String lastDigits = eventsList.getLast().getEventId().substring(eventsList.getLast().getEventId().length() - 4); //Extracts the last 5 digits from the EventID

            //Convert the string to integer
            try{
                lastIndex = Integer.parseInt(lastDigits);
            }
            catch (NumberFormatException nfe){
                System.out.println("Invalid Event ID");
            }

            GeneratedEventID = vendorID + "-E-" + ++lastIndex;
            setEventId(GeneratedEventID);
        }

    }

    public void createEventTicket( BigDecimal price, LocalDateTime dateTime){
        if(tickets.isEmpty()){
            for (int i = 0; i < maxTickets; i++) {
                Ticket newTicket = new Ticket(eventId, price, dateTime);
                newTicket.createTicketID(tickets);
                tickets.add(newTicket);
                ticketAvailable++;
            }
        } else if (ticketAvailable == maxTickets) {
            System.out.println("For "+eventName+"Maximum ticketCount is reached!\nPlease Update the Maximum Ticket Count.");

        } else {
            for (int i = 0; i < (maxTickets-ticketAvailable); i++) {
                Ticket newTicket = new Ticket(eventId, price, dateTime);
                newTicket.createTicketID(tickets);
            }
        }
    }

    public void deleteEventTickets(int deleteTicketCount){
        for (int i = 0; i < deleteTicketCount; i++) {
            System.out.println(tickets.get(i).getTicketId() + "Removed");
            tickets.removeFirst();
            ticketAvailable--;
        }
    }


    //Remove a specific ticket
    public void removeTicket(Ticket ticket){
        for(Ticket t : tickets){
            if(t.getTicketId().equals(ticket.getTicketId())){
                tickets.remove(ticket);
            }
        }
    }


}
