package com.walkerholic.walkingpet.global.error;

import com.walkerholic.walkingpet.domain.auth.error.TokenErrorCode;
import lombok.Getter;

@Getter
public class GlobalBaseException extends RuntimeException{
    GlobalErrorCode globalErrorCode;

    public GlobalBaseException(GlobalErrorCode globalErrorCode) {
        this.globalErrorCode = globalErrorCode;
    }
}