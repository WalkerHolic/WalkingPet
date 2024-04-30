package com.walkerholic.walkingpet.domain.ranking.controller;

import com.walkerholic.walkingpet.domain.ranking.dto.AccStepRankingInfo;
import com.walkerholic.walkingpet.domain.ranking.dto.response.AccStepRankingResponse;
import com.walkerholic.walkingpet.domain.ranking.dto.response.AccStepTop3RankingResponse;
import com.walkerholic.walkingpet.domain.ranking.dto.response.PersonalStepRankingResponse;
import com.walkerholic.walkingpet.domain.ranking.dto.response.UserPersonalStepRankingResponse;
import com.walkerholic.walkingpet.domain.ranking.service.RankingService;
import com.walkerholic.walkingpet.global.error.GlobalSuccessCode;
import com.walkerholic.walkingpet.global.error.response.CommonResponseEntity;
import com.walkerholic.walkingpet.global.redis.service.RedisRankingService;
import com.walkerholic.walkingpet.global.redis.service.TestRedisService;
import com.walkerholic.walkingpet.global.redis.service.TestRepo;
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
@RequestMapping("/ranking")
public class RankingController {
    private final RankingService rankingService;
    private final RedisRankingService redisRankingService;
    private final TestRedisService testRedisService;
    private final TestRepo testRepo;

    @GetMapping
    @Operation(summary = "유저의 개인 랭킹과 개인 랭킹 목록 조회", description = "유저의 어제/누적/실시간 랭킹 정보를 가져오기")
    @ApiResponse(responseCode = "200", description = "S200 - 유저의 개인 랭킹과 개인 랭킹 목록 조회 성공", content = @Content(schema = @Schema(implementation = PersonalStepRankingResponse.class)))
//    @ApiResponse(responseCode = "404", description = "C400 - 유저의 해당 캐릭터를 찾기 실패")
    public ResponseEntity<CommonResponseEntity> getPersonalRanking(@RequestParam("value") String value) {
        int userId = 1;
        log.info("개인 랭킹 조회 getPersonalRanking - userId: {}, value: {}", userId, value);

        PersonalStepRankingResponse personalStepRanking = rankingService.getAccStepRanking();
        return CommonResponseEntity.toResponseEntity(GlobalSuccessCode.SUCCESS, personalStepRanking);
    }

    @GetMapping("/person/top10")
    @Operation(summary = "개인 랭킹 top 10 조회", description = "어제/누적/실시간 개인 랭킹 정보를 top 10 가져오기")
    @ApiResponse(responseCode = "200", description = "S200 - 개인 랭킹 top 10 조회 조회 성공", content = @Content(schema = @Schema(implementation = AccStepRankingResponse.class)))
    public ResponseEntity<CommonResponseEntity> getPersonalRankingTop10(@RequestParam("value") String value) {
        log.info("개인 랭킹 top 10 조회 getPersonalRankingTop10 - value: {}", value);

//        AccStepRankingResponse accStepRankingList = redisRankingService.getRedisAccStepRanking(0, 9);
//        AccStepRankingResponse accStepRankingList = testRedisService.getRedisAccStepRanking(0, 9);
        AccStepRankingResponse accStepRankingList = testRepo.getRedisAccStepRanking(0, 9);
        return CommonResponseEntity.toResponseEntity(GlobalSuccessCode.SUCCESS, accStepRankingList);
    }

    @GetMapping("/person/top3")
    @Operation(summary = "개인 랭킹 top 3 조회", description = "어제/누적/실시간 개인 랭킹 정보를 top 3 가져오기")
    @ApiResponse(responseCode = "200", description = "S200 - 개인 랭킹 top 3 조회 조회 성공", content = @Content(schema = @Schema(implementation = AccStepTop3RankingResponse.class)))
    public ResponseEntity<CommonResponseEntity> getPersonalRankingTop3(@RequestParam("value") String value) {
        log.info("개인 랭킹 top 3 조회 getPersonalRankingTop3 - value: {}", value);

        AccStepRankingResponse accStepRankingList = redisRankingService.getRedisAccStepRanking(0, 3);
        return CommonResponseEntity.toResponseEntity(GlobalSuccessCode.SUCCESS, accStepRankingList);
    }

    @GetMapping("/person/myrank")
    @Operation(summary = "개인 랭킹 나의 순위 조회", description = "어제/누적/실시간 유저의 개인 랭킹 가져오기")
    @ApiResponse(responseCode = "200", description = "S200 - 개인 랭킹 나의 순위 조회 성공", content = @Content(schema = @Schema(implementation = UserPersonalStepRankingResponse.class)))
//    @ApiResponse(responseCode = "404", description = "C400 - 유저의 해당 캐릭터를 찾기 실패")
    public ResponseEntity<CommonResponseEntity> getMyPersonalRanking(@RequestParam("value") String value) {
        int userId = 1;
        log.info("개인 랭킹 나의 순위 조회 getMyPersonalRanking - value: {}, userId: {}", value, userId);

//        UserPersonalStepRankingResponse userAccStepRanking = rankingService.getUserAccStepRanking(userId);
        AccStepRankingInfo test = new AccStepRankingInfo(1, "test2", 900, 1);
        int userAccStepRanking = redisRankingService.getUserRanking(test);
        return CommonResponseEntity.toResponseEntity(GlobalSuccessCode.SUCCESS, userAccStepRanking);
    }
}
