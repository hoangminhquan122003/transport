package com.transporthc.repository;

import com.transporthc.entity.Cargo;
import com.transporthc.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends BaseRepository<Customer,Integer> {
    boolean existsByCustomerName(String customerName);

    boolean existsByEmail(String email);

    Optional<Customer> findByCustomerId(Integer customerId);

    void deleteByCustomerId(Integer customerId);

    Optional<Customer> findByCustomerName(String customerName);

    Optional<Customer> findByEmail(String email);

}
