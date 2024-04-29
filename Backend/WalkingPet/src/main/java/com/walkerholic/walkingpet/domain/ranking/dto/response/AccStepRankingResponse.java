package com.walkerholic.walkingpet.domain.ranking.dto.response;

import com.walkerholic.walkingpet.domain.ranking.dto.AccStepRankingInfo;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class AccStepRankingResponse {
    private List<AccStepRankingInfo> topRanking;

    public static AccStepRankingResponse from(List<AccStepRankingInfo> accStepRanking) {
        return AccStepRankingResponse.builder()
                .topRanking(accStepRanking)
                .build();
    }
}
