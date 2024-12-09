package org.example.ticketingapplication.model;



import java.math.BigDecimal;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.LinkedList;

/**
 -  Represents tickets for events.

 */

public class Ticket {

    private String ticketId;
    private BigDecimal price;
    private LocalDateTime expireDateTime;
    private String eventID;

    public Ticket(String eventID, String ticketId, BigDecimal price, LocalDateTime expireDateTime, LocalTime expireTime) {
        this.ticketId = ticketId;
        this.price = price;
        this.expireDateTime = expireDateTime;
        this.eventID = eventID;
    }

    public Ticket(String eventID, BigDecimal price, LocalDateTime expireDateTime) {
        this.price = price;
        this.expireDateTime = expireDateTime;
        this.eventID = eventID;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
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

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "ticketId='" + ticketId + '\'' +
                ", price=" + price +
                ", expireDateTime=" + expireDateTime +
                ", event=" + eventID +
                '}';
    }


    //Create Unique TicketID's
    public void createTicketID(LinkedList<Ticket> ticketsList) {
        int lastIndex = 0;
        String GeneratedTicketID;

        if(ticketsList.isEmpty()){
            if(eventID != null) {
                GeneratedTicketID = eventID + "-T-0001";
            } else {
                GeneratedTicketID = "0000-T-0001";
            }

        } else {
            String lastDigits = ticketsList.getLast().getTicketId().substring(ticketsList.getLast().getTicketId().length() - 4); //Extracts the last 5 digits from the EventID

            //Convert the string to integer
            try{
                lastIndex = Integer.parseInt(lastDigits);
            }
            catch (NumberFormatException nfe){
                System.out.println("Invalid Event ID");
            }
            GeneratedTicketID = eventID + "-T-" + ++lastIndex;
        }



    }
}
