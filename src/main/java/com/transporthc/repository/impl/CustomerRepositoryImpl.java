package com.transporthc.repository.impl;

import com.transporthc.entity.Customer;
import com.transporthc.entity.QCustomer;
import com.transporthc.repository.CustomerRepository;
import jakarta.persistence.EntityManager;
import org.springframework.context.annotation.Primary;

import java.util.Optional;
@Primary
public class CustomerRepositoryImpl extends BaseRepositoryImpl<Customer,Integer> implements CustomerRepository {
    final QCustomer customer=QCustomer.customer;

    public CustomerRepositoryImpl(EntityManager em) {
        super(Customer.class, em);
    }

    @Override
    public boolean existsByCustomerName(String customerName) {
        return query.selectOne()
                .from(customer)
                .where(customer.customerName.eq(customerName))
                .fetchOne() !=null;
    }

    @Override
    public boolean existsByEmail(String email) {
        return query.selectOne()
                .from(customer)
                .where(customer.email.eq(email))
                .fetchOne() !=null;
    }

    @Override
    public Optional<Customer> findByCustomerId(Integer customerId) {
        return Optional.ofNullable(query.selectFrom(customer)
                .where(customer.customerId.eq(customerId))
                .fetchOne());
    }

    @Override
    public void deleteByCustomerId(Integer customerId) {
        query.delete(customer)
                .where(customer.customerId.eq(customerId))
                .execute();
    }

    @Override
    public Optional<Customer> findByCustomerName(String customerName) {
        return Optional.ofNullable(query.selectFrom(customer)
                .where(customer.customerName.eq(customerName))
                .fetchOne());
    }

    @Override
    public Optional<Customer> findByEmail(String email) {
        return Optional.ofNullable(query.selectFrom(customer)
                .where(customer.email.eq(email))
                .fetchOne());
    }
}
