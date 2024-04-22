package com.walkerholic.walkingpet.domain.character.dto.response;


import com.walkerholic.walkingpet.domain.character.entity.UserCharacter;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@ToString
@Getter
public class UserCharacterResponse {
    private String nickname;
    private int characterId;
    private int level;
    private int experience;
    private int maxExperience;
    private int health;
    private int power;
    private int defense;
    private int statPoint;

    public static UserCharacterResponse from(UserCharacter userCharacter){
        return UserCharacterResponse.builder()
                .nickname("test")
                .characterId(userCharacter.getUserCharacterId())
                .level(userCharacter.getCharacterLevel())
                .experience(userCharacter.getExperience())
                .maxExperience(userCharacter.getExperience())
                .health(userCharacter.getHealth())
                .power(userCharacter.getPower())
                .defense(userCharacter.getDefense())
                .statPoint(userCharacter.getStatPoint())
                .build();
    }
}
