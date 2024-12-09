package org.example.ticketingapplication.service;

import org.example.ticketingapplication.model.Vendor;
import org.example.ticketingapplication.repository.TicketRepository;
import org.example.ticketingapplication.repository.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 - Handles vendor operations.

 */

@Service
public class VendorService {

    private final VendorRepository vendorRepository;

    @Autowired
    public VendorService(VendorRepository vendorRepository, TicketRepository ticketRepository) {
        this.vendorRepository = vendorRepository;
    }

    public List<Vendor> getAllVendors() {
        return vendorRepository.findAll();
    }

    public Vendor getVendorById(Long id) {
        return vendorRepository.findById(id).orElseThrow( () -> new RuntimeException("Vendor with id " + id + " not found!"));
    }

    public Vendor saveVendor(Vendor vendor) {
        return vendorRepository.save(vendor);
    }

    public void addVendor(Vendor vendor) {
        vendorRepository.save(vendor);
    }

    public void deleteVendorById(Long id) {
        vendorRepository.deleteById(id);
    }

    public void deleteAllVendors() {
        vendorRepository.deleteAll();
    }

}
