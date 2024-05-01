package com.walkerholic.walkingpet.domain.ranking.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
public class StepRankingList {
    private int userId;
    private String nickname;
    private int step;
    private int characterId;
    private int ranking;

    public static StepRankingList from(AccStepRankingInfo accStepRankingInfo, int ranking) {
        return StepRankingList.builder()
                .userId(accStepRankingInfo.getUserId())
                .nickname(accStepRankingInfo.getNickname())
                .characterId(accStepRankingInfo.getCharacterId())
                .step(accStepRankingInfo.getStep())
                .ranking(ranking)
                .build();
    }
}
