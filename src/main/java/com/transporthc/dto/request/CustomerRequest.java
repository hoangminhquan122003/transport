package com.transporthc.dto.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerRequest {
    @NotBlank(message = "NAME_BLANK")
    String customerName;

    String customerAddress;

    @NotBlank(message = "EMAIL_BLANK")
    @Email
    String email;

    @NotBlank(message = "PASSWORD_BLANK")
    String password;
}
