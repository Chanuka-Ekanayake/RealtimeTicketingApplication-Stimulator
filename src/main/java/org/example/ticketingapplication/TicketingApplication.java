package org.example.ticketingapplication;

import jakarta.annotation.PostConstruct;
import org.example.ticketingapplication.configuration.AppConfig;
import org.example.ticketingapplication.model.*;
import org.example.ticketingapplication.service.*;
import org.example.ticketingapplication.util.ThreadPoolManager;
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
    private ThreadPoolManager threadPoolManager;


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

        TicketPool ticketPool = new TicketPool(5,20);

        Customer c1 = customerService.findCustomerById("C-0001");
        Customer c2 = customerService.findCustomerById("C-0002");

        Vendor v1 = vendorService.getVendorById("V-0001");
        Vendor v2 = vendorService.getVendorById("V-0002");

        Event e1 = eventService.getEventById("V-0001-E-0001");
        Event e2 = eventService.getEventById("V-0002-E-0002");

        System.out.println(eventService.getAllEvents());



//        eventService.createEventTickets(e1,BigDecimal.valueOf(4000), LocalDateTime.now());
//        eventService.createEventTickets(e2,BigDecimal.valueOf(4000), LocalDateTime.now());


//        System.out.println(c1);
//        System.out.println(c2);
//        System.out.println(v1);
//        System.out.println(v2);
//
//        System.out.println(e1);
//        System.out.println(e2);

        System.out.println(e1.getTicketAvailable());
        System.out.println(e2.getTicketAvailable());





        c1.ticketBuyingProperties(ticketPool,10,2);
        c2.ticketBuyingProperties(ticketPool,10,2);
//
        v1.ticketSellingProcess(ticketPool,10,2);
        v2.ticketSellingProcess(ticketPool,10,2);
//
//      System.out.println(v1.getTotalTicketsToBeSold());
//        threadPoolManager.submitTask(v1);
//        threadPoolManager.submitTask(v2);
//        threadPoolManager.submitTask(c1);
//        threadPoolManager.submitTask(c2);
//
//        System.out.println(threadPoolManager.getActiveThreadCount());

    }
}