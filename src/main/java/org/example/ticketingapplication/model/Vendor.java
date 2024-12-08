package org.example.ticketingapplication.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 - Initialize vendor information

 */

public class Vendor implements Runnable {

    private final String vendorId;
    private String vendorName;
    private String vendorEmail;
    private int totalTickets;
    private int ticketReleaseRate;
    private BigDecimal totalProfit;

    public Vendor(String vendorId) {
        this.vendorId = vendorId;
    }


    public Vendor(String vendorId, String vendorName, String vendorEmail, int totalTickets, int ticketReleaseRate) {
        this.vendorId = vendorId;
        this.vendorName = vendorName;
        this.vendorEmail = vendorEmail;
        this.totalTickets = totalTickets;
        this.ticketReleaseRate = ticketReleaseRate;
    }

    public String getVendorId() {
        return vendorId;
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

    public BigDecimal getTotalProfit() {
        return totalProfit;
    }

    public void setTotalProfit(BigDecimal totalProfit) {
        this.totalProfit = totalProfit.setScale(2, RoundingMode.HALF_UP); //Rounds up the amount of money for 2 decimal places
    }

    public void setTicketingProcess(int totalTickets, int ticketReleaseRate) {
        this.totalTickets = totalTickets;
        this.ticketReleaseRate = ticketReleaseRate;
    }


    @Override
    public String toString() {
        return "Vendor{" +
                "vendorId='" + vendorId + '\'' +
                ", vendorName='" + vendorName + '\'' +
                ", vendorEmail='" + vendorEmail + '\'' +
                '}';
    }


    @Override
    public void run() {

    }
}
