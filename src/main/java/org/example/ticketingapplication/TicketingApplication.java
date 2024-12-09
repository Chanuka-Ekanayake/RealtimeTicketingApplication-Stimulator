package org.example.ticketingapplication;

import jakarta.annotation.PostConstruct;
import org.example.ticketingapplication.model.Customer;
import org.example.ticketingapplication.model.Event;
import org.example.ticketingapplication.model.Ticket;
import org.example.ticketingapplication.model.Vendor;
import org.example.ticketingapplication.service.CustomerService;
import org.example.ticketingapplication.service.EventService;
import org.example.ticketingapplication.service.TicketService;
import org.example.ticketingapplication.service.VendorService;
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


    public static void main(String[] args) {
        SpringApplication.run(TicketingApplication.class, args);

        System.out.println("Hello World!");

    }

    @PostConstruct
    public void testDatabase(){

        ticketService.deleteAllTickets();
        customerService.deleteAllCustomers();
        eventService.deleteAllEvents();
        vendorService.deleteAllVendors();


        Customer customer = new Customer("Chanuka", "chanukasamajith@gmail.com",true);
        customerService.addCustomer(customer);

        Vendor vendor = new Vendor("Vihanga","vihanga.isu@gmail.com");
        vendorService.addVendor(vendor);

        Event event = new Event("CodeSprint", LocalDateTime.now(),"IIT","StartupBattle",1000,vendor);
        event.createEventID(eventService.getAllEvents());
        eventService.addEvent(event);

        Ticket ticket = new Ticket(event, BigDecimal.valueOf(5000),LocalDateTime.now());
        ticket.createTicketID(ticketService.getAllTickets());
        ticketService.addTicket(ticket);

    }
}