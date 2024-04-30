package com.walkerholic.walkingpet.domain.battle.controller;

import com.walkerholic.walkingpet.domain.battle.dto.response.BattleResponse;
import com.walkerholic.walkingpet.domain.battle.dto.response.EnemyInfo;
import com.walkerholic.walkingpet.domain.battle.dto.response.UserBattleInfoDTO;
import com.walkerholic.walkingpet.domain.battle.service.BattleService;
import com.walkerholic.walkingpet.global.error.GlobalSuccessCode;
import com.walkerholic.walkingpet.global.error.response.CommonResponseEntity;
import io.swagger.v3.oas.annotations.Operation;
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
@RequestMapping("/battle")
public class BattleController {

    private final BattleService battleService;

    @GetMapping("/myinfo")
    @Operation(summary = "유저 배틀 정보 확인", description = "유저의 user,userDetail,userCharacter로 유저 배틀 정보 가져오기")
    public ResponseEntity<CommonResponseEntity> getUserBattleInfo(@RequestParam("userId") int userId){

        UserBattleInfoDTO userBattleInfoDTO = battleService.getUserBattleInfo(userId);

        log.info("나의 배틀 정보 확인하기 BattleController getUserBattleInfo - userId: {}", userId);

        return CommonResponseEntity.toResponseEntity(GlobalSuccessCode.SUCCESS, userBattleInfoDTO);
    }

    @GetMapping("/enemyinfo")
    @Operation(summary = "상대 유저 배틀 정보 확인", description = "상대 유저의 user,userDetail,userCharacter로 유저 배틀 정보 가져오기")
    public ResponseEntity<CommonResponseEntity> getEnemyBattleInfo(@RequestParam("userId")int userId){

        EnemyInfo enemyInfo = battleService.getEnemyBattleInfo(userId);

        log.info("적의 배틀 정보 확인하기 BattleController getEnemyBattleInfo - userId: {}", userId);

        return CommonResponseEntity.toResponseEntity(GlobalSuccessCode.SUCCESS, enemyInfo);
    }

    @GetMapping("/start")
    @Operation(summary = "배틀 전체 과정 출력", description = "배틀 시작, 진행 과정, 결과, 보상 출력")
    public ResponseEntity<CommonResponseEntity> battleStart(@RequestParam("userId") int userId){
        BattleResponse battleResponse = battleService.startBattle(userId);

        log.info("배틀 시작 BattleController battleStart - userId: {}", userId);

        return CommonResponseEntity.toResponseEntity(GlobalSuccessCode.SUCCESS, battleResponse);
    }
}
