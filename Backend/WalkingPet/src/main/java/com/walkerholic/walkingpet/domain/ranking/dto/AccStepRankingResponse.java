package com.walkerholic.walkingpet.domain.ranking.dto;

import com.walkerholic.walkingpet.domain.users.entity.UserStep;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AccStepRankingResponse {
    private String nickname;
    private int dailyStep;
    private int characterId;

//    public static AccStepRankingResponse from(String nickname, int dailyStep, boolean characterId) {
    public static AccStepRankingResponse from(UserStep userStep) {
        return AccStepRankingResponse.builder()
                .dailyStep(userStep.getDailyStep())
                .build();
    }
}


