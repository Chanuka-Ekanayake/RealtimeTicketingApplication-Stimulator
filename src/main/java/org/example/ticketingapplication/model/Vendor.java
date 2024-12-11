package org.example.ticketingapplication.model;



import jakarta.persistence.*;
import org.example.ticketingapplication.util.ThreadPoolManager;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
    private int totalTicketsToBeSold ;

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

    @Transient
    private ThreadPoolManager threadPoolManager;


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

    public void ticketSellingProcess(TicketPool ticketPool, int totalTicketsToBeSold, int ticketReleaseRate) {
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


    public LinkedList<Event> getEventsList() {
        return eventsList;
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

        while (running && totalTicketsToBeSold != 0) {

            if(eventsList.isEmpty()){
                System.out.println("Event list is empty for the Vendor! Please add Events and Tickets to interact with the TicketPool!");
            }else {
                for (Event event : eventsList) {
                    for (Ticket ticket : event.getTickets()) {
                        try {
                            ticketPool.addTicket(ticket, vendorName);

                            totalTicketsAdded++;
                            totalTicketsToBeSold--;

                            if (totalTicketsAdded % ticketReleaseRate == 0) {
                                Thread.sleep(ticketReleaseRate * 3000L); //Sleep More than customers to make tickets Demanding
                            }

                            if(totalTicketsToBeSold == 0){
                                break;
                            } else if (!running) {
                                break;

                            }

                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            System.out.println("Vendor - " + vendorName + " is interrupted!");
                            System.out.println(e.getMessage());
                        }
                    }

                    if(totalTicketsToBeSold == 0){
                        stopVendor();
                        break;
                    } else if (!running) {
                        break;
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

            String vendorLastIndex = String.format("%04d", ++lastIndex); //Convert the integer into 4 decimal Number
            GeneratedVendorID = "V-" + vendorLastIndex;
        }
        this.vendorId = GeneratedVendorID;
    }

}
