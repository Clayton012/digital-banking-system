package com.banking.repository;

import com.banking.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByCpf(String cpf);
    Optional<Customer> findByEmail(String email);
    List<Customer> findByStatus(Customer.CustomerStatus status);
    boolean existsByCpf(String cpf);
    boolean existsByEmail(String email);
}