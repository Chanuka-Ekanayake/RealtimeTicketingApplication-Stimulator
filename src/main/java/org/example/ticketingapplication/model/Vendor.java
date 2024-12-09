package org.example.ticketingapplication.model;



import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.LinkedList;


/**
 - Initialize vendor information

 */

public class Vendor implements Runnable {

    private String vendorId;
    private String vendorName;
    private String vendorEmail;


    private int totalTickets;
    private int ticketReleaseRate;
    private BigDecimal totalProfit;


    private TicketPool ticketPool;
    private LinkedList<Event> eventsList;
    private volatile boolean running = true;



    public Vendor(String vendorId,String vendorName, String vendorEmail) {
        this.vendorId = vendorId;
        this.vendorName = vendorName;
        this.vendorEmail = vendorEmail;
    }

    public Vendor(String vendorName, String vendorEmail) {
        this.vendorName = vendorName;
        this.vendorEmail = vendorEmail;
    }

    public Vendor(String vendorId, String vendorName, String vendorEmail, BigDecimal totalProfit, LinkedList<Event> eventsList) {
        this.vendorId = vendorId;
        this.vendorName = vendorName;
        this.vendorEmail = vendorEmail;
        this.totalProfit = totalProfit;
        this.eventsList = eventsList;
    }

    public String getVendorId() {
        return vendorId;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getVendorEmail() {
        return vendorEmail;
    }

    public void setVendorEmail(String vendorEmail) {
        this.vendorEmail = vendorEmail;
    }

    public int getTotalTickets() {
        return totalTickets;
    }

    public void setTotalTickets(int totalTickets) {
        this.totalTickets = totalTickets;
    }

    public int getTicketReleaseRate() {
        return ticketReleaseRate;
    }

    public void setTicketReleaseRate(int ticketReleaseRate) {
        this.ticketReleaseRate = ticketReleaseRate;
    }

    public BigDecimal getTotalProfit() {
        return totalProfit;
    }

    public void setTotalProfit(BigDecimal totalProfit) {
        this.totalProfit = totalProfit.setScale(2, RoundingMode.HALF_UP); //Rounds up the amount of money for 2 decimal places
    }

    public void setTicketingProcess(TicketPool ticketPool, int totalTickets, int ticketReleaseRate) {
        this.ticketPool = ticketPool;
        this.totalTickets = totalTickets;
        this.ticketReleaseRate = ticketReleaseRate;
    }

    public void setTicketPool(TicketPool ticketPool) {
        this.ticketPool = ticketPool;
    }

    public void setEventList(LinkedList<Event> events) {
        this.eventsList = events;
    }

    public LinkedList<Event> getEventsList() {
        return eventsList;
    }

    @Override
    public String toString() {
        return "Vendor{" +
                "vendorId='" + vendorId + '\'' +
                ", vendorName='" + vendorName + '\'' +
                ", vendorEmail='" + vendorEmail + '\'' +
                '}';
    }


    @Override
    public void run() {
        System.out.println("Vendor - " +vendorName+" started adding Tickets");

        while (running) {

        }

    }




    //Create an event for the vendor
    public Event createEvent(String eventName, LocalDateTime eventDateTime, String eventVenue, String eventCategory, int maxTickets, BigDecimal ticketPrice, LocalDateTime ticketExpireDateTime) {
        Event event = new Event(eventName, eventName,eventDateTime,eventVenue,eventCategory,maxTickets,vendorId);

        event.createEventID(eventsList);
        eventsList.add(event);
        return event;
    }

    //Print Events on the list
    public void printEvents() {
        System.out.println("Events List: ");
        for (Event event : eventsList) {
            System.out.println("\t"+event.getEventName()+"--"+event.getEventId());
        }
    }

    //Create tickets for a specific event
    public void createTickets(String eventID, BigDecimal ticketPrice, LocalDateTime ticketExpireDateTime) {

        while (totalTickets > 0) {
            for (Event event : eventsList) {
                if (event.getEventId().equals(eventID)) {
                    event.createEventTicket(ticketPrice, ticketExpireDateTime);
                    totalTickets--;
                }
            }
        }
    }

    //Create tickets automatically for required Events
    public void createTicketsAutomatically(BigDecimal ticketPrice, LocalDateTime ticketExpireDateTime) {

        int ticketsFilled = 0;

        while (totalTickets > 0) {
            for (Event event : eventsList) {
                if (event.getTickets().size() != event.getMaxTickets()) {
                    event.createEventTicket(ticketPrice, ticketExpireDateTime);
                    ticketsFilled++;
                    totalTickets--;
                }
            }
        }

        if(ticketsFilled > 0){
            System.out.println("Tickets filled: "+ticketsFilled);
        } else {
            System.out.println("No tickets filled");
        }
    }


    public void removeTicketBulk(String eventID, int ticketCount) {
        for(Event event : eventsList){
            if(event.getEventId().equals(eventID)){
                event.deleteEventTickets(ticketCount);
            }
        }
    }
}
