package org.example.ticketingapplication;

import jakarta.annotation.PostConstruct;
import org.example.ticketingapplication.model.Customer;
import org.example.ticketingapplication.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import java.util.List;


@SpringBootApplication
public class TicketingApplication {

    @Autowired
    private CustomerService customerService;


    public static void main(String[] args) {
        SpringApplication.run(TicketingApplication.class, args);

        System.out.println("Hello World!");

    }

    @PostConstruct
    public void testDatabase(){

        customerService.deleteAllCustomers();
    }
}