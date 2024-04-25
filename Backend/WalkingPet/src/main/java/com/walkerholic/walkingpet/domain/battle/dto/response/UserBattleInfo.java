package com.walkerholic.walkingpet.domain.battle.dto.response;

import com.walkerholic.walkingpet.domain.character.entity.UserCharacter;
import com.walkerholic.walkingpet.domain.users.entity.UserDetail;
import lombok.*;

@Builder
@ToString
@Getter
public class UserBattleInfo {
    private String nickname;
    private int characterId;
    private int health;
    private int power;
    private int defense;
    private int rating;
    private int battleCount;

    public static UserBattleInfo from(UserCharacter userCharacter, UserDetail userDetail){
        return UserBattleInfo.builder()
                .nickname(userDetail.getUser().getNickname())
                .characterId(userDetail.getSelectUserCharacter().getCharacter().getCharacterId())
                .health(userCharacter.getHealth())
                .power(userCharacter.getPower())
                .defense(userCharacter.getDefense())
                .battleCount(userDetail.getBattleCount())
                .build();
    }
}
