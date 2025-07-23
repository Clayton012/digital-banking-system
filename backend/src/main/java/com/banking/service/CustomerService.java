package com.banking.service;

import com.banking.dto.CustomerRegistrationDto;
import com.banking.dto.CustomerDto;
import com.banking.entity.Customer;
import com.banking.exception.CustomerAlreadyExistsException;
import com.banking.exception.CustomerNotFoundException;
import com.banking.repository.CustomerRepository;
import com.banking.messaging.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerService {
    
    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    private final NotificationService notificationService;
    
    @Transactional
    public CustomerDto registerCustomer(CustomerRegistrationDto dto) {
        if (customerRepository.existsByCpf(dto.getCpf())) {
            throw new CustomerAlreadyExistsException("CPF already registered");
        }
        
        if (customerRepository.existsByEmail(dto.getEmail())) {
            throw new CustomerAlreadyExistsException("Email already registered");
        }
        
        Customer customer = new Customer();
        customer.setCpf(dto.getCpf());
        customer.setName(dto.getName());
        customer.setEmail(dto.getEmail());
        customer.setPassword(passwordEncoder.encode(dto.getPassword()));
        customer.setPhone(dto.getPhone());
        customer.setAddress(dto.getAddress());
        
        Customer saved = customerRepository.save(customer);
        
        // Send notification to managers
        notificationService.notifyNewCustomerRegistration(saved);
        
        return convertToDto(saved);
    }
    
    @Transactional
    public CustomerDto approveCustomer(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
            .orElseThrow(() -> new CustomerNotFoundException("Customer not found"));
        
        customer.setStatus(Customer.CustomerStatus.APPROVED);
        Customer saved = customerRepository.save(customer);
        
        // Send approval notification
        notificationService.notifyCustomerApproval(saved);
        
        return convertToDto(saved);
    }
    
    public List<CustomerDto> getPendingCustomers() {
        return customerRepository.findByStatus(Customer.CustomerStatus.PENDING)
            .stream()
            .map(this::convertToDto)
            .collect(Collectors.toList());
    }
    
    public CustomerDto getCustomerByEmail(String email) {
        Customer customer = customerRepository.findByEmail(email)
            .orElseThrow(() -> new CustomerNotFoundException("Customer not found"));
        return convertToDto(customer);
    }
    
    private CustomerDto convertToDto(Customer customer) {
        CustomerDto dto = new CustomerDto();
        dto.setId(customer.getId());
        dto.setCpf(customer.getCpf());
        dto.setName(customer.getName());
        dto.setEmail(customer.getEmail());
        dto.setPhone(customer.getPhone());
        dto.setAddress(customer.getAddress());
        dto.setBalance(customer.getBalance());
        dto.setStatus(customer.getStatus());
        dto.setCreatedAt(customer.getCreatedAt());
        return dto;
    }
}