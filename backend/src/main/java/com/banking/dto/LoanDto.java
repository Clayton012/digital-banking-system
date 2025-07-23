package com.banking.dto;

import com.banking.entity.Loan;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class LoanDto {
    private Long id;
    private BigDecimal amount;
    private Integer installments;
    private BigDecimal interestRate;
    private BigDecimal totalAmount;
    private BigDecimal monthlyPayment;
    private Loan.LoanStatus status;
    private LocalDateTime createdAt;
}