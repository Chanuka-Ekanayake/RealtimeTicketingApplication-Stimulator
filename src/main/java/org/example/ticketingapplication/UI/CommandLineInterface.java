package org.example.ticketingapplication.UI;


import org.example.ticketingapplication.configuration.AppConfig;
import org.example.ticketingapplication.controller.ApplicationController;
import org.example.ticketingapplication.controller.CustomerController;
import org.example.ticketingapplication.controller.VendorController;
import org.example.ticketingapplication.model.Customer;
import org.example.ticketingapplication.model.Vendor;

import java.util.Scanner;

public class CommandLineInterface {


    public static void mainMenu() {
        System.out.println("---------- Welcome to Real-Time Ticketing Stimulator ----------\n");


        while (true) {
            System.out.println("--- Main Menu ---\n\n");

            System.out.println("1. Start Ticketing Stimulator");
            System.out.println("2. Show System info");
            System.out.println("3. Configure System");
            System.out.println("4. Exit");

            int choice = integerInput("Enter your choice: ");

            switch (choice) {
                case 1:
                    startSystem();
                    break;
                case 2:
                    showSystemInfo();
                    break;
                case 3:
                    configureSystem();
                    break;
                case 4:
                    System.exit(0);
                default:
                    System.out.println("Invalid choice");
            }
        }

    }

    public static void startSystem() {
        System.out.println("\n--- Real-Time Ticketing Stimulator ---\n");
        ApplicationController.startTicketPool();
        
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Enter 0 to stop the simulator:");
            String input = scanner.nextLine();
            if (input.equals("0")) {
                ApplicationController.stopTicketPool();
                break;
            }
        }
    }

    public static void showSystemInfo(){
        System.out.println("\n--- System Information ---\n");

        int totalCustomers;
        int totalVendors;

        try {
            totalCustomers = ApplicationController.customersList.size();
            totalVendors = ApplicationController.vendorsList.size();
        }catch (NullPointerException e){
            totalCustomers = 0;
            totalVendors = 0;
        }

        System.out.println("Total Number of Customers: "+ totalCustomers);
        System.out.println("Total Number of Vendors: "+ totalVendors);
        System.out.println("Total Number of Tickets on sale: "+ ApplicationController.appConfig.getTotalTickets());
        System.out.println("Maximum TicketPool Capacity: "+ ApplicationController.appConfig.getMaxTicketsPoolCapacity());
        System.out.println("Customer Ticket Buying Rate: "+ Math.round((float) ApplicationController.appConfig.getCustomerRetrievalRate()/ApplicationController.appConfig.getTicketReleaseRate()) + " tickets per second");
        System.out.println("Customer Ticket Buying Rate: "+ Math.round((float) ApplicationController.appConfig.getVendorSellingQuantity()  /ApplicationController.appConfig.getTicketReleaseRate()) + " tickets per second\n");

        int exitCode = integerInput("Enter any number to exit: ");
    }

    public static void configureSystem(){
        System.out.println("\n--- System Configuration ---\n");

        System.out.println("1. Add Customer");
        System.out.println("2. Add Vendor");
        System.out.println("3. Remove Customer");
        System.out.println("4. Remove Vendor");
        System.out.println("5. Configure TicketPool");

        while(true) {
            int choice = integerInput("Enter your choice: ");

            switch (choice) {
                case 1:
                    addCustomer();
                    break;
                case 2:
                    addVendor();
                    break;
                case 3:
                    removeCustomer();
                    break;
                case 4:
                    removeVendor();
                    break;
                case 5:
                    configTicketPool();
                    break;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }




    //Input validation
    public static int integerInput(String prompt) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();
            if (input.isEmpty()) {
                System.out.println("Input cannot be empty. Please try again.");
            } else{
                try{
                    return Integer.parseInt(input);
                } catch (NumberFormatException e){
                    System.out.println("Input is not an integer. Please try again.");
                    scanner.nextLine();
                } catch (Exception e){
                    System.out.println("Invalid Input. Please try again. \n" + e.getMessage());
                    scanner.nextLine();
                }
            }
        }
    }

    public static void addCustomer() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n--- Add Customer ---\n");

            System.out.print("Enter customer Name: ");
            String customerName = scanner.nextLine();

            System.out.println("Enter customer Email: ");
            String customerEmail = scanner.nextLine();


            if (customerName.isEmpty() || customerEmail.isEmpty()) {
                System.out.println("Customer name or email cannot be empty. Please try again.");
            } else {
                Customer customer = new Customer(customerName, customerEmail, false);

                ApplicationController.customersList.add(customer);
                CustomerController customerController = new CustomerController();
                customerController.addDynamicCustomer(customerName,customerEmail);

                System.out.println("Customer added successfully.");
                break;
            }
        }
    }

    public static void addVendor() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n--- Add Vendor ---\n");

            System.out.print("Enter Vendor Name: ");
            String VendorName = scanner.nextLine();

            System.out.println("Enter Vendor Email: ");
            String vendorEmail = scanner.nextLine();


            if (VendorName.isEmpty() || vendorEmail.isEmpty()) {
                System.out.println("Customer name or email cannot be empty. Please try again.");
            } else {

                Vendor vendor = new Vendor(VendorName, vendorEmail);

                ApplicationController.vendorsList.add(vendor);
                VendorController vendorController = new VendorController();
                vendorController.addDynamicVendor(VendorName,vendorEmail);

                System.out.println("Vendor added successfully.");
                break;
            }
        }
    }

    public static void removeCustomer() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n--- Remove Customer ---\n");

            System.out.print("Enter customer Name: ");
            String customerName = scanner.nextLine();
            System.out.print("Enter customer Email: ");
            String customerEmail = scanner.nextLine();

            if (customerName.isEmpty() || customerEmail.isEmpty()) {
                System.out.println("Customer name or email cannot be empty. Please try again.");
                continue;
            }

            boolean found = false;

            for (Customer customer : ApplicationController.customersList) {
                if (customerName.equals(customer.getCustomerName()) && customerEmail.equals(customer.getCustomerEmail())) {
                    found = true;
                    ApplicationController.customersList.remove(customer);
                    CustomerController customerController = new CustomerController();
                    customerController.deleteCustomer(customerName);
                    System.out.println("Customer removed successfully.");
                    break;
                }
            }

            if (!found) {
                System.out.println("Customer Name or Email not found. Please try again.\")");
            }else{
                break;
            }
        }

    }

    public static void removeVendor() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n--- Remove Vendor ---\n");


            System.out.print("Enter Vendor Name: ");
            String VendorName = scanner.nextLine();
            System.out.print("Enter Vendor Email: ");
            String vendorEmail = scanner.nextLine();


            if (VendorName.isEmpty() || vendorEmail.isEmpty()) {
                System.out.println("Customer name or email cannot be empty. Please try again.");
                continue;
            }

            boolean found = false;

            for (Vendor vendorL : ApplicationController.vendorsList) {
                if(vendorL.getVendorName().equals(VendorName) && vendorL.getVendorEmail().equals(vendorEmail)) {
                    found = true;
                    ApplicationController.vendorsList.remove(vendorL);
                    CustomerController customerController = new CustomerController();
                    customerController.deleteCustomer(vendorL.getVendorName());

                    System.out.println("Customer removed successfully." );
                }
            }

            if(!found){
                System.out.println("Vendor Name or Email not found. Please try again.\")");
            }else{
                break;
            }

        }
    }

    public static void configTicketPool() {
        int totalTickets = integerInput("Enter Total Tickets: ");
        int ticketReleaseRate = integerInput("Enter Ticket Release Rate: ");
        int ticketPoolCapacity = integerInput("Enter Ticket Pool Capacity: ");
        int customerRetrievalRate = integerInput("Enter Customer Retrieval Rate: ");
        int vendorSellingRate = integerInput("Enter Vendor Selling Rate: ");

        ApplicationController.appConfig.setMaxTicketsPoolCapacity(totalTickets);
        ApplicationController.appConfig.setTotalTickets(totalTickets);
        ApplicationController.appConfig.setTicketReleaseRate(ticketReleaseRate);
        ApplicationController.appConfig.setCustomerBuyingQuantity(customerRetrievalRate);
        ApplicationController.appConfig.setVendorSellingQuantity(vendorSellingRate);
        ApplicationController.appConfig.setMaxTicketsPoolCapacity(ticketPoolCapacity);

        System.out.println("Ticket Pool configured successfully.!");


    }

}
