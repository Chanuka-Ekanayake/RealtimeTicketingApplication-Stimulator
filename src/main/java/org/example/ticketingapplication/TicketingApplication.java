package org.example.ticketingapplication;

import jakarta.annotation.PostConstruct;
import org.example.ticketingapplication.UI.CommandLineInterface;
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
import java.util.Scanner;


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

        System.out.println("Ticketing Application Started....");
        Scanner scanner = new Scanner(System.in);

        while(true) {
            System.out.print("Please enter \"0\" to start the Command Line Interface: ");
            String choice = scanner.nextLine();

            if (choice.equals("0")) {
                break;
            }
        }


        CommandLineInterface.mainMenu();
    }

}