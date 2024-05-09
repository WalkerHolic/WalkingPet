package com.walkerholic.walkingpet.domain.ranking.dto.response;

import com.walkerholic.walkingpet.domain.ranking.dto.AccStepRankingInfo;
import com.walkerholic.walkingpet.domain.ranking.dto.StepRankingInfo;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AccStepRankingResponse {
    private List<StepRankingInfo> topRanking;

    @Builder
    public AccStepRankingResponse(List<StepRankingInfo> topRanking) {
        this.topRanking = topRanking;
    }

    public static AccStepRankingResponse from(List<StepRankingInfo> accStepRanking) {
        return AccStepRankingResponse.builder()
                .topRanking(accStepRanking)
                .build();
    }
}
