package com.walkerholic.walkingpet.domain.auth.filter;

import com.walkerholic.walkingpet.domain.auth.Service.SecurityService;
import com.walkerholic.walkingpet.domain.auth.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

/**
 * 인증 정보 설정
 * : 요정 헤더에서 키가 'Authorization'인 필드의 값을 가져온 다음, 토큰의 접두사 'Bearer'을 제외한 값을 얻는다.
 * 만약 값이 null이거나 Bearer로 시작하지 않으면 null을 반환한다.
 * 이어서 가져온 토큰이 유효한지 확인하고 유효하다면 인증 정보를 관리하는 시큐리티 컨텍스트에 인증 정보를 설정한다.
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final SecurityService securityService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        if(checkAccessTokenValid(request)) {
            filterChain.doFilter(request, response);
        } else {
            throw new RuntimeException("알 수 없는 오류가 발생했습니다.");
        }
    }

    private boolean checkAccessTokenValid(HttpServletRequest request) {
        String accessToken = jwtUtil.extractTokenFromHeader(request);
        if(!jwtUtil.validateAccessToken(accessToken)) {
            securityService.saveUserInSecurityContext(accessToken);
        }
        return true;
    }

    /* 
        SecurityConfig 파일에서 해당 api에 접근시 permitAll로 해두었으나 
        permitAll과 상관없이 jwtAuthorizationFilter 로 계속 들어가서 JwtAuthorizationFilter에서 다시 설정
        -> Spring Security에서는 보안 필터 체인을 통해 모든 요청에 대한 보안 검사를 수행하기 때문
        즉, excludedPath에 포함되어 있으면 true를 반환하고 해당 필터를 타지 않도록 설정
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
//        String[] excludePath = {
////                "/character/test",
//                "/auth/social-login",
//                "/swagger-ui/",
//                "/error"
//        };
        String[] excludePath = {
                "/api-docs/json",
                "/api-docs",
                "/auth/social-login",
//                "/v3/api-docs/swagger-config",
                "/swagger-ui/",
                "/swagger-config",
                "/swagger.yaml",
                "/requestBodies",
                "/swagger-",
                "/error"
        };
        String path = request.getRequestURI();

        System.out.println(path);
        System.out.println(Arrays.toString(excludePath));
        boolean shouldNotFilter = Arrays.stream(excludePath).anyMatch(path::startsWith);
        System.out.println(shouldNotFilter);

        return shouldNotFilter;
    }
}
