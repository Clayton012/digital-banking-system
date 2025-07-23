package com.banking.controller;

import com.banking.dto.CustomerRegistrationDto;
import com.banking.dto.LoginDto;
import com.banking.dto.JwtResponse;
import com.banking.dto.CustomerDto;
import com.banking.service.CustomerService;
import com.banking.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Authentication endpoints")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {
    
    private final CustomerService customerService;
    private final AuthService authService;
    
    @PostMapping("/register")
    @Operation(summary = "Register new customer")
    public ResponseEntity<CustomerDto> register(@Valid @RequestBody CustomerRegistrationDto dto) {
        CustomerDto customer = customerService.registerCustomer(dto);
        return ResponseEntity.ok(customer);
    }
    
    @PostMapping("/login")
    @Operation(summary = "Login customer")
    public ResponseEntity<JwtResponse> login(@Valid @RequestBody LoginDto dto) {
        JwtResponse response = authService.login(dto);
        return ResponseEntity.ok(response);
    }
}