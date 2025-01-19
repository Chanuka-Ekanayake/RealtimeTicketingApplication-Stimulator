package org.example.ticketingapplication.service;


import jakarta.validation.constraints.Negative;
import org.example.ticketingapplication.model.Customer;
import org.example.ticketingapplication.model.Event;
import org.example.ticketingapplication.model.TicketPool;
import org.example.ticketingapplication.model.Vendor;
import org.example.ticketingapplication.util.ThreadPoolManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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
    private EventService eventService;

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
    public VendorCustomerManager(VendorService vendorService, CustomerService customerService, EventService eventService) {
        this.vendorService = vendorService;
        this.customerService = customerService;
        this.eventService = eventService;
    }

    // Add a new vendor
    public Vendor addVendor(String vendorName, String vendorEmail) {
        Vendor newVendor = vendorService.createVendor(vendorName, vendorEmail);
        activeVendors.put(newVendor.getVendorId(), newVendor);

        newVendor.ticketSellingProcess(ticketPool,vendorTicketsToSell,ticketReleaseRate); //Initialize Vendor before entering the ticketPool

        System.out.println("Adding a Random Event with 10 tickets to access ticket pool");
        Event NewEvent = eventService.createEvent(newVendor.getVendorId()+"-E-0001", LocalDateTime.now(),"Sri Lanka","Default evvent",10,newVendor);

        eventService.createEventTickets(NewEvent, BigDecimal.valueOf(1000),LocalDateTime.now()); //Create tickets to the event, Since without ticket vendor cannot access the ticketPool

        threadPoolManager.submitTask(newVendor);
        System.out.println("Vendor added and task started: " + vendorName);
        return newVendor;
    }


    // Remove an existing vendor
    public void removeVendor (String vendorId){
        Vendor vendor = activeVendors.remove(vendorId);
        if (vendor != null) {
            vendor.stopVendor(); // Signal the task to stop

            System.out.println("Vendor removed: " + vendorId);
            vendorService.deleteVendorById(vendor.getVendorId());

        } else {
            System.out.println("Vendor not found: " + vendorId);
        }
    }

    // Add a new customer
    public Customer addCustomer (String customerName, String customerEmail, Boolean isVIP) {
        Customer newCustomer = customerService.createCustomer(customerName, customerEmail, isVIP);
        activeCustomers.put(newCustomer.getCustomerId(), newCustomer);

        newCustomer.ticketBuyingProperties(ticketPool,customerBuyingQuantity,customerRetrievalRate);

        threadPoolManager.submitTask(newCustomer); // Submit the task to the thread pool
        System.out.println("Customer added and task started: " + customerName);
        return newCustomer;
    }

    // Remove an existing customer
    public void removeCustomer (String customerId){
        Customer customer = activeCustomers.remove(customerId);
        if (customer != null) {
            customer.stopCustomer(); // Signal the task to stop
            System.out.println("Customer removed: " + customerId);
            customerService.deleteCustomer(customer.getCustomerId());

        } else {
            System.out.println("Customer not found: " + customerId);
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

