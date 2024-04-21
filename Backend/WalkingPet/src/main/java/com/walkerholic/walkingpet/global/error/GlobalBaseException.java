package com.walkerholic.walkingpet.global.error;

import lombok.Getter;

@Getter
public class GlobalBaseException extends RuntimeException{
    GlobalErrorCode globalErrorCode;

    public GlobalBaseException(GlobalErrorCode globalErrorCode) {
        this.globalErrorCode = globalErrorCode;
    }
}
//public class GlobalBaseException extends RuntimeException {
//    private final GlobalErrorCode errorCode;
//
//    public GlobalBaseException(GlobalErrorCode errorCode) {
//        super(errorCode.getMessage());
//        this.errorCode = errorCode;
//    }
//
//    public GlobalErrorCode getErrorCode() {
//        return errorCode;
//    }
//
//}