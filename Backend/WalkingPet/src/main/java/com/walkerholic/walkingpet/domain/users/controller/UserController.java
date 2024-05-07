package com.walkerholic.walkingpet.domain.users.controller;

import com.walkerholic.walkingpet.domain.character.dto.response.UserCharacterInfoResponse;
import com.walkerholic.walkingpet.domain.gacha.service.GachaService;
import com.walkerholic.walkingpet.domain.users.service.LoginService;
import com.walkerholic.walkingpet.global.error.GlobalSuccessCode;
import com.walkerholic.walkingpet.global.error.response.CommonResponseEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/user")
public class UserController {

    private final LoginService loginService;

    @GetMapping("/emailCheck")
    @Operation(summary = "가입한 회원인지 확인", description = "유저의 이메일이 이미 회원가입한 이메일인지 확인하기 ")
    @ApiResponse(responseCode = "200", description = "S200 - 가입한 회원인지 확인 성공", content = @Content(schema = @Schema(implementation = CommonResponseEntity.class)))
    @ApiResponse(responseCode = "404", description = "C400 - 가입한 회원인지 확인 실패")
    public ResponseEntity<CommonResponseEntity> checkIsMember(@RequestParam("userEmail") String userEmail) {
        log.info("가입한 회원인지 확인 checkIsMember - userEmail: {}", userEmail);
        boolean isMember = loginService.checkIsMember(userEmail);
        return CommonResponseEntity.toResponseEntity(GlobalSuccessCode.SUCCESS, isMember);
    }

    @GetMapping("/nicknameCheck")
    @Operation(summary = "닉네임 중복 체크", description = "입력한 닉네임이 기존 유저들과 중복되지 않는지 확인하기 ")
    @ApiResponse(responseCode = "200", description = "S200 - 닉네임 중복 체크 성공", content = @Content(schema = @Schema(implementation = CommonResponseEntity.class)))
    @ApiResponse(responseCode = "404", description = "C400 - 닉네임 중복 체크 실패")
    public ResponseEntity<CommonResponseEntity> checkAvailableNickname(@RequestParam("nickname") String nickname) {
        log.info("닉네임 중복 체크 checkAvailableNickname - nickname: {}", nickname);
        boolean isAvailable = loginService.checkAvailableNickname(nickname);
        return CommonResponseEntity.toResponseEntity(GlobalSuccessCode.SUCCESS, isAvailable);
    }
}