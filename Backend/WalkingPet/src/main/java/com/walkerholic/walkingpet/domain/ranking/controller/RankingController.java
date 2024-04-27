package com.walkerholic.walkingpet.domain.ranking.controller;

import com.walkerholic.walkingpet.domain.ranking.dto.AccStepRankingResponse;
import com.walkerholic.walkingpet.domain.ranking.service.RankingService;
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

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/ranking")
public class RankingController {
    private final RankingService rankingService;

    @GetMapping
    @Operation(summary = "개인 랭킹 조회", description = "유저의 어제/누적/실시간 랭킹 정보를 가져오기")
//    @ApiResponse(responseCode = "200", description = "S200 - 유저의 해당 캐릭터를 찾기 성공", content = @Content(schema = @Schema(implementation = UserCharacterInfoResponse.class)))
//    @ApiResponse(responseCode = "404", description = "C400 - 유저의 해당 캐릭터를 찾기 실패")
    public ResponseEntity<CommonResponseEntity> getPersonalRanking(@RequestParam("value") String value) {
        int userId = 1;
        log.info("개인 랭킹 조회 getPersonalRanking - userId: {}, value: {}", userId, value);

        List<AccStepRankingResponse> accStepRanking = rankingService.getAccStepRanking();
        return CommonResponseEntity.toResponseEntity(GlobalSuccessCode.SUCCESS, accStepRanking);
    }
}
