package com.transporthc.service;

import com.transporthc.dto.request.CustomerRequest;
import com.transporthc.dto.request.UpdateCustomerRequest;
import com.transporthc.dto.response.CustomerResponse;
import com.transporthc.entity.Customer;
import com.transporthc.exception.AppException;
import com.transporthc.repository.CustomerRepository;
import com.transporthc.utils.ErrorCode;
import com.transporthc.utils.Role;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CustomerService {

    CustomerResponse createCustomer (CustomerRequest customerRequest);

    List<CustomerResponse> getAllCustomer();

    CustomerResponse getCustomerById(Integer customerId);

    CustomerResponse updateCustomer(Integer customerId, UpdateCustomerRequest updateCustomerRequest);

    void deleteCustomer(Integer customerId);
}
