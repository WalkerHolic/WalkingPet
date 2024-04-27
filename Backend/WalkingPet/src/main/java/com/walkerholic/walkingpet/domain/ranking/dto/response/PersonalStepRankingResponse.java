package com.walkerholic.walkingpet.domain.ranking.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class PersonalStepRankingResponse {
    private UserPersonalStepRankingResponse myranking;
    private List<AccStepRankingResponse> rankings;

    public static PersonalStepRankingResponse from(UserPersonalStepRankingResponse userPersonalStepRanking, List<AccStepRankingResponse> accStepRanking) {
        return PersonalStepRankingResponse.builder()
                .myranking(userPersonalStepRanking)
                .rankings(accStepRanking)
                .build();
    }
}
