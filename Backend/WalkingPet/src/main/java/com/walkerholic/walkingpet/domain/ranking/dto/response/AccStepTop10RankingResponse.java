package com.walkerholic.walkingpet.domain.ranking.dto.response;

import com.walkerholic.walkingpet.domain.ranking.dto.AccStepTop10Ranking;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class AccStepTop10RankingResponse {
    private List<AccStepTop10Ranking> topRanking;

    public static AccStepTop10RankingResponse from(List<AccStepTop10Ranking> accStepRanking) {
        return AccStepTop10RankingResponse.builder()
                .topRanking(accStepRanking)
                .build();
    }
}
