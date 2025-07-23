package com.banking.repository;

import com.banking.entity.Loan;
import com.banking.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {
    List<Loan> findByCustomer(Customer customer);
    Optional<Loan> findByCustomerAndStatus(Customer customer, Loan.LoanStatus status);
    boolean existsByCustomerAndStatusIn(Customer customer, List<Loan.LoanStatus> statuses);
}