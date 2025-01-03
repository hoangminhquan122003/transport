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
public class Warehouse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer warehouseId;

    @Column(unique = true)
    String warehouseName;

    String warehouseAddress;

    @OneToMany(mappedBy = "warehouse",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    List<Transaction> transactions;
}
