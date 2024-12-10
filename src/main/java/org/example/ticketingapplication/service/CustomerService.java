package org.example.ticketingapplication.service;

import org.example.ticketingapplication.model.Customer;
import org.example.ticketingapplication.model.Ticket;
import org.example.ticketingapplication.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 - Implements customer-related operations.

 */

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public void addCustomer(Customer customer) {
        customerRepository.save(customer);
    }

    public Customer findCustomerById(String id) {
        return customerRepository.findById(id).orElseThrow(() -> new RuntimeException("Customer with id " + id + " not found"));
    }

    public List<Customer> findAllCustomers() {
        return customerRepository.findAll();
    }

    public void deleteCustomer(String id) {
        customerRepository.deleteById(id);
        for(Ticket ticket : findCustomerById(id).getTickets()) {
            ticket.setCustomer(null);
        }
    }

    public void deleteAllCustomers() {
        customerRepository.deleteAll();
        for(Customer customer : customerRepository.findAll() ) {
            for(Ticket ticket : customer.getTickets()) {
                ticket.setCustomer(null);
            }
        }
    }

    public Customer createCustomer(String CustomerName, String Email, Boolean isVIP) {
        Customer customer = new Customer(CustomerName, Email, isVIP);
        customer.createCustomerID(customerRepository.findAll());
        return customerRepository.save(customer);
    }

}
