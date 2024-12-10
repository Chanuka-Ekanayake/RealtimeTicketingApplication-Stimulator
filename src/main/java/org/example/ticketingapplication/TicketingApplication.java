package org.example.ticketingapplication;

import jakarta.annotation.PostConstruct;
import org.example.ticketingapplication.configuration.AppConfig;
import org.example.ticketingapplication.model.*;
import org.example.ticketingapplication.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


@SpringBootApplication
public class TicketingApplication {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private VendorService vendorService;

    @Autowired
    private EventService eventService;

    @Autowired
    private TicketService ticketService;

    @Autowired
    private ConfigService configService;

    @Autowired
    private AppConfig appConfig;

    @Autowired
    private TicketPool ticketPool;


    public static void main(String[] args) {
        SpringApplication.run(TicketingApplication.class, args);

        System.out.println("Hello World!");

    }

    @PostConstruct
    public void testDatabase(){
//        customerService.deleteAllCustomers();
//        vendorService.deleteAllVendors();

    }

    @PostConstruct
    public void init(){
//        Test Config File
//        String fileName = "Configuration.JSON";
//        configService.loadConfigFile(fileName);
//        System.out.println(appConfig.toString());
//        System.out.println("\n\n\nLook Up\n\n\n");

    }

    @PostConstruct
    public void testTicketPool(){

        customerService.createCustomer("Chanux","chanuxBro@gmail.com", true);

    }
}