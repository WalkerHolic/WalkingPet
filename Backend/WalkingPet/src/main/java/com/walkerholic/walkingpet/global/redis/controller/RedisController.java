package com.walkerholic.walkingpet.global.redis.controller;

import com.walkerholic.walkingpet.domain.ranking.dto.AccStepRankingInfo;
import com.walkerholic.walkingpet.domain.ranking.dto.response.AccStepRankingResponse;
import com.walkerholic.walkingpet.domain.ranking.dto.response.StepRankingResponse;
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
            redisRankingService.saveAccStepList(stepInfo);
        }

        return CommonResponseEntity.toResponseEntity(GlobalSuccessCode.SUCCESS);
    }

    @GetMapping("/getTest/accStepRanking")
    @Operation(summary = "redis test", description = "")
    public ResponseEntity<CommonResponseEntity> getRedisAccRankingInfo(@RequestParam("userId") int userId) {
        log.info("redis 사용자 순위 조회 테스트 - redis test getRedisAccRankingInfo");

        System.out.println("redis controller 테스트");
        int rank = redisRankingService.getUserRanking(1);
        return CommonResponseEntity.toResponseEntity(GlobalSuccessCode.SUCCESS, rank);
    }

    @GetMapping("/getTest/top10")
    @Operation(summary = "redis test top10", description = "")
    public ResponseEntity<CommonResponseEntity> getRedisAccRankingTop10() {
        log.info("redis 누적 랭킹 Top 10 출력 테스트 - redis test getRedisAccRankingTop10");

        System.out.println("테스트");
        StepRankingResponse accStepRankingList = redisRankingService.getRedisAccStepRanking(0, 9);

        return CommonResponseEntity.toResponseEntity(GlobalSuccessCode.SUCCESS, accStepRankingList);
    }
}
