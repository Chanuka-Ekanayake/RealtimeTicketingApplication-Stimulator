package org.example.ticketingapplication.model;



import jakarta.persistence.*;
import org.example.ticketingapplication.service.EventService;
import org.example.ticketingapplication.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;


/**
 - Initialize vendor information

 */

@Entity
@Table(name = "vendor")
public class Vendor implements Runnable {

    @Id
    private String vendorId;

    @Column(nullable = false)
    private String vendorName;

    @Column(nullable = false, unique = true)
    private String vendorEmail;

    @Transient
    private int totalTicketsToBeSold = 0;

    @Transient
    private int ticketReleaseRate;

    @Column(nullable = false)
    private BigDecimal totalProfit = BigDecimal.valueOf(0);

    @Transient
    private TicketPool ticketPool;

    @OneToMany(mappedBy = "vendor")
    @Transient
    private LinkedList<Event> eventsList = new LinkedList<>();

    @Transient
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

    public Vendor() {
    }



    public String getVendorId() {
        return vendorId;
    }


    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
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

    public int getTotalTicketsToBeSold() {
        return totalTicketsToBeSold;
    }

    public void setTotalTicketsToBeSold(int totalTicketsToBeSold) {
        this.totalTicketsToBeSold = totalTicketsToBeSold;
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

    public void setTicketingProcess(TicketPool ticketPool, int totalTicketsToBeSold, int ticketReleaseRate) {
        this.ticketPool = ticketPool;
        this.totalTicketsToBeSold = totalTicketsToBeSold;
        this.ticketReleaseRate = ticketReleaseRate;
    }

    public void setTicketPool(TicketPool ticketPool) {
        this.ticketPool = ticketPool;
    }

    public void setEventList(LinkedList<Event> events) {

        for(Event event : events) {
            boolean isAdded = false;
            for(Event Preevent : eventsList){
                if (Preevent.getEventId().equals(event.getEventId())) {
                    isAdded = true;
                    break;
                }
            }
            if(!isAdded){
                eventsList.add(event);
            }
        }
        this.eventsList = events;
    }


    public List<Event> getEventsList() {
        return eventsList;
    }

    public void setEventsList(LinkedList<Event> eventsList) {
        this.eventsList = eventsList;
    }

    public void addEvent(Event event){
        eventsList.add(event);
    }

    public void removeEvent(Event event){
        eventsList.remove(event);
        event.setVendor(null);
    }


    @Override
    public String toString() {
        return "Vendor{" +
                "vendorId='" + vendorId + '\'' +
                ", vendorName='" + vendorName + '\'' +
                ", vendorEmail='" + vendorEmail + '\'' +
                ", totalProfit=" + totalProfit + "\"" +
                ", totalEventsRegistered=" + eventsList.size() +
                '}';
    }


    @Override
    public void run() {

        if(ticketPool == null || ticketReleaseRate == 0 || totalTicketsToBeSold == 0){
            System.out.println("Please Initialise the TicketPool, TicketReleaseRate and the Total Tickets to be sold in the TicketPool!");
            stopVendor();
        }

        running = true;
        System.out.println("Vendor - " + vendorName +"  started Adding Tickets!");

        int totalTicketsAdded = 0;

        while (running && totalTicketsToBeSold > 0) {

            if(eventsList.isEmpty()){
                System.out.println("Event list is empty for the Vendor! Please add Events and Tickets to interact with the TicketPool!");
            }else {
                for (Event event : eventsList) {
                    for (Ticket ticket : event.getTickets()) {
                        try {
                            ticketPool.addTicket(ticket, vendorName);
                            totalTicketsAdded++;

                            if (totalTicketsAdded % ticketReleaseRate == 0) {
                                Thread.sleep(ticketReleaseRate * 3000L); //Sleep More than customers to make tickets Demanding
                            }

                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            System.out.println("Vendor - " + vendorName + " is interrupted!");
                            System.out.println(e.getMessage());
                        }
                    }
                }
            }
        }
        System.out.println("No. of tickets added by "+vendorName+": " + totalTicketsAdded);
    }

    public void stopVendor() {
        running = false;
    }






    //Print Events on the list
    public void printEvents() {
        System.out.println("Events List: ");
        for (Event event : eventsList) {
            System.out.println("\t"+event.getEventName()+"--"+event.getEventId());
        }
    }

    //Create tickets for a specific event
    public void createTickets(Event event, BigDecimal ticketPrice, LocalDateTime ticketExpireDateTime) {

        while (totalTicketsToBeSold > 0) {
            for(Event VendorEvents:eventsList){
                if(VendorEvents.getEventId().equals(event.getEventId())){
                    VendorEvents.addTicket(new Ticket(event, ticketPrice, ticketExpireDateTime));
                    totalTicketsToBeSold--;
                }
            }
        }
    }

    //Create tickets automatically for required Events
    public void createTicketsAutomatically(BigDecimal ticketPrice, LocalDateTime ticketExpireDateTime) {
        int ticketsFilled = 0;
        while (totalTicketsToBeSold > 0) {
            for (Event event : eventsList) {
                if (event.getTicketAvailable() < event.getMaxTickets()) {
                    event.createEventTicket(event, ticketPrice, ticketExpireDateTime);
                    ticketsFilled++;
                    totalTicketsToBeSold--;
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

    //Create Unique VendorID's
    public void createVendorID(List<Vendor> vendorsList) {
        int lastIndex = 0;
        String GeneratedVendorID;

        if(vendorsList.isEmpty()){
            GeneratedVendorID = "V-0001";
        } else {
            String lastDigits = vendorsList.getLast().getVendorId().substring(vendorsList.getLast().getVendorId().length() - 4); //Extracts the last 5 digits from the VendorID

            //Convert the string to integer
            try{
                lastIndex = Integer.parseInt(lastDigits);
            }
            catch (NumberFormatException nfe){
                System.out.println("Invalid Vendor ID");
            }
            GeneratedVendorID = "V-" + ++lastIndex;
        }
        this.vendorId = GeneratedVendorID;
    }
}
