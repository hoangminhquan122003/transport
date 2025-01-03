package com.transporthc.exception;

import com.transporthc.utils.ErrorCode;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AppException extends RuntimeException{
    ErrorCode errorCode;

}
