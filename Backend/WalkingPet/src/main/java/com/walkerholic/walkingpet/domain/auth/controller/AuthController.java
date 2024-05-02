package com.walkerholic.walkingpet.domain.auth.controller;

import com.walkerholic.walkingpet.domain.auth.dto.request.SocialLoginDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/auth")
public class AuthController {

    @PostMapping("/social-login")
    public ResponseEntity<Map<String, String>> socialLogin(@RequestBody SocialLoginDTO socialLoginDTO) //TODO: @Valid 추가
    {

        return null;
    }
}
