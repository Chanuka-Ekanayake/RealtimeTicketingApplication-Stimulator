package org.example.ticketingapplication.controller;

/**
 - Expose APIs for CRUD operations and actions

 */


import org.example.ticketingapplication.model.Customer;
import org.example.ticketingapplication.model.Vendor;
import org.example.ticketingapplication.repository.CustomerRepository;
import org.example.ticketingapplication.repository.VendorRepository;
import org.example.ticketingapplication.service.VendorCustomerManager;
import org.example.ticketingapplication.util.SystemLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private SystemLogger systemLogger;

    @Autowired
    private VendorCustomerManager vendorCustomerManager;
    @Autowired
    private VendorRepository vendorRepository;

    // Get all customers
    @GetMapping
    public List<Customer> getAllCustomers() {
        systemLogger.logInfo("Fetching all customers.");
        return customerRepository.findAll();
    }

    // Get customer by ID
    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable String id) {
        systemLogger.logInfo("Fetching customer with ID: " + id);
        return customerRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Create a new customer
    @PostMapping
    public Customer createCustomer(@RequestBody Customer customer) {
        systemLogger.logInfo("Creating a new customer: " + customer.getCustomerName());
        return customerRepository.save(customer);
    }

    // Update a customer
    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable String id, @RequestBody Customer updatedCustomer) {
        systemLogger.logInfo("Updating customer with ID: " + id);
        return customerRepository.findById(id)
                .map(customer -> {
                    customer.setCustomerName(updatedCustomer.getCustomerName());
                    customer.setCustomerEmail(updatedCustomer.getCustomerEmail());
                    customer.setVIP(updatedCustomer.isVIP());
                    return ResponseEntity.ok(customerRepository.save(customer));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Delete a customer
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable String id) {
        systemLogger.logInfo("Deleting customer with ID: " + id);
        if (customerRepository.existsById(id)) {
            customerRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/addDynamicCustomer")
    public Customer addDynamicCustomer(@RequestBody String CustomerName, String CustomerEmail) {
        Customer customer = vendorCustomerManager.addCustomer(CustomerName,CustomerEmail,false);
        return customerRepository.save(customer);
    }

    // Change this method's mapping to a different endpoint
    @DeleteMapping("/removeDynamicCustomer")
    public void deleteDynamicCustomer(@RequestBody String CustomerName, String CustomerEmail) {

        for(Customer customer : customerRepository.findAll()) {
            if(customer.getCustomerEmail().equals(CustomerEmail) && customer.getCustomerName().equals(CustomerName)) {
                customerRepository.delete(customer);
                break;
            }
        }
    }
}

