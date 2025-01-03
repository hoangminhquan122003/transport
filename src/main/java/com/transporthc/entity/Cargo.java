package com.transporthc.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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
public class Cargo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer cargoId;

    @Column(unique = true)
    String cargoName;

    String cargoType;

    Double availableMass;

    @OneToMany(mappedBy = "cargo",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    List<Transaction> transactions;
}
