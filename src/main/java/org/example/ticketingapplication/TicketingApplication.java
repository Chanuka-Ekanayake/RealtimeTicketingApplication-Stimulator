package org.example.ticketingapplication;

import jakarta.annotation.PostConstruct;
import org.example.ticketingapplication.configuration.AppConfig;
import org.example.ticketingapplication.model.*;
import org.example.ticketingapplication.service.*;
import org.example.ticketingapplication.util.SystemLogger;
import org.example.ticketingapplication.util.ThreadPoolManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



import java.util.ArrayList;
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

    @Autowired
    private InitializerService initializerService;

    @Autowired
    private AppConfig aConfig;
    @Autowired
    private SystemLogger systemLogger;


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

//        ConfigService configService = new ConfigService(appConfig);
//        configService.loadConfigFile();
////
//        TicketPool ticketPool = new TicketPool(appConfig.getMaxTicketsPoolCapacity(),appConfig.getTotalTickets());
////
//        List<Customer> customersList = initializerService.loadCustomersFromDatabase();
//        List<Vendor> vendorsList = initializerService.loadVendorsFromDatabase();
//        List<Event> eventsList = initializerService.loadEventsFromDatabase();
//        List<Ticket> ticketsList = initializerService.loadTicketsFromDatabase();
////
////
////
//        Customer c1 = customersList.get(0);
//        Customer c2 = customersList.get(1);
//
//        Vendor v1 = vendorsList.get(0);
//        Vendor v2 = vendorsList.get(1);
//
//
//        c1.ticketBuyingProperties(ticketPool,7,appConfig.getCustomerRetrievalRate());
//        c2.ticketBuyingProperties(ticketPool,6,appConfig.getCustomerRetrievalRate());
//
//        v1.ticketSellingProcess(ticketPool,5,appConfig.getTicketReleaseRate());
//        v2.ticketSellingProcess(ticketPool,10,appConfig.getTicketReleaseRate());
//
//
//
//        threadPoolManager.submitTask(v1);
//        threadPoolManager.submitTask(v2);
//
//        //VIP Customers here
//        threadPoolManager.submitTask(c1);
//
//        threadPoolManager.shutdown();
//        threadPoolManager.waitForCompletion();
//        threadPoolManager.reinitialize();
//
//        //Non-VIP Customers Here
//        threadPoolManager.submitTask(c2);
//
//        threadPoolManager.shutdown();
////        threadPoolManager.initializeNewPool();
//
//        systemLogger.logInfo("Ticket pool round Completed!");
//
    }


}