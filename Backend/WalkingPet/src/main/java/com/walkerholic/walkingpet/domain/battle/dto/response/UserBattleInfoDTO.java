package com.walkerholic.walkingpet.domain.battle.dto.response;

import com.walkerholic.walkingpet.domain.character.entity.UserCharacter;
import com.walkerholic.walkingpet.domain.users.entity.UserDetail;
import lombok.*;

@Builder
@ToString
@Getter
public class UserBattleInfoDTO {
    private String nickname;
    private int level;
    private int grade;
    private int characterId;
    private int health;
    private int power;
    private int defense;
    private int rating;
    private int battleCount;

    public static UserBattleInfoDTO from(UserCharacter userCharacter, UserDetail userDetail){
        return UserBattleInfoDTO.builder()
                .nickname(userDetail.getUser().getNickname())
                .rating(userDetail.getBattleRating())
                .level(userCharacter.getLevel())
                .grade(userCharacter.getCharacter().getGrade())
                .characterId(userDetail.getSelectUserCharacter().getCharacter().getCharacterId())
                .health(userCharacter.getHealth())
                .power(userCharacter.getPower())
                .defense(userCharacter.getDefense())
                .battleCount(userDetail.getBattleCount())
                .build();
    }
}
