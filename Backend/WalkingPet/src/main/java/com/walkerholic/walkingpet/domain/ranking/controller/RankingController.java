package com.walkerholic.walkingpet.domain.ranking.controller;

import com.walkerholic.walkingpet.domain.ranking.dto.AccStepTop3Ranking;
import com.walkerholic.walkingpet.domain.ranking.dto.response.AccStepTop10RankingResponse;
import com.walkerholic.walkingpet.domain.ranking.dto.response.AccStepTop3RankingResponse;
import com.walkerholic.walkingpet.domain.ranking.dto.response.PersonalStepRankingResponse;
import com.walkerholic.walkingpet.domain.ranking.service.RankingService;
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
@RequestMapping("/ranking")
public class RankingController {
    private final RankingService rankingService;

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
    @ApiResponse(responseCode = "200", description = "S200 - 개인 랭킹 top 10 조회 조회 성공", content = @Content(schema = @Schema(implementation = AccStepTop10RankingResponse.class)))
//    @ApiResponse(responseCode = "404", description = "C400 - 유저의 해당 캐릭터를 찾기 실패")
    public ResponseEntity<CommonResponseEntity> getPersonalRankingTop10(@RequestParam("value") String value) {
        log.info("개인 랭킹 top 10 조회 getPersonalRankingTop10 - value: {}", value);

        AccStepTop10RankingResponse accStepRankingTop10 = rankingService.getAccStepRankingTop10();
        return CommonResponseEntity.toResponseEntity(GlobalSuccessCode.SUCCESS, accStepRankingTop10);
    }

    @GetMapping("/person/top3")
    @Operation(summary = "개인 랭킹 top 3 조회", description = "어제/누적/실시간 개인 랭킹 정보를 top 3 가져오기")
    @ApiResponse(responseCode = "200", description = "S200 - 개인 랭킹 top 3 조회 조회 성공", content = @Content(schema = @Schema(implementation = AccStepTop3RankingResponse.class)))
//    @ApiResponse(responseCode = "404", description = "C400 - 유저의 해당 캐릭터를 찾기 실패")
    public ResponseEntity<CommonResponseEntity> getPersonalRankingTop3(@RequestParam("value") String value) {
        log.info("개인 랭킹 top 3 조회 getPersonalRankingTop3 - value: {}", value);

        AccStepTop3RankingResponse accStepRankingTop3 = rankingService.getAccStepRankingTop3();
        return CommonResponseEntity.toResponseEntity(GlobalSuccessCode.SUCCESS, accStepRankingTop3);
    }
}