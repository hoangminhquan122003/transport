package com.transporthc.utils;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    USER_EXISTED(1000,"user existed", HttpStatus.BAD_REQUEST),
    EMAIL_EXISTED(1001,"email existed",HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(1002,"user existed", HttpStatus.BAD_REQUEST),
    PASSWORD_INCORRECT(1003, "old password incorrect",HttpStatus.BAD_REQUEST),
    PASSWORD_SAME(1004,"both password are the same", HttpStatus.BAD_REQUEST),
    WAREHOUSE_EXISTED(1005,"warehouse existed",HttpStatus.BAD_REQUEST),
    WAREHOUSE_NOT_EXISTED(1006,"warehouse not existed",HttpStatus.BAD_REQUEST),
    CARGO_EXISTED(1007,"cargo existed",HttpStatus.BAD_REQUEST),
    CARGO_NOT_EXISTED(1008,"cargo not existed",HttpStatus.BAD_REQUEST),
    UNAUTHENTICATED(1009, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1010, "Unauthorized", HttpStatus.FORBIDDEN),
    INVALID_MESSAGE_KEY(1011,"invalid message key",HttpStatus.BAD_REQUEST),
    NAME_BLANK(1012,"name not be blank",HttpStatus.BAD_REQUEST),
    EMAIL_BLANK(1013,"email not be blank",HttpStatus.BAD_REQUEST),
    PASSWORD_BLANK(1014,"password not be blank",HttpStatus.BAD_REQUEST),
    BLANK(1015,"field not be blank",HttpStatus.BAD_REQUEST),
    MASS(1016,"mass must be grater than 0",HttpStatus.BAD_REQUEST),
    NOT_ENOUGH_CARGO(1017,"not enought mass",HttpStatus.BAD_REQUEST),
    PAGE_SIZE_INVALID(1018,"page size need to greater than 10 ",HttpStatus.BAD_REQUEST),
    PAGE_NO_INVALID(1019,"cannot be negative",HttpStatus.BAD_REQUEST),
    TRANSACTION_NOT_EXISTED(1020,"transaction not existed",HttpStatus.BAD_REQUEST),
    EMAIL_NOT_EXISTED(1021,"email not existed",HttpStatus.BAD_REQUEST),

    ;
    int code;
    String message;
    HttpStatusCode statusCode;

}
