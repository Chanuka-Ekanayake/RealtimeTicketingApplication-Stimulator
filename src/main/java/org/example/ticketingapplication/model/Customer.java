package org.example.ticketingapplication.model;



import org.example.ticketingapplication.controller.TicketPool;

import java.time.LocalDateTime;
import java.util.LinkedList;

/**
 -  Initialize customer details

 */

public class Customer implements Runnable {


    //Customer Details for the program
    private final String customerId;


    //Customer Details for Database
    private String customerName;
    private String customerEmail;
    private final LocalDateTime dateAdded;
    private boolean isVIP;


    private  TicketPool ticketPool;
    private LinkedList<Ticket> tickets;
    private int buyingQuantity;
    private int customerRetrievalRate;




    public Customer(String customerId, String customerName, String customerEmail, String dateAdded, boolean isVIP) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.isVIP = isVIP;
        this.dateAdded = LocalDateTime.now();
    }



    //-- Getters and Setters --
    public String getCustomerId() {
        return customerId;
    }

    public int getBuyingQuantity() {
        return buyingQuantity;
    }

    public void setBuyingQuantity(int buyingQuantity) {
        this.buyingQuantity = buyingQuantity;
    }

    public int getCustomerRetrievalRate() {
        return customerRetrievalRate;
    }

    public void setCustomerRetrievalRate(int customerRetrievalRate) {
        this.customerRetrievalRate = customerRetrievalRate;
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
        return dateAdded.toString();
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

    public void setTicketingProcess(int buyingQuantity, int customerRetrievalRate, TicketPool ticketPool) {
        this.buyingQuantity = buyingQuantity;
        this.customerRetrievalRate = customerRetrievalRate;
        this.ticketPool = ticketPool;
    }


    public void setTicketPool(TicketPool ticketPool) {
        this.ticketPool = ticketPool;
    }

    @Override
    public String toString() {
        return "Customer {" +
                "customerName='" + customerName + '\'' +
                ", customerEmail='" + customerEmail + '\'' +
                ", isVIP=" + isVIP +
                ", customerId='" + customerId + '\'' +
                '}';
    }


    @Override
    public void run() {

    }
}

