package com.walkerholic.walkingpet.domain.battle.dto.response;

import com.walkerholic.walkingpet.domain.character.entity.UserCharacter;
import com.walkerholic.walkingpet.domain.users.entity.UserDetail;
import lombok.*;

@Builder
@Getter
@ToString
public class EnemyInfo {
    private String nickname;
    private int level;
    private int characterId;
    private int rating;
    private int health;
    private int power;
    private int defense;

    public static EnemyInfo from(UserCharacter userCharacter, UserDetail userDetail){
        return EnemyInfo.builder()
                .nickname(userCharacter.getUser().getNickname())
                .characterId(userCharacter.getUserCharacterId())
                .rating(userDetail.getBattleRating())
                .health(userCharacter.getHealth())
                .power(userCharacter.getPower())
                .defense(userCharacter.getDefense())
                .build();
    }
}
