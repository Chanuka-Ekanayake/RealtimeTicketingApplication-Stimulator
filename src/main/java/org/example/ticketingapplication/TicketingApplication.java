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

//        Customer c1 = customerService.createCustomer("Chanuka", "chanukasamajith@gmail.com",true);
//        Customer c2 = customerService.createCustomer("Vihanga", "vihanga@gmail.com",false);
//
//        Vendor v1 = vendorService.createVendor("Kulitha", "kulitha@gmail.com");
//        Vendor v2 = vendorService.createVendor("Vihan", "vihan@gmail.com");
//
//        Event e1 = eventService.createEvent("CodeSprint",LocalDateTime.now(),"IIT","Hackathon",100,v1);
//        Event e2 = eventService.createEvent("IX",LocalDateTime.now(),"Hatch","Designathon",50,v2);
//
//        eventService.createEventTickets(e1,BigDecimal.valueOf(5000),LocalDateTime.now());
//        eventService.createEventTickets(e2,BigDecimal.valueOf(3000),LocalDateTime.now());


        TicketPool ticketPool = new TicketPool(5,20);
//
//        Customer c1 = customerService.findCustomerById("C-0001");
//        Customer c2 = customerService.findCustomerById("C-0002");
//
//        Vendor v1 = vendorService.getVendorById("V-0001");
//        Vendor v2 = vendorService.getVendorById("V-0002");
//
//        Event e1 = eventService.getEventById("E-0001");
//        Event e2 = eventService.getEventById("E-0002");
//
//        System.out.println(eventService.getAllEvents());

//        e1.setTicketAvailable(0);
//        e2.setTicketAvailable(0);

//        e1.setTickets(ticketService.getAllTickets());



//        eventService.createEventTickets(e1,BigDecimal.valueOf(4000), LocalDateTime.now());
//        eventService.createEventTickets(e2,BigDecimal.valueOf(4000), LocalDateTime.now());


//        System.out.println(c1);
//        System.out.println(c2);
//        System.out.println(v1);
//        System.out.println(v2);
////
//        System.out.println(e1);
//        System.out.println(e2);

        List<Vendor> vendorsList = new ArrayList<>();
        List<Event> eventsList = new ArrayList<>();
        List<Customer> customersList = new ArrayList<>();
        List<Ticket> ticketsList = new ArrayList<>();

        for(Vendor vendor : vendorService.getAllVendors()){
            vendorsList.add(vendor);
        }

        for(Event event : eventService.getAllEvents()){
            eventsList.add(event);
        }

        for(Customer customer : customerService.findAllCustomers()){
            customersList.add(customer);
        }

        for(Ticket ticket : ticketService.getAllTickets()){
            ticketsList.add(ticket);
        }

        for(Vendor vendor : vendorsList){
            for(Event event: eventsList){
                for(Ticket ticket: ticketsList){
                    if(ticket.getEvent().getEventId().equals(event.getEventId())){
                        event.addTicket(ticket);
                    }
                }
                if(event.getVendor().getVendorId().equals(vendor.getVendorId())){
                    vendor.addEvent(event);
                }
            }
        }

        for(Customer customer : customersList){
            for(Ticket ticket : ticketsList){
                if(ticket.getCustomer() != null) {
                    if (ticket.getCustomer().getCustomerId().equals(customer.getCustomerId())) {
                        customer.addTicket(ticket);
                    }
                }
            }
        }

        for(Vendor vendor : vendorsList){
            System.out.println(vendor.getEventsList());
        }

        Vendor v1 = vendorsList.get(0);
        Vendor v2 = vendorsList.get(1);

        Event e1 = eventsList.get(0);
        Event e2 = eventsList.get(1);

        Customer c1 = customersList.get(0);
        Customer c2 = customersList.get(1);

        Ticket t1 = ticketsList.get(0);
        Ticket t2 = ticketsList.get(1);



//        System.out.println(vendorService.getVendorById("V-0001").getEventsList());




//        System.out.println(e1.getTickets().size());
//        System.out.println(e2.getTickets().size());
//        System.out.println(e1.getTicketAvailable());
//        System.out.println(e2.getTicketAvailable());

//        for(Customer customer : customerService.findAllCustomers()){
//            if(customer == c1){
//                System.out.println("Customer found");
//            } else{
//                System.out.println("Customer not found");
//            }
//        }

//
//
//
//
//
//
        c1.ticketBuyingProperties(ticketPool,10,2);
        c2.ticketBuyingProperties(ticketPool,10,2);
////
        v1.ticketSellingProcess(ticketPool,10,2);
        v2.ticketSellingProcess(ticketPool,10,2);
//
//        System.out.println(vendorService.getVendorById(v1.getVendorId()).getTotalTicketsToBeSold());
////
        threadPoolManager.submitTask(v1);
        threadPoolManager.submitTask(v2);
        threadPoolManager.submitTask(c1);
        threadPoolManager.submitTask(c2);
//
//        System.out.println(threadPoolManager.getActiveThreadCount());

    }
}