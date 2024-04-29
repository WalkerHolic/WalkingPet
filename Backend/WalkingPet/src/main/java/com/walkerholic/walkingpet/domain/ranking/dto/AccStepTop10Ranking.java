package com.walkerholic.walkingpet.domain.ranking.dto;

import com.walkerholic.walkingpet.domain.users.entity.UserStep;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AccStepTop10Ranking {
//    private int ranking;
    private String nickname; // user
    private int step; // user_step

    public static AccStepTop10Ranking from(UserStep userStep) {
        return AccStepTop10Ranking.builder()
//                .ranking(1)
                .nickname("테스트 유저")
                .step(userStep.getDailyStep())
                .build();
    }
}


