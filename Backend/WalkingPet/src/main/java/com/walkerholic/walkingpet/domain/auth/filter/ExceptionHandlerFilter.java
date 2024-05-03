package com.walkerholic.walkingpet.domain.auth.filter;

import com.walkerholic.walkingpet.domain.auth.error.TokenBaseException;
import com.walkerholic.walkingpet.domain.auth.error.TokenErrorCode;
import com.walkerholic.walkingpet.global.error.response.ErrorResponseEntity;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

@Slf4j
@Component
public class ExceptionHandlerFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (TokenBaseException tokenBaseException) {
            log.warn("tokenBaseException occur:");
            TokenErrorCode tokenErrorCode = tokenBaseException.getTokenErrorCode();
            setErrorResponse(tokenErrorCode,response);
//            setErrorResponse(tokenErrorCode.getStatus(), tokenErrorCode.getMessage() ,response);
        }
    }

    private void setErrorResponse(TokenErrorCode tokenErrorCode, HttpServletResponse response) {
//        response.setStatus(status.value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            String json = new ObjectMapper().writeValueAsString(ErrorResponseEntity.toTokenEntity(tokenErrorCode));
            response.getWriter().write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    private void setErrorResponse(HttpStatus status, String message, HttpServletResponse response) {
////        response.setStatus(status.value());
//        response.setContentType("application/json");
//        response.setCharacterEncoding("UTF-8");
//        ErrorResponseEntity errorResponse = new ErrorResponseEntity();
//
//        try {
//            String json = new ObjectMapper().writeValueAsString(errorResponse);
//            response.getWriter().write(json);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }


}
