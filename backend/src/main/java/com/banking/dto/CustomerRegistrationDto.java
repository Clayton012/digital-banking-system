package com.banking.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CustomerRegistrationDto {
    
    @NotBlank(message = "CPF is required")
    @Pattern(regexp = "\d{11}", message = "CPF must have 11 digits")
    private String cpf;
    
    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String name;
    
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;
    
    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;
    
    private String phone;
    private String address;
}