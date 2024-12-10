package org.example.ticketingapplication.model;



import jakarta.persistence.*;


import java.math.BigDecimal;

import java.time.LocalDateTime;
import java.util.List;

/**
 -  Represents tickets for events.

 */

@Entity
@Table(name = "Tickets")
public class Ticket {

    @Id
    private String ticketId;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private LocalDateTime expireDateTime;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    @ManyToOne
    @JoinColumn(name = "customer_id",nullable = true)
    private Customer customer;

    public Ticket(Event event, String ticketId, BigDecimal price, LocalDateTime expireDateTime) {
        this.ticketId = ticketId;
        this.price = price;
        this.expireDateTime = expireDateTime;
        this.event = event;
    }

    public Ticket(Event event, BigDecimal price, LocalDateTime expireDateTime) {
        this.price = price;
        this.expireDateTime = expireDateTime;
        this.event = event;
    }


    public Ticket() {
    }

    public Ticket(String ticketId, BigDecimal price, LocalDateTime expireDateTime, Event event, Customer customer) {
        this.ticketId = ticketId;
        this.price = price;
        this.expireDateTime = expireDateTime;
        this.event = event;
        this.customer = customer;
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

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "ticketId='" + ticketId + '\'' +
                ", price=" + price +
                ", expireDateTime=" + expireDateTime +
                ", event=" + event.getEventName() +
                '}';
    }


    //Create Unique TicketID's
    public void createTicketID(List<Ticket> ticketsList) {
        int lastIndex = 0;
        String GeneratedTicketID;

        if(ticketsList.isEmpty()){
            if(event.getEventId() != null) {
                GeneratedTicketID = event.getEventId() + "-T-0001";
            } else {
                GeneratedTicketID = "0000-T-0001";
            }
            this.ticketId = GeneratedTicketID;

        } else {
            String lastDigits = ticketsList.getLast().getTicketId().substring(ticketsList.getLast().getTicketId().length() - 4); //Extracts the last 5 digits from the TicketID

            //Convert the string to integer
            try{
                lastIndex = Integer.parseInt(lastDigits);
            }
            catch (NumberFormatException nfe){
                System.out.println("Invalid Event ID");
            }
            GeneratedTicketID = event.getEventId() + "-T-" + ++lastIndex;
            this.ticketId = GeneratedTicketID;
        }
    }
}
