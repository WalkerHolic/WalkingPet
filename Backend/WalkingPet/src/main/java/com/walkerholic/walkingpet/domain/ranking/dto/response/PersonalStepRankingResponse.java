package com.walkerholic.walkingpet.domain.ranking.dto.response;

import com.walkerholic.walkingpet.domain.ranking.dto.AccStepRankingInfo;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class PersonalStepRankingResponse {
    private UserPersonalStepRankingResponse myranking;
    private List<AccStepRankingInfo> rankings;

    public static PersonalStepRankingResponse from(UserPersonalStepRankingResponse userPersonalStepRanking, List<AccStepRankingInfo> accStepRanking) {
        return PersonalStepRankingResponse.builder()
                .myranking(userPersonalStepRanking)
                .rankings(accStepRanking)
                .build();
    }
}
