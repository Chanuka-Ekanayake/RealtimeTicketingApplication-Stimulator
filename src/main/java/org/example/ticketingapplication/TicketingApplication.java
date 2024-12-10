package org.example.ticketingapplication;

import jakarta.annotation.PostConstruct;
import org.example.ticketingapplication.configuration.AppConfig;
import org.example.ticketingapplication.model.Customer;
import org.example.ticketingapplication.model.Event;
import org.example.ticketingapplication.model.Ticket;
import org.example.ticketingapplication.model.Vendor;
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


    public static void main(String[] args) {
        SpringApplication.run(TicketingApplication.class, args);

        System.out.println("Hello World!");

    }

    @PostConstruct
    public void testDatabase(){
        customerService.deleteAllCustomers();
        vendorService.deleteAllVendors();

        Vendor vendor = vendorService.createVendor("Vihanga", "vihanga.isu@gmail.com");
        Event event = eventService.createEvent("CodeSprint",LocalDateTime.now(),"IIT","StartupBuisness",500,vendor);
        Ticket ticket = ticketService.createTicket(event,BigDecimal.valueOf(5000),LocalDateTime.now());
        Customer customer = customerService.createCustomer("Chanuka", "chanukasamajith@gmail.com",true);

    }

    @PostConstruct
    public void init(){
        //TEst Config File
        String fileName = "Configuration.JSON";
        configService.loadConfigFile(fileName);
        System.out.println(appConfig.toString());
        System.out.println("\n\n\nLook Up\n\n\n");

    }
}