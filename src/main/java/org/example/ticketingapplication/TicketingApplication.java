package org.example.ticketingapplication;

import jakarta.annotation.PostConstruct;
import org.example.ticketingapplication.configuration.AppConfig;
import org.example.ticketingapplication.model.*;
import org.example.ticketingapplication.service.*;
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

        TicketPool ticketPool = new TicketPool(5,50);


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

        Customer c1 = customersList.get(0);
        Customer c2 = customersList.get(1);



        c1.ticketBuyingProperties(ticketPool,7,3);
        c2.ticketBuyingProperties(ticketPool,3,2);

        v1.ticketSellingProcess(ticketPool,5,2);
        v2.ticketSellingProcess(ticketPool,10,5);



        threadPoolManager.submitTask(v1);
        threadPoolManager.submitTask(v2);
        threadPoolManager.submitTask(c1);
        threadPoolManager.submitTask(c2);


    }
}