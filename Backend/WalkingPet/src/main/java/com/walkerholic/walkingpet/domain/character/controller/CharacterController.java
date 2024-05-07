package com.walkerholic.walkingpet.domain.character.controller;

import com.walkerholic.walkingpet.domain.character.dto.request.ChangeUserCharacterIdRequest;
import com.walkerholic.walkingpet.domain.character.dto.response.*;
import com.walkerholic.walkingpet.domain.character.service.UserCharacterService;
import com.walkerholic.walkingpet.domain.users.entity.Users;
import com.walkerholic.walkingpet.global.auth.dto.CustomUserDetail;
import com.walkerholic.walkingpet.global.error.GlobalSuccessCode;
import com.walkerholic.walkingpet.global.error.response.CommonResponseEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/character")
public class CharacterController {

    private final UserCharacterService userCharacterService;

    @GetMapping
    @Operation(summary = "캐릭터 정보 확인", description = "유저의 userCharacterId로  캐릭터 정보 가져오기")
    @ApiResponse(responseCode = "200", description = "S200 - 유저의 해당 캐릭터를 찾기 성공", content = @Content(schema = @Schema(implementation = UserCharacterInfoResponse.class)))
    @ApiResponse(responseCode = "404", description = "C400 - 유저의 해당 캐릭터를 찾기 실패")
    public ResponseEntity<CommonResponseEntity> getUserCharacterInfo(@AuthenticationPrincipal CustomUserDetail userDetail) {
        Integer userId = userDetail.getUsers().getUserId();
        log.info("캐릭터 정보 확인 getUserCharacterInfo - userId: {}", userId);

        UserCharacterInfoResponse userCharacterInfo = userCharacterService.getUserCharacterInfo(userId);
        return CommonResponseEntity.toResponseEntity(GlobalSuccessCode.SUCCESS, userCharacterInfo);
    }

    @GetMapping("/list")
    @Operation(summary = "유저의 캐릭터 목록 정보 가져오기", description = "유저가 보유한 캐릭터와 보유하지 않은 캐릭터 목록 가져오기")
//    @ApiResponse(responseCode = "200", description = "S200 - 유저의 캐릭터 정보 찾기 성공", content = @Content(schema = @Schema(implementation = UserCharacterInfoResponse.class)))
    public ResponseEntity<CommonResponseEntity> getUserCharacterListInfo(@AuthenticationPrincipal CustomUserDetail userDetail) {
        Integer userId = userDetail.getUsers().getUserId();
        log.info("유저의 캐릭터 목록 정보 가져오기 getUserCharacterListInfo - userId: {}", userId);

        UserCharacterListInfoResponse userCharacterInfoList = userCharacterService.getUserCharacterInfoList(userId);
        return CommonResponseEntity.toResponseEntity(GlobalSuccessCode.SUCCESS, userCharacterInfoList);
    }

    // /api/character/stat?value=power&userCharacterId=3
    @GetMapping("/stat")
    @Operation(summary = "스탯 분배", description = "유저의 캐릭터 스탯을 분배")
    @ApiResponse(responseCode = "200", description = "S200 - 유저의 캐릭터 스탯 분배 성공", content = @Content(schema = @Schema(implementation = UserCharacterStatResponse.class)))
    @ApiResponse(responseCode = "403", description = "C400 - 유저의 해당 캐릭터 스탯 포인트가 부족")
    @ApiResponse(responseCode = "404", description = "C400 - 유저의 해당 캐릭터를 찾기 실패")
    public ResponseEntity<CommonResponseEntity> statDistribution(@AuthenticationPrincipal CustomUserDetail userDetail, @RequestParam("value") String value) {
        Integer userId = userDetail.getUsers().getUserId();
        log.info("스탯 분배 statDistribution - userId: {}, value: {}", userId, value);

        UserCharacterStatResponse userCharacterStatInfo = userCharacterService.addStatPoint(userId, value);
        return CommonResponseEntity.toResponseEntity(GlobalSuccessCode.SUCCESS, userCharacterStatInfo);
    }

//    @PostMapping("/stat/reset")
    @GetMapping("/stat/reset")
    @Operation(summary = "스탯 분배 초기화", description = "유저의 캐릭터 스탯을 분배 초기화")
    @ApiResponse(responseCode = "200", description = "S200 - 유저의 캐릭터 스탯 분배 초기화 성공", content = @Content(schema = @Schema(implementation = ResetStatResponse.class)))
    @ApiResponse(responseCode = "400", description = "C400 - 이미 스탯 초기화 버튼 누름")
    public ResponseEntity<CommonResponseEntity> resetStatDistribution(@AuthenticationPrincipal CustomUserDetail userDetail) {
        Integer userId = userDetail.getUsers().getUserId();
        log.info("스탯 분배 초기화 resetStatDistribution - userIdv: {}", userId);

        ResetStatResponse resetStatResponse = userCharacterService.resetInitStatus(userId);
        return CommonResponseEntity.toResponseEntity(GlobalSuccessCode.SUCCESS, resetStatResponse);
    }

    @PostMapping("/change")
    @Operation(summary = "캐릭터 변경", description = "유저의 현재 캐릭터 변경")
    @ApiResponse(responseCode = "200", description = "S200 - 유저의 캐릭터 변경 성공")
    public ResponseEntity<CommonResponseEntity> changeUserCharacter(@AuthenticationPrincipal CustomUserDetail userDetail, @RequestBody ChangeUserCharacterIdRequest changeUserCharacterIdRequest) {
        Integer userId = userDetail.getUsers().getUserId();
        log.info("캐릭터 변경 changeUserCharacter - userId: {}, userCharacterId: {}", userId, changeUserCharacterIdRequest.getUserCharacterId());
        userCharacterService.changeUserCharacter(userId, changeUserCharacterIdRequest);

        return CommonResponseEntity.toResponseEntity(GlobalSuccessCode.SUCCESS);
    }

    @GetMapping("/checkstep")
    @Operation(summary = "유저의 걸음수 측정", description = "앱 시작시 걸음수 측정")
    @ApiResponse(responseCode = "200", description = "S200 - 걸음수 측정 성공")
    public ResponseEntity<CommonResponseEntity> getUserStep(@AuthenticationPrincipal CustomUserDetail userDetail) {
        Integer userId = userDetail.getUsers().getUserId();
        int frontStep = 100;
        log.info("CharacterController getUserStep - userId: {}, step: {}", userId, frontStep);

        UserStepResponse userStepResponse = userCharacterService.checkUserStep(userId, frontStep);
        return CommonResponseEntity.toResponseEntity(GlobalSuccessCode.SUCCESS, userStepResponse);
    }



    @GetMapping("/test")
    @Operation(summary = "통신 테스트", description = "통신 테스트")
    @ApiResponse(responseCode = "200", description = "S200 - 통신 테스트 성공", content = @Content(schema = @Schema(implementation = String.class)))
    public ResponseEntity<CommonResponseEntity> test() {
        log.info("통신 테스트");
//        Users users = userDetail.getUsers();
//        System.out.println("user: " + users.getEmail());
//        System.out.println("user: " + users.getUserId());
//        userCharacterService.saveUserStep(1, 1234);
        return CommonResponseEntity.toResponseEntity(GlobalSuccessCode.SUCCESS, "통신 테스트");
    }

}
