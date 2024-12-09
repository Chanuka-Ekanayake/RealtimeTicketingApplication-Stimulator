package org.example.ticketingapplication.model;





import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;



/**
 -  Initialize customer details

 */

@Entity
@Table(name = "customer")
public class Customer implements Runnable {

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

    @Transient
    @OneToMany(mappedBy = "customer")
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
    public String getCustomerId() {
        return "C-"+String.format("%04d", customerId);
    }

    public long getRealCustomerId(){
        return customerId;
    }

    public void setRealCustomerId(long customerId){
        this.customerId = customerId;
    }


    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerId(String customerId) {
        this.customerId = Long.parseLong(customerId.substring(customerId.length()-4));
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

    public LocalDateTime getDateTimeAdded() {
        return dateTimeAdded;
    }



    @Override
    public String toString() {
        return "Customer{" +
                "customerId='" + customerId + '\'' +
                ", customerName='" + customerName + '\'' +
                ", customerEmail='" + customerEmail + '\'' +
                ", dateTimeAdded=" + dateTimeAdded +
                ", isVIP=" + isVIP +
                ", totalTickets=" + tickets.size() +
                '}';
    }

    @Override
    public void run() {

    }


    public void createCustomerID(List<Customer> customersList) {
        long lastIndex = 0L;

        if(!customersList.isEmpty()){
            lastIndex = customersList.getLast().getRealCustomerId();
        }

        this.customerId = lastIndex + 1;

    }

}

