package com.walkerholic.walkingpet.domain.ranking.dto.response;

import com.walkerholic.walkingpet.domain.ranking.dto.AccStepTop10Ranking;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class PersonalStepRankingResponse {
    private UserPersonalStepRankingResponse myranking;
    private List<AccStepTop10Ranking> rankings;

    public static PersonalStepRankingResponse from(UserPersonalStepRankingResponse userPersonalStepRanking, List<AccStepTop10Ranking> accStepRanking) {
        return PersonalStepRankingResponse.builder()
                .myranking(userPersonalStepRanking)
                .rankings(accStepRanking)
                .build();
    }
}
