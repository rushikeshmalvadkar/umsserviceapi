package com.rm.ums.common.model.response;

import com.rm.ums.common.enums.UmsResponseMessageEnum;
import lombok.Builder;
import lombok.Data;

import static com.rm.ums.common.enums.UmsResponseStatusEnum.SUCCESS;

@Data
@Builder
public class CustomResponse {
    private Object data;
    private String message;
    private int code;
    private String status;

    public static CustomResponse success(Object data, UmsResponseMessageEnum message) {
        return CustomResponse.builder()
                .data(data)
                .message(message.value())
                .code(SUCCESS.code())
                .status(SUCCESS.name())
                .build();
    }
}
