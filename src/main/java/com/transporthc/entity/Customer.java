package com.transporthc.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer customerId;

    @Column(unique = true)
    String customerName;

    String customerAddress;

    String email;

    String password;

    String role;

    @OneToMany(mappedBy = "customer",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    List<Transaction> transactions;
}
