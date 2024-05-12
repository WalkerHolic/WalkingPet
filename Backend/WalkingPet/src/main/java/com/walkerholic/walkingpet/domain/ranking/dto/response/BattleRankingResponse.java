package com.walkerholic.walkingpet.domain.ranking.dto.response;

import com.walkerholic.walkingpet.domain.ranking.dto.BattleRankingList;
import com.walkerholic.walkingpet.domain.ranking.dto.PersonalStepRankingInfo;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class BattleRankingResponse {
    private List<BattleRankingList> topRanking;

    public static BattleRankingResponse from(List<BattleRankingList> accStepRanking) {
        return BattleRankingResponse.builder()
                .topRanking(accStepRanking)
                .build();
    }
}
