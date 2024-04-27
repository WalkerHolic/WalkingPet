package com.walkerholic.walkingpet.domain.ranking.dto.response;

import com.walkerholic.walkingpet.domain.users.entity.UserStep;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AccStepRankingResponse {
    private int ranking;
    private String nickname; // use
    private int step; // user_step
    private int characterId; // user_character

//    public static AccStepRankingResponse from(String nickname, int dailyStep, boolean characterId) {
    public static AccStepRankingResponse from(UserStep userStep) {
        return AccStepRankingResponse.builder()
                .ranking(1)
                .characterId(1)
                .nickname("쉬고시포용")
                .step(userStep.getDailyStep())
                .build();
    }

    /**
     *                 "ranking" : 1,
     *                 "characterId" : 10,
     *                 "nickname" : "ssafy",
     *                 "step" : 7234
     */
}


