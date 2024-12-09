package org.example.ticketingapplication.repository;

import org.example.ticketingapplication.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 - Interface for CustomerDetails entity

 */

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
