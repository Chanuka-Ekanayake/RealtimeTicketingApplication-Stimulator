package org.example.ticketingapplication.controller;

/**
 - Expose APIs for CRUD operations and actions

 */





import org.example.ticketingapplication.model.Vendor;
import org.example.ticketingapplication.repository.VendorRepository;
import org.example.ticketingapplication.util.SystemLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vendors")
public class VendorController {

    @Autowired
    private VendorRepository vendorRepository;

    @Autowired
    private SystemLogger systemLogger;

    // Get all vendors
    @GetMapping
    public List<Vendor> getAllVendors() {
        systemLogger.logInfo("Fetching all vendors.");
        return vendorRepository.findAll();
    }

    // Get vendor by ID
    @GetMapping("/{id}")
    public ResponseEntity<Vendor> getVendorById(@PathVariable String id) {
        systemLogger.logInfo("Fetching vendor with ID: " + id);
        return vendorRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Create a new vendor
    @PostMapping
    public Vendor createVendor(@RequestBody Vendor vendor) {
        systemLogger.logInfo("Creating a new vendor: " + vendor.getVendorName());
        return vendorRepository.save(vendor);
    }

    // Update a vendor
    @PutMapping("/{id}")
    public ResponseEntity<Vendor> updateVendor(@PathVariable String id, @RequestBody Vendor updatedVendor) {
        systemLogger.logInfo("Updating vendor with ID: " + id);
        return vendorRepository.findById(id)
                .map(vendor -> {
                    vendor.setVendorName(updatedVendor.getVendorName());
                    vendor.setTotalProfit(updatedVendor.getTotalProfit());
                    return ResponseEntity.ok(vendorRepository.save(vendor));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Delete a vendor
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVendor(@PathVariable String id) {
        systemLogger.logInfo("Deleting vendor with ID: " + id);
        if (vendorRepository.existsById(id)) {
            vendorRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}


