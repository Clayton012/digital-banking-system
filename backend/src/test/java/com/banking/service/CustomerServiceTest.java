package com.banking.service;

import com.banking.dto.CustomerRegistrationDto;
import com.banking.dto.CustomerDto;
import com.banking.entity.Customer;
import com.banking.repository.CustomerRepository;
import com.banking.messaging.NotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;
    
    @Mock
    private PasswordEncoder passwordEncoder;
    
    @Mock
    private NotificationService notificationService;
    
    @InjectMocks
    private CustomerService customerService;
    
    private CustomerRegistrationDto registrationDto;
    private Customer customer;
    
    @BeforeEach
    void setUp() {
        registrationDto = new CustomerRegistrationDto();
        registrationDto.setCpf("12345678901");
        registrationDto.setName("John Doe");
        registrationDto.setEmail("john@example.com");
        registrationDto.setPassword("password123");
        registrationDto.setPhone("11999999999");
        registrationDto.setAddress("123 Main St");
        
        customer = new Customer();
        customer.setId(1L);
        customer.setCpf("12345678901");
        customer.setName("John Doe");
        customer.setEmail("john@example.com");
        customer.setPassword("encodedPassword");
        customer.setPhone("11999999999");
        customer.setAddress("123 Main St");
        customer.setBalance(BigDecimal.ZERO);
        customer.setStatus(Customer.CustomerStatus.PENDING);
    }
    
    @Test
    void registerCustomer_Success() {
        when(customerRepository.existsByCpf(anyString())).thenReturn(false);
        when(customerRepository.existsByEmail(anyString())).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);
        
        CustomerDto result = customerService.registerCustomer(registrationDto);
        
        assertNotNull(result);
        assertEquals("John Doe", result.getName());
        assertEquals("john@example.com", result.getEmail());
        verify(notificationService).notifyNewCustomerRegistration(any(Customer.class));
    }
    
    @Test
    void approveCustomer_Success() {
        customer.setStatus(Customer.CustomerStatus.PENDING);
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);
        
        CustomerDto result = customerService.approveCustomer(1L);
        
        assertNotNull(result);
        verify(customerRepository).save(any(Customer.class));
        verify(notificationService).notifyCustomerApproval(any(Customer.class));
    }
}