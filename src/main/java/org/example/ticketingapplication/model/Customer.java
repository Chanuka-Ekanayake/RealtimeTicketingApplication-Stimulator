package org.example.ticketingapplication.model;



import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.UUID;

/**
 -  Initialize customer details

 */

public class Customer {


    //CustomerDetails Details for the program
    private final String customerId = UUID.randomUUID().toString();


    //CustomerDetails Details for Database
    private String customerName;
    private String customerEmail;
    private final LocalDateTime dateTimeAdded;
    private boolean isVIP;

    private LinkedList<Ticket> tickets;
    private TicketPool ticketPool;
    private int buyingQuantity;
    private int customerRetrievalRate;





    public Customer(String customerName, String customerEmail,boolean isVIP) {
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.isVIP = isVIP;
        this.dateTimeAdded = LocalDateTime.now();
    }



    //-- Getters and Setters --
    public String getCustomerId() {
        return customerId;
    }


    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getDateAdded() {
        return dateTimeAdded.toString();
    }

    public boolean isVIP() {
        return isVIP;
    }

    public void setVIP(boolean VIP) {
        isVIP = VIP;
    }

    public LinkedList<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(LinkedList<Ticket> tickets) {
        this.tickets = tickets;
    }


    @Override
    public String toString() {
        return "CustomerDetails {" +
                "customerName='" + customerName + '\'' +
                ", customerEmail='" + customerEmail + '\'' +
                ", isVIP=" + isVIP +
                ", customerId='" + customerId + '\'' +
                '}';
    }

}

