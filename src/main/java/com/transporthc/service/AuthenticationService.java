package com.transporthc.service;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.transporthc.dto.request.AuthenticationRequest;
import com.transporthc.dto.request.IntrospectRequest;
import com.transporthc.dto.response.AuthenticationResponse;
import com.transporthc.dto.response.IntrospectResponse;
import com.transporthc.entity.Customer;
import com.transporthc.exception.AppException;
import com.transporthc.repository.CustomerRepository;
import com.transporthc.utils.ErrorCode;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {
    CustomerRepository customerRepository;

    @NonFinal
    @Value("${jwt.signer-key}")
    String SIGNER_KEY;

    public AuthenticationResponse authenticate(AuthenticationRequest request){
        Customer customer=customerRepository.findByEmail(request.getEmail())
                .orElseThrow(()-> new AppException(ErrorCode.EMAIL_NOT_EXISTED));
        PasswordEncoder passwordEncoder= new BCryptPasswordEncoder(10);
        boolean isAuthenticated =passwordEncoder.matches(request.getPassword(), customer.getPassword());
        if(!isAuthenticated){
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        String token=generateToken(customer);
        return  AuthenticationResponse.builder()
                .token(token)
                .build();
    }

    private String generateToken(Customer customer) {
        JWSHeader header=new JWSHeader(JWSAlgorithm.HS512);
        JWTClaimsSet jwtClaimsSet=new JWTClaimsSet.Builder()
                .jwtID(UUID.randomUUID().toString())
                .issueTime(new Date())
                .expirationTime(new Date(Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()))
                .subject(customer.getCustomerName())
                .claim("customerId",customer.getCustomerId())
                .claim("scope","ROLE_"+customer.getRole())
                .build();
        Payload payload= new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject=new JWSObject(header,payload);
        try{
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        }catch (JOSEException e){
            log.error("token not create");
            throw new RuntimeException(e);
        }
    }
    public IntrospectResponse introspect(IntrospectRequest introspectRequest){
        String token=introspectRequest.getToken();
        boolean isValid=true;
        try{
            verifyToken(token);
        }catch (AppException | JOSEException | ParseException e){
            isValid=false;
        }
        return IntrospectResponse.builder()
                .isAuthenticated(isValid)
                .build();
    }

    private SignedJWT verifyToken(String token) throws JOSEException, ParseException {
        JWSVerifier jwsVerifier=new MACVerifier(SIGNER_KEY.getBytes());
        SignedJWT signedJWT= SignedJWT.parse(token);
        Date expirationTime= signedJWT.getJWTClaimsSet().getExpirationTime();
        boolean isVerified = signedJWT.verify(jwsVerifier);
        if(!(isVerified &&expirationTime.after(new Date())))
            throw new AppException(ErrorCode.UNAUTHENTICATED);

        return signedJWT;
    }
}
