package org.example.ticketingapplication.repository;

import org.example.ticketingapplication.model.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 - Interface for Vendor entity

 */

public interface VendorRepository extends JpaRepository<Vendor, String> {
}
