package com.transporthc.dto.response;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CargoResponse {
    Integer cargoId;

    String cargoName;

    String cargoType;

    Double availableMass;
}
