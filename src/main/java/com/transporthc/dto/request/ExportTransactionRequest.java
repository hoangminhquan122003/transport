package com.transporthc.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.transporthc.utils.TransactionType;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ExportTransactionRequest {
    @Builder.Default
    TransactionType transactionType=TransactionType.EXPORT;

    LocalDate transactionDate;

    @NotBlank(message = "BLANK")
    String destination;

    @NotNull(message = "BLANK")
    @Min(value = 0,message = "MASS")
    Double mass;

    String description;

    @NotBlank(message = "NAME_BLANK")
    String cargoName;

    @NotBlank(message = "NAME_BLANK")
    String customerName;

    @NotBlank(message = "NAME_BLANK")
    String warehouseName;
}
