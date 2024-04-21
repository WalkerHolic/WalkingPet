package com.walkerholic.walkingpet.global.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * RestControllerAdvice = ControllerAdvice + ResponseBody
 * 여러 컨트롤러에 대해 전역적으로 ExceptionHandler를 적용하여 json 형태로 반환
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionAdvice {

    /**
     * 우리가 만든 커스텀 예외 처리
     * @param e
     * @return
     */
    @ExceptionHandler(GlobalBaseException.class) // 등록한 Controller 영역 안에서만 작동
    protected ResponseEntity<ExceptionResponse> handleGlobalBaseException(GlobalBaseException e) {
        log.error("{} Exception {}: {}", e.getGlobalErrorCode(), e.getGlobalErrorCode().getCode(), e.getGlobalErrorCode().getMessage());
        return makeErrorResponse(e.getGlobalErrorCode());
    }

    /**
     * Valid 검증 실패시 오류 발생
     * 주로 @RequestBody, @RequestPart 어노테이션에서 발생
     */
    //TODO @Valid 예외 처리 코드 추가

    public ResponseEntity<ExceptionResponse> makeErrorResponse(GlobalErrorCode e){
        return ResponseEntity.status(e.getStatus())
                .body(ExceptionResponse.builder()
                        .code(e.getCode())
                        .message(e.getMessage())
                        .status(e.getStatus())
                        .build());
    }
}
