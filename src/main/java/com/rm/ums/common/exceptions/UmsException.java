package com.rm.ums.common.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class UmsException extends RuntimeException {

    private final HttpStatus httpStatus;

    public UmsException(String message,HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
