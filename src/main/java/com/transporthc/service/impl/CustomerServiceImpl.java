package com.transporthc.service.impl;

import com.transporthc.dto.request.CustomerRequest;
import com.transporthc.dto.request.UpdateCustomerRequest;
import com.transporthc.dto.response.CustomerResponse;
import com.transporthc.entity.Customer;
import com.transporthc.exception.AppException;
import com.transporthc.repository.CustomerRepository;
import com.transporthc.service.CustomerService;
import com.transporthc.utils.ErrorCode;
import com.transporthc.utils.Role;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class CustomerServiceImpl implements CustomerService {
    PasswordEncoder passwordEncoder;
    CustomerRepository customerRepository;
    @Override
    public CustomerResponse createCustomer (CustomerRequest customerRequest){
        validateCustomerRequest(customerRequest);
        Customer customer=Customer.builder()
                .customerName(customerRequest.getCustomerName())
                .customerAddress(customerRequest.getCustomerAddress())
                .email(customerRequest.getEmail())
                .password(passwordEncoder.encode(customerRequest.getPassword()))
                .role(Role.CUSTOMER.name())
                .build();
        customerRepository.save(customer);
        log.info("Creating new customer with name: {}", customerRequest.getCustomerName());
        return CustomerResponse.builder()
                .customerId(customer.getCustomerId())
                .customerName(customer.getCustomerName())
                .customerAddress(customer.getCustomerAddress())
                .email(customer.getEmail())
                .role(customer.getRole())
                .build();
    }

    private void validateCustomerRequest(CustomerRequest customerRequest) {
        if (customerRepository.existsByCustomerName(customerRequest.getCustomerName())) {
            log.error("customer name existed:{}",customerRequest.getCustomerName());
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        if (customerRepository.existsByEmail(customerRequest.getEmail())) {
            log.error("email existed:{}",customerRequest.getEmail());
            throw new AppException(ErrorCode.EMAIL_EXISTED);
        }
    }
    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public List<CustomerResponse> getAllCustomer(){
        var listCustomer=customerRepository.findAll();
        return listCustomer.stream().map((customer)->CustomerResponse.builder()
                .customerId(customer.getCustomerId())
                .customerName(customer.getCustomerName())
                .customerAddress(customer.getCustomerAddress())
                .email(customer.getEmail())
                .build()).toList();
    }
    @Override
    public CustomerResponse getCustomerById(Integer customerId){
        var customer=customerRepository.findByCustomerId(customerId)
                .orElseThrow(()->new AppException(ErrorCode.USER_NOT_EXISTED));
        log.info("get customer by id: {}",customerId);
        return CustomerResponse.builder()
                .customerId(customer.getCustomerId())
                .customerName(customer.getCustomerName())
                .customerAddress(customer.getCustomerAddress())
                .email(customer.getEmail())
                .build();
    }
    @Override
    public CustomerResponse updateCustomer(Integer customerId, UpdateCustomerRequest updateCustomerRequest){
        var customer=customerRepository.findByCustomerId(customerId)
                .orElseThrow(()->new AppException(ErrorCode.USER_NOT_EXISTED));
        if(!passwordEncoder.matches(updateCustomerRequest.getOldPassword(),customer.getPassword())){
            log.error("password incorrect");
            throw new AppException(ErrorCode.PASSWORD_INCORRECT);
        }
        if(updateCustomerRequest.getOldPassword().equalsIgnoreCase(updateCustomerRequest.getNewPassword())){
            log.error("both password are the same");
            throw new AppException(ErrorCode.PASSWORD_SAME);
        }
        customer.setCustomerName(updateCustomerRequest.getCustomerName());
        customer.setCustomerAddress(updateCustomerRequest.getCustomerAddress());
        customer.setEmail(updateCustomerRequest.getEmail());
        customer.setPassword(passwordEncoder.encode(updateCustomerRequest.getNewPassword()));
        customerRepository.save(customer);
        log.info("update customer with id:{} successful",customerId);
        return CustomerResponse.builder()
                .customerId(customer.getCustomerId())
                .customerName(customer.getCustomerName())
                .customerAddress(customer.getCustomerAddress())
                .email(customer.getEmail())
                .build();

    }
    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    @Modifying
    @Override
    public void deleteCustomer(Integer customerId){
        customerRepository.deleteByCustomerId(customerId);
        log.info("delete customer id:{} successful",customerId);
    }
}
