package com.banking.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class LoanRequestDto {
    
    @NotNull(message = "Amount is required")
    @DecimalMin(value = "100.00", message = "Minimum loan amount is R$ 100.00")
    @DecimalMax(value = "10000.00", message = "Maximum loan amount is R$ 10,000.00")
    private BigDecimal amount;
    
    @NotNull(message = "Installments is required")
    @Min(value = 1, message = "Minimum installments is 1")
    @Max(value = 24, message = "Maximum installments is 24")
    private Integer installments;
}