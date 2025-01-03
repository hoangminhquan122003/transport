package com.transporthc.controller;

import com.transporthc.dto.request.CustomerRequest;
import com.transporthc.dto.request.UpdateCustomerRequest;
import com.transporthc.dto.response.ApiResponse;
import com.transporthc.dto.response.CustomerResponse;
import com.transporthc.service.CustomerService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@RequestMapping("/customer")
public class CustomerController {
    CustomerService customerServer;

    @PostMapping
    public ApiResponse<CustomerResponse> createCustomer(@RequestBody @Valid CustomerRequest customerRequest){

        return ApiResponse.<CustomerResponse>builder()
                .message("create customer")
                .result(customerServer.createCustomer(customerRequest))
                .build();
    }

    @GetMapping
    public ApiResponse<List<CustomerResponse>> getAllCustomer(){
        return ApiResponse.<List<CustomerResponse>>builder()
                .message("get all customer")
                .result(customerServer.getAllCustomer())
                .build();
    }
    @GetMapping("/{customerId}")
    public ApiResponse<CustomerResponse> getCustomerById(@PathVariable Integer customerId){
        return ApiResponse.<CustomerResponse>builder()
                .message("get customer by id "+customerId)
                .result(customerServer.getCustomerById(customerId))
                .build();

    }
    @PutMapping("/{customerId}")
    public ApiResponse<CustomerResponse> updateCustomerById(@PathVariable Integer customerId, @RequestBody @Valid
                                        UpdateCustomerRequest updateCustomerRequest){
        return ApiResponse.<CustomerResponse>builder()
                .message("update customer successful")
                .result(customerServer.updateCustomer(customerId,updateCustomerRequest))
                .build();
    }
    @DeleteMapping("/{customerId}")
    public ApiResponse<Void> deleteCustomerById( @PathVariable Integer customerId){
        customerServer.deleteCustomer(customerId);
        return ApiResponse.<Void>builder()
                .message("delete customer successful")
                .build();
    }
}
