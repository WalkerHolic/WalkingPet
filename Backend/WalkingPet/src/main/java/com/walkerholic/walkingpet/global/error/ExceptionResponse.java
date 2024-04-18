package com.walkerholic.walkingpet.global.error;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ExceptionResponse {
    private String code;
    private String message;
    private int status;

    @Builder
    public ExceptionResponse(String code, String message, int status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }
}
