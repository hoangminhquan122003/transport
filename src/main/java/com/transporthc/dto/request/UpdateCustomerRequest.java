package com.transporthc.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateCustomerRequest {
    @NotBlank(message = "NAME_BLANK")
    String customerName;

    String customerAddress;

    @NotBlank
    @Email
    String email;

    @NotBlank
    String oldPassword;

    @NotBlank
    String newPassword;
}
