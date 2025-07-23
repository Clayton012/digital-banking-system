package com.banking.controller;

import com.banking.dto.CustomerDto;
import com.banking.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@Tag(name = "Admin", description = "Admin endpoints for managers")
@CrossOrigin(origins = "http://localhost:4200")
@PreAuthorize("hasRole('MANAGER')")
public class AdminController {
    
    private final CustomerService customerService;
    
    @GetMapping("/customers/pending")
    @Operation(summary = "Get pending customers for approval")
    public ResponseEntity<List<CustomerDto>> getPendingCustomers() {
        List<CustomerDto> customers = customerService.getPendingCustomers();
        return ResponseEntity.ok(customers);
    }
    
    @PostMapping("/customers/{customerId}/approve")
    @Operation(summary = "Approve customer registration")
    public ResponseEntity<CustomerDto> approveCustomer(@PathVariable Long customerId) {
        CustomerDto customer = customerService.approveCustomer(customerId);
        return ResponseEntity.ok(customer);
    }
}