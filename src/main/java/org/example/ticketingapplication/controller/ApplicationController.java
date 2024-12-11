package org.example.ticketingapplication.controller;


import org.example.ticketingapplication.configuration.AppConfig;
import org.example.ticketingapplication.model.*;
import org.example.ticketingapplication.service.ConfigService;
import org.example.ticketingapplication.service.InitializerService;
import org.example.ticketingapplication.util.SystemLogger;
import org.example.ticketingapplication.util.ThreadPoolManager;
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

    @Autowired
    private AppConfig appConfig = new AppConfig();

    @Autowired
    ConfigService configService = new ConfigService(appConfig);


    private List<Customer> customersList;
    private List<Vendor> vendorsList;
    private List<Event> eventsList;
    private List<Ticket> ticketsList;

    @Autowired
    private ThreadPoolManager threadPoolManager;

    // Initialize data from the database
    @GetMapping("/initialize")
    public String initializeData() {

        configService.loadConfigFile();


        customersList = initializerService.loadCustomersFromDatabase();
        vendorsList = initializerService.loadVendorsFromDatabase();
        eventsList = initializerService.loadEventsFromDatabase();
        ticketsList = initializerService.loadTicketsFromDatabase();

        return "Data initialized successfully!";
    }

    @GetMapping("/start")
    public void startTicketPool(){

        TicketPool ticketPool = new TicketPool(appConfig.getMaxTicketsPoolCapacity(),appConfig.getTotalTickets());
        threadPoolManager.initializeNewPool();

        for(Customer customer : customersList){
            customer.ticketBuyingProperties(ticketPool,appConfig.getCustomerBuyingQuantity(),appConfig.getCustomerRetrievalRate());
        }

        for(Vendor vendor : vendorsList){
            vendor.ticketSellingProcess(ticketPool,appConfig.getVendorSellingQuantity(),appConfig.getTicketReleaseRate());
            threadPoolManager.submitTask(vendor);
        }

        for(Customer customer : customersList){
                threadPoolManager.submitTask(customer);
            }


        systemLogger.logInfo("Ticket Pool Executing!");
    }

    // Application status endpoint
    @GetMapping("/status")
    public String getApplicationStatus() {
        systemLogger.logInfo("Fetching application status.");
        return "Application is running successfully!";
    }
}

