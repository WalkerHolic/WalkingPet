package com.walkerholic.walkingpet.domain.battle.controller;

import com.walkerholic.walkingpet.domain.battle.dto.response.BattleResponse;
import com.walkerholic.walkingpet.domain.battle.dto.response.EnemyInfo;
import com.walkerholic.walkingpet.domain.battle.dto.response.UserBattleInfoDTO;
import com.walkerholic.walkingpet.domain.battle.service.BattleService;
import com.walkerholic.walkingpet.global.error.GlobalSuccessCode;
import com.walkerholic.walkingpet.global.error.response.CommonResponseEntity;
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
    public ResponseEntity<CommonResponseEntity> getUserBattleInfo(@RequestParam("userId") int userId){
        //추후 헤더에서 userId 받아올 예정
        //int userId = ~~~

        UserBattleInfoDTO userBattleInfoDTO = battleService.getUserBattleInfo(userId);

        log.info("BattleController getUserBattleInfo - userId: {}", userId);

        return CommonResponseEntity.toResponseEntity(GlobalSuccessCode.SUCCESS, userBattleInfoDTO);
    }

    @GetMapping("/enemyinfo")
    public ResponseEntity<CommonResponseEntity> getEnemyBattleInfo(@RequestParam("userId")int userId){

        EnemyInfo enemyInfo = battleService.getEnemyBattleInfo(userId);

        return CommonResponseEntity.toResponseEntity(GlobalSuccessCode.SUCCESS, enemyInfo);
    }

    @GetMapping("/start")
    public ResponseEntity<CommonResponseEntity> battleStart(@RequestParam("userId") int userId){
        BattleResponse battleResponse = battleService.startBattle(userId);

        return CommonResponseEntity.toResponseEntity(GlobalSuccessCode.SUCCESS, battleResponse);
    }
}
