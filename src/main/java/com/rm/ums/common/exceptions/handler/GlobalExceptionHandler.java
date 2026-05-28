package com.rm.ums.common.exceptions.handler;

import com.rm.ums.common.exceptions.UmsException;
import com.rm.ums.common.model.response.CustomResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public CustomResponse handleLmsException(MethodArgumentNotValidException ex) {
        String message = prepareMethodArgumentNotValidationMessage(ex);
        logException(ex);
        return CustomResponse.fail(message, HttpStatus.valueOf(ex.getStatusCode().value()));
    }

    @ExceptionHandler(UmsException.class)
    public CustomResponse handleLmsException(UmsException ex) {
        logException(ex);
        return CustomResponse.fail(ex.getMessage(), ex.getHttpStatus());
    }


    private  void logException(Throwable ex) {
        log.error("Exception occurred -> ", ex);
    }

    private static String prepareMethodArgumentNotValidationMessage(MethodArgumentNotValidException ex) {
        return ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .filter(Objects::nonNull)
                .filter(msg -> !msg.isBlank())
                .findFirst()
                .orElse("Validation error");
    }

}
