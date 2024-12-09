package org.example.ticketingapplication.model;





import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.LinkedList;


/**
 -  Represents an event.

 */


public class Event {
    private String eventId;
    private String eventName;
    private LocalDateTime eventDateTime;
    private String eventVenue;
    private String eventCategory;
    private int maxTickets;
    private int ticketAvailable = maxTickets;
    private String vendorID;
    private LinkedList<Ticket> tickets = new LinkedList<>();

    public Event( String eventName, LocalDateTime eventDateTime, String eventVenue, String eventCategory, int maxTickets) {
        this.eventName = eventName;
        this.eventDateTime = eventDateTime;
        this.eventVenue = eventVenue;
        this.eventCategory = eventCategory;
        this.maxTickets = maxTickets;
    }

    public Event(String eventId, String eventName, LocalDateTime eventDateTime,String eventVenue, String eventCategory, int maxTickets, String vendorID) {
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

    public String getVendorID() {
        return vendorID;
    }

    public void setVendorID(String vendorID) {
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
        }
    }


}
