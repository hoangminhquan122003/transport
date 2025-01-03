package com.transporthc.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.transporthc.utils.TransactionType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer transactionId;

    @Enumerated(EnumType.STRING)
    TransactionType transactionType;

    @CreatedDate
    LocalDate transactionDate;

    String origin;

    String destination;

    Double mass;

    String description;

    String imageName;

    String imageType;

    @Lob
    byte[] imageData;

    @ManyToOne(fetch = FetchType.LAZY)
    Cargo cargo;

    @ManyToOne(fetch = FetchType.LAZY)
    Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    Warehouse warehouse;

}
