package org.example.ticketingapplication.controller;


import org.example.ticketingapplication.model.Customer;
import org.example.ticketingapplication.model.Event;
import org.example.ticketingapplication.model.Ticket;
import org.example.ticketingapplication.model.Vendor;
import org.example.ticketingapplication.service.InitializerService;
import org.example.ticketingapplication.util.SystemLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/application")
public class ApplicationController {

    @Autowired
    private InitializerService initializerService;

    @Autowired
    private SystemLogger systemLogger;

    private List<Customer> customersList;
    private List<Vendor> vendorsList;
    private List<Event> eventsList;
    private List<Ticket> ticketsList;

    // Initialize data from the database
    @GetMapping("/initialize")
    public String initializeData() {
        customersList = initializerService.loadCustomersFromDatabase();
        vendorsList = initializerService.loadVendorsFromDatabase();
        eventsList = initializerService.loadEventsFromDatabase();
        ticketsList = initializerService.loadTicketsFromDatabase();
        return "Data initialized successfully!";
    }

    // Application status endpoint
    @GetMapping("/status")
    public String getApplicationStatus() {
        systemLogger.logInfo("Fetching application status.");
        return "Application is running successfully!";
    }
}

