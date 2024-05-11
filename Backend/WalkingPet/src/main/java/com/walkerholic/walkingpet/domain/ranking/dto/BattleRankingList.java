package com.walkerholic.walkingpet.domain.ranking.dto;

import com.walkerholic.walkingpet.domain.users.dto.UserRedisDto;
import com.walkerholic.walkingpet.domain.users.entity.UserDetail;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
public class BattleRankingList {
    private int userId;
    private String nickname;
    private int characterId;
    private int ranking;
    private int battleRating;

    public static BattleRankingList from(UserDetail userDetail, int ranking) {
        return BattleRankingList.builder()
                .userId(userDetail.getUser().getUserId())
                .nickname(userDetail.getUser().getNickname())
                .battleRating(userDetail.getBattleRating())
                .characterId(userDetail.getSelectUserCharacter().getCharacter().getCharacterId())
                .ranking(ranking)
                .build();
    }
}
