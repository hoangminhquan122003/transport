package com.transporthc.controller;

import com.transporthc.dto.request.AuthenticationRequest;
import com.transporthc.dto.request.IntrospectRequest;
import com.transporthc.dto.response.ApiResponse;
import com.transporthc.dto.response.AuthenticationResponse;
import com.transporthc.dto.response.IntrospectResponse;
import com.transporthc.service.AuthenticationService;
import com.transporthc.service.CustomerService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/auth")
public class AuthenticationController {
    AuthenticationService authenticationService;
    CustomerService customerService;

    @PostMapping("/login")
    public ApiResponse<AuthenticationResponse> login(@RequestBody AuthenticationRequest request) {
        return ApiResponse.<AuthenticationResponse>builder()
                .message("login successful")
                .result(authenticationService.authenticate(request))
                .build();
    }

    @PostMapping("/introspect")
    public ApiResponse<IntrospectResponse> introspect(@RequestBody IntrospectRequest request) {
        return ApiResponse.<IntrospectResponse>builder()
                .result(authenticationService.introspect(request))
                .message("the account is active")
                .build();
    }

}
