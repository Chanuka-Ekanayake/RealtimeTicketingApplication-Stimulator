package org.example.ticketingapplication.controller;


import org.example.ticketingapplication.configuration.AppConfig;
import org.example.ticketingapplication.model.*;
import org.example.ticketingapplication.service.ConfigService;
import org.example.ticketingapplication.service.InitializerService;
import org.example.ticketingapplication.util.SystemLogger;
import org.example.ticketingapplication.util.ThreadPoolManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.core.io.ResourceLoader;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

import java.util.List;
import java.io.File;

@RestController
@RequestMapping("/api/application")
@CrossOrigin(origins = "*")  // Simplify CORS for testing
public class ApplicationController {

    @Autowired
    private InitializerService initializerService;

    @Autowired
    private static SystemLogger systemLogger;

    @Autowired
    public static AppConfig appConfig = new AppConfig();

    @Autowired
    public static ConfigService configService = new ConfigService(appConfig);


    public static List<Customer> customersList;
    public static List<Vendor> vendorsList;
    public static List<Event> eventsList;
    public static List<Ticket> ticketsList;

    @Autowired
    private ResourceLoader resourceLoader;

    // Add these private variables at the class level
    public static int currentPoolSize = 0;
    public static int operators = 0;

    // Initialize data from the database
    @GetMapping("/initialize")
    public static void initializeData() {

        configService.loadConfigFile();


        customersList = InitializerService.loadCustomersFromDatabase();
        vendorsList = InitializerService.loadVendorsFromDatabase();
        eventsList = InitializerService.loadEventsFromDatabase();
        ticketsList = InitializerService.loadTicketsFromDatabase();

    }

    @GetMapping("/start")
    public static void startTicketPool() {
        // Ensure initialization
        initializeData();
        if (customersList == null || vendorsList == null) {
            throw new RuntimeException("Failed to initialize data");
        }
        
        currentPoolSize = 0;
        operators = 0;

        TicketPool ticketPool = new TicketPool(appConfig.getMaxTicketsPoolCapacity(), appConfig.getTotalTickets());
        ThreadPoolManager.initializeNewPool();

        // Start vendors using traditional for loop
        for (Vendor vendor : vendorsList) {
            vendor.ticketSellingProcess(ticketPool, appConfig.getVendorSellingQuantity(), appConfig.getTicketReleaseRate());
            Thread vendorThread = new Thread(vendor);
            ThreadPoolManager.submitTask(vendorThread);
        }

        try {
            // Allow vendors to populate the pool first
            Thread.sleep(1000);

            // Start all customers using traditional for loop
            for (Customer customer : customersList) {
                customer.ticketBuyingProperties(ticketPool, 
                    appConfig.getCustomerBuyingQuantity(), 
                    appConfig.getCustomerRetrievalRate());
                Thread customerThread = new Thread(customer);
                ThreadPoolManager.submitTask(customerThread);
            }

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println(e.getMessage());
        }
    }

    @GetMapping("/stop")
    public static void stopTicketPool() {
        try {
            ThreadPoolManager.shutdown();
            System.out.println("Stopped ticket pool");
        } catch (Exception e) {
            System.out.println("Error stopping Ticket Pool: " + e.getMessage());
            e.getMessage();
        }
    }

    

    // Application status endpoint
    @GetMapping("/status")
    public String getApplicationStatus() {
        return "Application is running successfully!";
    }

    @PostMapping("/saveConfig")
    public ResponseEntity<String> saveConfiguration(@RequestBody Map<String, Integer> config) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String path = "../../resources";

            File file = new File(path);
            if (file.exists()) {
                System.out.println("File exists at: " + file.getAbsolutePath());
            } else {
                System.out.println("File does not exist!");
            }
            
            // Write the configuration to file
            mapper.writeValue(file, config);
            
            // Update the AppConfig instance
            appConfig.setTotalTickets(config.get("totalTickets"));
            appConfig.setMaxTicketsPoolCapacity(config.get("maxTicketPoolCapacity"));
            appConfig.setVendorSellingQuantity(config.get("VendorSellingQuantity"));
            appConfig.setCustomerRetrievalRate(config.get("customerRetrievalRate"));
            appConfig.setTicketReleaseRate(config.get("ticketReleaseRate"));
            appConfig.setCustomerBuyingQuantity(config.get("CustomerBuyingQuantity"));
            
            return ResponseEntity.ok("Configuration saved successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Error saving configuration: " + e.getMessage());
        }
    }

    @GetMapping("/getStats")
    public ResponseEntity<Map<String, Integer>> getStatistics() {
        // Initialize lists if they're null
        if (customersList == null) {
            initializeData();
        }
        
        Map<String, Integer> stats = new HashMap<>();
        stats.put("totalTickets", appConfig.getTotalTickets());
        stats.put("currentPoolSize", appConfig.getMaxTicketsPoolCapacity());
        
        // Safe check for lists
        int totalOperators = 0;
        if (customersList != null && vendorsList != null) {
            totalOperators = customersList.size() + vendorsList.size();
        }
        stats.put("operators", totalOperators);
        
        return ResponseEntity.ok(stats);
    }

    // Update these values in your ticket pool operations
    public void updatePoolSize(int size) {
        currentPoolSize = size;
    }
}

