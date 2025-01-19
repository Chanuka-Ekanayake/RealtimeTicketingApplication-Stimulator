package org.example.ticketingapplication.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
    - Load and validate system-wide configurations

 */

@Configuration
@Component
public class AppConfig {

    private int totalTickets;
    private int ticketReleaseRate;
    private int customerRetrievalRate;
    private int maxTicketsPoolCapacity;
    private int customerBuyingQuantity;
    private int vendorSellingQuantity;

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

    public int getCustomerBuyingQuantity() {
        return customerBuyingQuantity;
    }

    public void setCustomerBuyingQuantity(int customerBuyingQuantity) {
        this.customerBuyingQuantity = customerBuyingQuantity;
    }

    public int getVendorSellingQuantity() {
        return vendorSellingQuantity;
    }

    public void setVendorSellingQuantity(int vendorSellingQuantity) {
        this.vendorSellingQuantity = vendorSellingQuantity;
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
