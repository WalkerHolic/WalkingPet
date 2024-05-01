package com.walkerholic.walkingpet.domain.ranking.dto;

import com.walkerholic.walkingpet.domain.users.entity.UserDetail;
import com.walkerholic.walkingpet.domain.users.entity.UserStep;
import lombok.*;

import java.io.Serializable;

// Redis에서 사용
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AccStepRankingInfo implements Serializable {
    private int userId;
    private String nickname; // user
    private int step; // user_step
    private int characterId; // user_detail에서 character

    @Builder
    public AccStepRankingInfo(int userId, String nickname, int step, int characterId) {
        this.userId = userId;
        this.nickname = nickname;
        this.step = step;
        this.characterId = characterId;
    }

    public static AccStepRankingInfo entityFrom(UserDetail userDetail, UserStep userStep) {
        return AccStepRankingInfo.builder()
                .userId(userDetail.getUser().getUserId())
                .nickname(userDetail.getUser().getNickname())
                .characterId(userDetail.getSelectUserCharacter().getCharacter().getCharacterId())
                .step(userStep.getAccumulationStep())
                .build();
    }

    public static AccStepRankingInfo redisFrom(AccStepRankingInfo accStepRankingInfo) {
        return AccStepRankingInfo.builder()
                .userId(1)
                .nickname("test2")
                .characterId(1)
                .step(accStepRankingInfo.getStep())
                .build();
    }

    public AccStepRankingInfo changeStep(int step) {
        this.step = step;
        return this;
    }
}


