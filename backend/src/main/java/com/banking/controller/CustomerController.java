package com.banking.controller;

import com.banking.dto.CustomerDto;
import com.banking.dto.LoanRequestDto;
import com.banking.dto.LoanDto;
import com.banking.service.CustomerService;
import com.banking.service.LoanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
@Tag(name = "Customer", description = "Customer endpoints")
@CrossOrigin(origins = "http://localhost:4200")
@PreAuthorize("hasRole('CUSTOMER')")
public class CustomerController {
    
    private final CustomerService customerService;
    private final LoanService loanService;
    
    @GetMapping("/profile")
    @Operation(summary = "Get customer profile")
    public ResponseEntity<CustomerDto> getProfile(Authentication authentication) {
        CustomerDto customer = customerService.getCustomerByEmail(authentication.getName());
        return ResponseEntity.ok(customer);
    }
    
    @PostMapping("/loan/request")
    @Operation(summary = "Request a loan")
    public ResponseEntity<LoanDto> requestLoan(@Valid @RequestBody LoanRequestDto dto, 
                                               Authentication authentication) {
        LoanDto loan = loanService.requestLoan(authentication.getName(), dto);
        return ResponseEntity.ok(loan);
    }
    
    @GetMapping("/loans")
    @Operation(summary = "Get customer loans")
    public ResponseEntity<List<LoanDto>> getLoans(Authentication authentication) {
        List<LoanDto> loans = loanService.getCustomerLoans(authentication.getName());
        return ResponseEntity.ok(loans);
    }
}