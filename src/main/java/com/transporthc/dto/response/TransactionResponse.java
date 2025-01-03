package com.transporthc.dto.response;

import com.transporthc.utils.TransactionType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TransactionResponse {
    Integer transactionId;

    TransactionType transactionType;

    LocalDate transactionDate;

    String origin;

    String destination;

    Double mass;

    String imageName;

    String imageType;

    byte[] imageData;

    String description;

    String cargoName;

    String customerName;

    String warehouseName;
}
