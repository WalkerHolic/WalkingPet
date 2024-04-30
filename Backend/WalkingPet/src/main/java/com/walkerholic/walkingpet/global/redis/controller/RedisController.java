package com.walkerholic.walkingpet.global.redis.controller;

import com.walkerholic.walkingpet.domain.ranking.dto.AccStepRankingInfo;
import com.walkerholic.walkingpet.domain.ranking.service.RankingService;
import com.walkerholic.walkingpet.global.error.GlobalSuccessCode;
import com.walkerholic.walkingpet.global.error.response.CommonResponseEntity;
import com.walkerholic.walkingpet.global.redis.service.RedisRankingService;
import com.walkerholic.walkingpet.global.redis.service.TestRedisService;
import com.walkerholic.walkingpet.global.redis.service.TestRepo;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/redis")
public class RedisController {
    private final RankingService rankingService;
    private final RedisRankingService redisRankingService;
    private final TestRedisService testRedisService;
    private final TestRepo testRepo;

    @GetMapping("/saveTest/accStepRanking")
    @Operation(summary = "redis test", description = "")
    public ResponseEntity<CommonResponseEntity> saveRedisAccRankingInfo() {
        log.info("redis 누적 랭킹 저장 테스트 - redis test saveRedisAccRankingInfo");

        List<AccStepRankingInfo> userAccStepList = rankingService.getUserAccStepList();

        for (AccStepRankingInfo stepInfo : userAccStepList) {
//            redisRankingService.saveAccStepList(stepInfo);
//            testRedisService.saveAccStepInfo(stepInfo);
            testRepo.saveUserData(stepInfo);
        }

        return CommonResponseEntity.toResponseEntity(GlobalSuccessCode.SUCCESS);
    }

    @GetMapping("/getTest/accStepRanking")
    @Operation(summary = "redis test", description = "")
    public ResponseEntity<CommonResponseEntity> getRedisAccRankingInfo(@RequestParam("userId") int userId) {
        log.info("redis 누적 랭킹 출력 테스트 - redis test getRedisAccRankingInfo");

        AccStepRankingInfo user = redisRankingService.getUser(userId);
        System.out.println("redis user: " + user);

        return CommonResponseEntity.toResponseEntity(GlobalSuccessCode.SUCCESS);
    }

    @GetMapping("/getTest/top10")
    @Operation(summary = "redis test top10", description = "")
    public ResponseEntity<CommonResponseEntity> getRedisAccRankingTop10() {
        log.info("redis 누적 랭킹 Top 10 출력 테스트 - redis test getRedisAccRankingTop10");

//        List<AccStepRankingInfo> topRankingProjectIdList = redisRankingService.getRedisAccStepRanking();
//        if (topRankingProjectIdList.isEmpty()) {
//            System.out.println("누적 랭킹 리스트 비어있음");
//        }
//        for (AccStepRankingInfo accStepInfo : topRankingProjectIdList) {
//            System.out.println("redis accStepInfo: " + accStepInfo);
//        }
        System.out.println("테스트");

        return CommonResponseEntity.toResponseEntity(GlobalSuccessCode.SUCCESS);
    }
}
