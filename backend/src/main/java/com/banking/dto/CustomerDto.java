package com.banking.dto;

import com.banking.entity.Customer;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class CustomerDto {
    private Long id;
    private String cpf;
    private String name;
    private String email;
    private String phone;
    private String address;
    private BigDecimal balance;
    private Customer.CustomerStatus status;
    private LocalDateTime createdAt;
}