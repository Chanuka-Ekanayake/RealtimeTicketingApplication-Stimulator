package org.example.ticketingapplication.service;


import org.example.ticketingapplication.model.Customer;
import org.example.ticketingapplication.model.TicketPool;
import org.example.ticketingapplication.model.Vendor;
import org.example.ticketingapplication.util.ThreadPoolManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;


@Service
public class VendorCustomerManager {
    private final ConcurrentHashMap<String, Vendor> activeVendors = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, Customer> activeCustomers = new ConcurrentHashMap<>();
    private ThreadPoolManager threadPoolManager;
    private TicketPool ticketPool;

    private int customerRetrievalRate;
    private int ticketReleaseRate;
    private int customerBuyingQuantity;
    private int vendorTicketsToSell;
    private VendorService vendorService;
    private CustomerService customerService;

    public VendorCustomerManager(){

    }

    public VendorCustomerManager(ThreadPoolManager threadPoolManager, TicketPool ticketPool, int customerRetrievalRate, int ticketReleaseRate, int customerBuyingQuantity, int vendorTicketsToSell, VendorService vendorService, CustomerService customerService, List<Customer> customers, List<Vendor> vendors) {
        updateActiveMaps(customers, vendors);
        this.threadPoolManager = threadPoolManager;
        this.ticketPool = ticketPool;
        this.customerRetrievalRate = customerRetrievalRate;
        this.ticketReleaseRate = ticketReleaseRate;
        this.customerBuyingQuantity = customerBuyingQuantity;
        this.vendorTicketsToSell = vendorTicketsToSell;
        this.vendorService = vendorService;
        this.customerService = customerService;

    }

    @Autowired
    public VendorCustomerManager(VendorService vendorService, CustomerService customerService) {
        this.vendorService = vendorService;
        this.customerService = customerService;
    }

    // Add a new vendor
    public void addVendor(String vendorName, String vendorEmail) {
        Vendor newVendor = vendorService.createVendor(vendorName, vendorEmail);
        activeVendors.put(newVendor.getVendorId(), newVendor);

        newVendor.ticketSellingProcess(ticketPool,vendorTicketsToSell,ticketReleaseRate); //Initialize Vendor before entering the ticketPool

        threadPoolManager.submitTask(newVendor);
        System.out.println("Vendor added and task started: " + vendorName);
    }


    // Remove an existing vendor
    public void removeVendor (String vendorName){
        Vendor vendor = activeVendors.remove(vendorName);
        if (vendor != null) {
            vendor.stopVendor(); // Signal the task to stop

            System.out.println("Vendor removed: " + vendorName);
            vendorService.deleteVendorById(vendor.getVendorId());

        } else {
            System.out.println("Vendor not found: " + vendorName);
        }
    }

    // Add a new customer
    public void addCustomer (String customerName, String customerEmail, Boolean isVIP) {
        Customer newCustomer = customerService.createCustomer(customerName, customerEmail, isVIP);
        activeCustomers.put(newCustomer.getCustomerId(), newCustomer);

        newCustomer.ticketBuyingProperties(ticketPool,customerBuyingQuantity,customerRetrievalRate);

        threadPoolManager.submitTask(newCustomer); // Submit the task to the thread pool
        System.out.println("Customer added and task started: " + customerName);
    }

    // Remove an existing customer
    public void removeCustomer (String customerName){
        Customer customer = activeCustomers.remove(customerName);
        if (customer != null) {
            customer.stopCustomer(); // Signal the task to stop
            System.out.println("Customer removed: " + customerName);
            customerService.deleteCustomer(customer.getCustomerId());

        } else {
            System.out.println("Customer not found: " + customerName);
        }
    }

    public void updateActiveMaps(List<Customer> customers, List<Vendor> vendors) {
        for (Customer customer : customers) {
            activeCustomers.put(customer.getCustomerId(), customer);
        }
        System.out.println("Active customers initialised");

        for(Vendor vendor :vendors ){
            activeVendors.put(vendor.getVendorId(), vendor);
        }
        System.out.println("Active vendors initialised");
    }

}

