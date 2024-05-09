package com.walkerholic.walkingpet.domain.ranking.dto.response;

import com.walkerholic.walkingpet.domain.ranking.dto.AccStepRankingInfo;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class PersonalStepRankingAllInfoResponse {
    private UserPersonalStepRankingResponse myranking;
    private List<AccStepRankingInfo> rankings;

    public static PersonalStepRankingAllInfoResponse from(UserPersonalStepRankingResponse userPersonalStepRanking, List<AccStepRankingInfo> accStepRanking) {
        return PersonalStepRankingAllInfoResponse.builder()
                .myranking(userPersonalStepRanking)
                .rankings(accStepRanking)
                .build();
    }
}
