package com.banking.service;

import com.banking.dto.LoanRequestDto;
import com.banking.dto.LoanDto;
import com.banking.entity.Loan;
import com.banking.entity.Customer;
import com.banking.exception.LoanNotAllowedException;
import com.banking.exception.CustomerNotFoundException;
import com.banking.repository.LoanRepository;
import com.banking.repository.CustomerRepository;
import com.banking.messaging.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LoanService {
    
    private final LoanRepository loanRepository;
    private final CustomerRepository customerRepository;
    private final NotificationService notificationService;
    
    private static final BigDecimal MAX_LOAN_AMOUNT = new BigDecimal("10000.00");
    private static final Integer MAX_INSTALLMENTS = 24;
    private static final BigDecimal INTEREST_RATE = new BigDecimal("0.01");
    
    @Transactional
    public LoanDto requestLoan(String customerEmail, LoanRequestDto dto) {
        Customer customer = customerRepository.findByEmail(customerEmail)
            .orElseThrow(() -> new CustomerNotFoundException("Customer not found"));
        
        // Validate business rules
        validateLoanRequest(customer, dto);
        
        // Create loan
        Loan loan = new Loan();
        loan.setCustomer(customer);
        loan.setAmount(dto.getAmount());
        loan.setInstallments(dto.getInstallments());
        loan.setInterestRate(INTEREST_RATE);
        
        // Calculate total amount and monthly payment
        BigDecimal totalAmount = calculateTotalAmount(dto.getAmount(), dto.getInstallments());
        BigDecimal monthlyPayment = totalAmount.divide(new BigDecimal(dto.getInstallments()), 2, RoundingMode.HALF_UP);
        
        loan.setTotalAmount(totalAmount);
        loan.setMonthlyPayment(monthlyPayment);
        
        Loan saved = loanRepository.save(loan);
        
        // Send notification
        notificationService.notifyLoanRequest(saved);
        
        return convertToDto(saved);
    }
    
    public List<LoanDto> getCustomerLoans(String customerEmail) {
        Customer customer = customerRepository.findByEmail(customerEmail)
            .orElseThrow(() -> new CustomerNotFoundException("Customer not found"));
        
        return loanRepository.findByCustomer(customer)
            .stream()
            .map(this::convertToDto)
            .collect(Collectors.toList());
    }
    
    private void validateLoanRequest(Customer customer, LoanRequestDto dto) {
        // Check if customer is approved
        if (customer.getStatus() != Customer.CustomerStatus.APPROVED) {
            throw new LoanNotAllowedException("Customer must be approved to request a loan");
        }
        
        // Check loan amount limit
        if (dto.getAmount().compareTo(MAX_LOAN_AMOUNT) > 0) {
            throw new LoanNotAllowedException("Loan amount exceeds maximum limit of R$ 10,000.00");
        }
        
        // Check installments limit
        if (dto.getInstallments() > MAX_INSTALLMENTS) {
            throw new LoanNotAllowedException("Maximum installments allowed is 24");
        }
        
        // Check if customer already has a loan
        List<Loan.LoanStatus> activeStatuses = Arrays.asList(Loan.LoanStatus.PENDING, Loan.LoanStatus.APPROVED);
        if (loanRepository.existsByCustomerAndStatusIn(customer, activeStatuses)) {
            throw new LoanNotAllowedException("Customer can only have one loan at a time");
        }
    }
    
    private BigDecimal calculateTotalAmount(BigDecimal amount, Integer installments) {
        // Simple interest calculation: Amount * (1 + interest_rate * installments)
        BigDecimal interestMultiplier = BigDecimal.ONE.add(INTEREST_RATE.multiply(new BigDecimal(installments)));
        return amount.multiply(interestMultiplier).setScale(2, RoundingMode.HALF_UP);
    }
    
    private LoanDto convertToDto(Loan loan) {
        LoanDto dto = new LoanDto();
        dto.setId(loan.getId());
        dto.setAmount(loan.getAmount());
        dto.setInstallments(loan.getInstallments());
        dto.setInterestRate(loan.getInterestRate());
        dto.setTotalAmount(loan.getTotalAmount());
        dto.setMonthlyPayment(loan.getMonthlyPayment());
        dto.setStatus(loan.getStatus());
        dto.setCreatedAt(loan.getCreatedAt());
        return dto;
    }
}