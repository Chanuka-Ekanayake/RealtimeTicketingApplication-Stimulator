package org.example.ticketingapplication.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.ticketingapplication.model.Customer;
import org.example.ticketingapplication.model.Event;
import org.example.ticketingapplication.model.Ticket;
import org.example.ticketingapplication.model.Vendor;
import org.example.ticketingapplication.repository.CustomerRepository;
import org.example.ticketingapplication.repository.EventRepository;
import org.example.ticketingapplication.repository.TicketRepository;
import org.example.ticketingapplication.repository.VendorRepository;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class InitializerService {
    private final CustomerRepository customerRepository;
    private final VendorRepository vendorRepository;
    private final TicketRepository ticketRepository;
    private final EventRepository eventRepository;


    public InitializerService(CustomerRepository customerRepository, VendorRepository vendorRepository,
                              TicketRepository ticketRepository, EventRepository eventRepository) {
        this.customerRepository = customerRepository;
        this.vendorRepository = vendorRepository;
        this.ticketRepository = ticketRepository;
        this.eventRepository = eventRepository;

    }

    // 1. Input data from the database
    public List<Customer> loadCustomersFromDatabase() {
        List<Customer> customersList = customerRepository.findAll();

        for(Customer customer : customersList){
            for(Ticket ticket : ticketRepository.findAll()){
                if(ticket.getCustomer() != null) {
                    if (ticket.getCustomer().getCustomerId().equals(customer.getCustomerId())) {
                        customer.addTicket(ticket);
                    }
                }
            }
        }
        return customersList;

    }

    public List<Vendor> loadVendorsFromDatabase() {
        List<Vendor> vendorsList = vendorRepository.findAll();

        for(Vendor vendor : vendorsList){
            for(Event event: eventRepository.findAll()){
                for(Ticket ticket: ticketRepository.findAll()){
                    if(ticket.getEvent().getEventId().equals(event.getEventId())){
                        event.addTicket(ticket);
                    }
                }
                if(event.getVendor().getVendorId().equals(vendor.getVendorId())){
                    vendor.addEvent(event);
                }
            }
        }
        return vendorsList;
    }

    public List<Ticket> loadTicketsFromDatabase() {
        return ticketRepository.findAll();
    }

    public List<Event> loadEventsFromDatabase() {
        List<Event> eventList = eventRepository.findAll();

        for(Event event : eventList){
            event.setTicketAvailable(0);
            for(Ticket ticket : ticketRepository.findAll()){
                if(ticket.getEvent().getEventId().equals(event.getEventId())){
                    event.addTicket(ticket);
                }
            }
        }
        return eventList;
    }


    // Input data from a file, save in the database, and return as a list
    public List<Customer> loadCustomersFromFile(String filePath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Customer[] customers = objectMapper.readValue(new File(filePath), Customer[].class);
        List<Customer> customerList = List.of(customers);
        customerRepository.saveAll(customerList);

        customerList = loadCustomersFromDatabase();
        return customerList;
    }

    public List<Vendor> loadVendorsFromFile(String filePath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Vendor[] vendors = objectMapper.readValue(new File(filePath), Vendor[].class);
        List<Vendor> vendorList = List.of(vendors);
        vendorRepository.saveAll(vendorList);

        vendorList = loadVendorsFromDatabase();
        return vendorList;
    }

    public List<Ticket> loadTicketsFromFile(String filePath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Ticket[] tickets = objectMapper.readValue(new File(filePath), Ticket[].class);
        List<Ticket> ticketList = List.of(tickets);
        ticketRepository.saveAll(ticketList);
        return ticketList;
    }

    public List<Event> loadEventsFromFile(String filePath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Event[] events = objectMapper.readValue(new File(filePath), Event[].class);
        List<Event> eventList = List.of(events);
        eventRepository.saveAll(eventList);

        eventList = loadEventsFromDatabase();
        return eventList;
    }
}



