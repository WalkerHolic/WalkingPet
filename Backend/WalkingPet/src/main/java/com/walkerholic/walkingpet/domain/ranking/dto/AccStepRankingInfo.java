package com.walkerholic.walkingpet.domain.ranking.dto;

import com.walkerholic.walkingpet.domain.users.entity.UserStep;
import lombok.*;

import java.io.Serializable;


@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AccStepRankingInfo implements Serializable {
    private int userId;
    private String nickname; // user
    private int step; // user_step
    private int characterId;

    @Builder
    public AccStepRankingInfo(int userId, String nickname, int step, int characterId) {
        this.userId = userId;
        this.nickname = nickname;
        this.step = step;
        this.characterId = characterId;
    }

    public static AccStepRankingInfo entityFrom(UserStep userStep) {
        return AccStepRankingInfo.builder()
                .userId(1)
                .nickname("test2")
                .characterId(1)
                .step(userStep.getAccumulationStep())
                .build();
    }

    public static AccStepRankingInfo redisFrom(AccStepRankingInfo userStep) {
        return AccStepRankingInfo.builder()
                .userId(1)
                .nickname("test2")
                .characterId(1)
                .step(userStep.getStep())
                .build();
    }
}


