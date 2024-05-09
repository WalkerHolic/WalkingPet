package com.walkerholic.walkingpet.global.auth.controller;

import com.walkerholic.walkingpet.global.auth.dto.request.SocialLoginDTO;
import com.walkerholic.walkingpet.global.auth.util.JwtUtil;
import com.walkerholic.walkingpet.domain.users.dto.UsersDto;
import com.walkerholic.walkingpet.domain.users.service.LoginService;
import com.walkerholic.walkingpet.global.auth.service.SecurityLoginService;
import com.walkerholic.walkingpet.global.error.GlobalSuccessCode;
import com.walkerholic.walkingpet.global.error.response.CommonResponseEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/auth")
public class AuthController {
    private final LoginService loginService;

    private final SecurityLoginService securityService;
    private final JwtUtil jwtUtil;

    @PostMapping("/social-login")
    public ResponseEntity<CommonResponseEntity> socialLogin(@RequestBody SocialLoginDTO socialLoginDTO) //TODO: @Valid 추가
    {
        log.info("로그인 api socialLogin - SocialLoginDTO: {}", socialLoginDTO);
        UsersDto savedOrFindUser = loginService.socialLogin(socialLoginDTO);
//        securityService.saveUserInSecurityContext(socialLoginDTO);
        securityService.saveUserInSecurityContext(savedOrFindUser);
        Map<String, String> tokenMap = jwtUtil.initToken(savedOrFindUser);
        return CommonResponseEntity.toResponseEntity(GlobalSuccessCode.SUCCESS, tokenMap);
    }
}
