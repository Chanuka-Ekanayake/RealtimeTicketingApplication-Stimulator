package org.example.ticketingapplication.service;

import org.example.ticketingapplication.model.Event;
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
    private final EventService eventService;

    @Autowired
    public VendorService(VendorRepository vendorRepository, TicketRepository ticketRepository, EventService eventService) {
        this.vendorRepository = vendorRepository;
        this.eventService = eventService;
    }

    public List<Vendor> getAllVendors() {
        return vendorRepository.findAll();
    }

    public Vendor getVendorById(String id) {
        return vendorRepository.findById(id).orElseThrow( () -> new RuntimeException("Vendor with id " + id + " not found!"));
    }

    public Vendor saveVendor(Vendor vendor) {
        return vendorRepository.save(vendor);
    }

    public void addVendor(Vendor vendor) {
        vendorRepository.save(vendor);
    }

    public void deleteVendorById(String id) {
        vendorRepository.deleteById(id);
        for(Event event : getVendorById(id).getEventsList()) {
            eventService.deleteEvent(event.getEventId());
        }
    }

    public void deleteAllVendors() {
        eventService.deleteAllEvents();
        vendorRepository.deleteAll();
    }

    public Vendor createVendor(String vendorName, String vendorEmail) {
        Vendor vendor = new Vendor(vendorName, vendorEmail);
        vendor.createVendorID(vendorRepository.findAll());
        return vendorRepository.save(vendor);
    }
}
