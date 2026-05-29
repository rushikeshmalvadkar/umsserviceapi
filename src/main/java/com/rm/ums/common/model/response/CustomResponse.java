package com.rm.ums.common.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.rm.ums.common.enums.UmsResponseMessageEnum;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;
import static com.rm.ums.common.enums.UmsResponseStatusEnum.*;

@Data
@Builder
@JsonInclude(NON_NULL)
public class CustomResponse {
    private Object data;
    private String message;
    private int code;
    private String status;
    private boolean success;
    private List<String> errors;

    public static CustomResponse success(Object data, UmsResponseMessageEnum message) {
        return CustomResponse.builder()
                .data(data)
                .message(message.value())
                .code(SUCCESS.code())
                .success(true)
                .status(SUCCESS.name())
                .build();
    }

    public static CustomResponse created(Object data, UmsResponseMessageEnum message) {
        return CustomResponse.builder()
                .data(data)
                .message(message.value())
                .code(CREATED.code())
                .success(true)
                .status(CREATED.name())
                .build();
    }

    public static CustomResponse fail(String message, HttpStatus httpStatus) {
        return builder()
                .message(message)
                .code(httpStatus.value())
                .status(httpStatus.name())
                .build();
    }
    public static CustomResponse badRequest(List<String> errorMessages) {
        return CustomResponse.builder()
                .code(BAD_REQUEST.code())
                .success(false)
                .status(BAD_REQUEST.name())
                .errors(errorMessages)
                .build();
    }
}
