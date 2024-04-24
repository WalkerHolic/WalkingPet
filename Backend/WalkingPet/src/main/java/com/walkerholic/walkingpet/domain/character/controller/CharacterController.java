package com.walkerholic.walkingpet.domain.character.controller;

import com.walkerholic.walkingpet.domain.character.dto.response.UserCharacterResponse;
import com.walkerholic.walkingpet.domain.character.dto.response.UserCharacterStatResponse;
import com.walkerholic.walkingpet.domain.character.service.UserCharacterService;
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
@RequestMapping("/character")
public class CharacterController {

    private final UserCharacterService characterService;

    @GetMapping
    @Operation(summary = "캐릭터 정보 확인", description = "유저의 userCharacterId로  캐릭터 정보 가져오기")
    @ApiResponse(responseCode = "200", description = "S200 - 유저의 해당 캐릭터를 찾기 성공", content = @Content(schema = @Schema(implementation = UserCharacterResponse.class)))
    @ApiResponse(responseCode = "404", description = "C400 - 유저의 해당 캐릭터를 찾기 실패")
    public ResponseEntity<CommonResponseEntity> getUserCharacterInfo(@RequestParam("userCharacterId") int userCharacterId) {
        log.info("CharacterController getUserCharacterInfo - userCharacterId: {}", userCharacterId);

        UserCharacterResponse userCharacterInfo = characterService.getUserCharacterInfo(userCharacterId);
        return CommonResponseEntity.toResponseEntity(GlobalSuccessCode.SUCCESS, userCharacterInfo);
    }

    // /api/character/stat?value=power&userCharacterId=3
    @GetMapping("/stat")
    @Operation(summary = "스탯 분배", description = "유저의 캐릭터 스탯을 분배")
    @ApiResponse(responseCode = "200", description = "S200 - 유저의 캐릭터 스탯 분배 성공", content = @Content(schema = @Schema(implementation = UserCharacterStatResponse.class)))
    @ApiResponse(responseCode = "403", description = "C400 - 유저의 해당 캐릭터 스탯 포인트가 부족")
    @ApiResponse(responseCode = "404", description = "C400 - 유저의 해당 캐릭터를 찾기 실패")
    public ResponseEntity<CommonResponseEntity> statDistribution(@RequestParam("userCharacterId") int userCharacterId, @RequestParam("value") String value) {
        log.info("CharacterController statDistribution - userCharacterId: {}, value: {}", userCharacterId, value);

        UserCharacterStatResponse userCharacterStatInfo = characterService.addStatPoint(userCharacterId, value);
        return CommonResponseEntity.toResponseEntity(GlobalSuccessCode.SUCCESS, userCharacterStatInfo);
    }

    @GetMapping("/test")
    @Operation(summary = "통신 테스트", description = "통신 테스트")
    @ApiResponse(responseCode = "200", description = "S200 - 통신 테스트 성공", content = @Content(schema = @Schema(implementation = String.class)))
    public ResponseEntity<CommonResponseEntity> test() {
        log.info("통신 테스트");
        return CommonResponseEntity.toResponseEntity(GlobalSuccessCode.SUCCESS, "통신 테스트");
    }

}
