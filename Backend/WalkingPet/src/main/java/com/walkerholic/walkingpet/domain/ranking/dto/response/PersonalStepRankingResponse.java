package com.walkerholic.walkingpet.domain.ranking.dto.response;

import com.walkerholic.walkingpet.domain.ranking.dto.PersonalStepRankingInfo;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PersonalStepRankingResponse {
    private List<PersonalStepRankingInfo> topRanking;

    @Builder
    public PersonalStepRankingResponse(List<PersonalStepRankingInfo> topRanking) {
        this.topRanking = topRanking;
    }

    public static PersonalStepRankingResponse from(List<PersonalStepRankingInfo> accStepRanking) {
        return PersonalStepRankingResponse.builder()
                .topRanking(accStepRanking)
                .build();
    }
}
