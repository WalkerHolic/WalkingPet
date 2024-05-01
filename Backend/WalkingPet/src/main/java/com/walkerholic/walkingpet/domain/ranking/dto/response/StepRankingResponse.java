package com.walkerholic.walkingpet.domain.ranking.dto.response;

import com.walkerholic.walkingpet.domain.ranking.dto.AccStepRankingInfo;
import com.walkerholic.walkingpet.domain.ranking.dto.StepRankingList;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class StepRankingResponse {
    private List<StepRankingList> topRanking;

    public static StepRankingResponse from(List<StepRankingList> accStepRanking) {
        return StepRankingResponse.builder()
                .topRanking(accStepRanking)
                .build();
    }
}
