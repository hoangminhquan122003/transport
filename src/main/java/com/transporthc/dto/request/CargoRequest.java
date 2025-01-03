package com.transporthc.dto.request;

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
public class CargoRequest {
    @NotBlank(message = "NAME_BLANK")
    String cargoName;

    String cargoType;

    @Builder.Default
    Double availableMass= 0.0;
}
