package org.example.ticketingapplication.model;



import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.UUID;

/**
 -  Initialize customer details

 */

@Entity
@Table(name = "customer")
public class Customer {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerId;


    //CustomerDetails Details for Database
    @Column(nullable = false)
    private String customerName;

    @Column(nullable = false, unique = true)
    private String customerEmail;

    @Column(nullable = false)
    private final LocalDateTime dateTimeAdded;

    @Column(nullable = false)
    private boolean isVIP;

//    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    @Transient
    private LinkedList<Ticket> tickets;

    @Transient
    private TicketPool ticketPool;

    @Transient
    private int buyingQuantity;

    @Transient
    private int customerRetrievalRate;





    public Customer(String customerName, String customerEmail,boolean isVIP) {
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.isVIP = isVIP;
        this.dateTimeAdded = LocalDateTime.now();
    }

    public Customer() {
        this.dateTimeAdded = LocalDateTime.now();
    }



    //-- Getters and Setters --
    public Long getCustomerId() {
        return customerId;
    }


    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
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

