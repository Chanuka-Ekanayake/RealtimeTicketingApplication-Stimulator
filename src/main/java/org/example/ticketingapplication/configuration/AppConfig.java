package org.example.ticketingapplication.configuration;

import org.springframework.stereotype.Component;

/**
    - Load and validate system-wide configurations

 */

@Component
public class AppConfig {

    private int totalTickets;
    private int ticketReleaseRate;
    private int customerRetrievalRate;
    private int maxTicketsPoolCapacity;

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

    public int getCustomerRetrievalRate() {
        return customerRetrievalRate;
    }

    public void setCustomerRetrievalRate(int customerRetrievalRate) {
        this.customerRetrievalRate = customerRetrievalRate;
    }

    public int getMaxTicketsPoolCapacity() {
        return maxTicketsPoolCapacity;
    }

    public void setMaxTicketsPoolCapacity(int maxTicketsPoolCapacity) {
        this.maxTicketsPoolCapacity = maxTicketsPoolCapacity;
    }

    @Override
    public String toString() {
        return "AppConfig{" +
                "totalTickets=" + totalTickets +
                ", ticketReleaseRate=" + ticketReleaseRate +
                ", customerRetrievalRate=" + customerRetrievalRate +
                ", maxTicketsPoolCapacity=" + maxTicketsPoolCapacity +
                '}';
    }
}
