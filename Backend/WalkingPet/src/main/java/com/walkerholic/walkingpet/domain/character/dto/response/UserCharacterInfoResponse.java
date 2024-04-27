package com.walkerholic.walkingpet.domain.character.dto.response;


import com.walkerholic.walkingpet.domain.character.entity.UserCharacter;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserCharacterInfoResponse {
    private String nickname;
    private int characterId;
    private int level;
    private int experience;
    private int maxExperience;
    private int health;
    private int power;
    private int defense;
    private int basicHealth;
    private int basicPower;
    private int basicDefense;
    private int statPoint;
    private int upgrade;

    public static UserCharacterInfoResponse from(UserCharacter userCharacter){
        return UserCharacterInfoResponse.builder()
                .nickname("test")
                .characterId(userCharacter.getUserCharacterId())
                .level(userCharacter.getLevel())
                .experience(userCharacter.getExperience())
                .maxExperience(userCharacter.getExperience())
                .health(userCharacter.getHealth())
                .power(userCharacter.getPower())
                .defense(userCharacter.getDefense())
                .statPoint(userCharacter.getStatPoint())
                .basicHealth(userCharacter.getHealth() - userCharacter.getUpgrade())
                .basicPower(userCharacter.getPower() - userCharacter.getUpgrade())
                .basicDefense(userCharacter.getDefense() - userCharacter.getUpgrade())
                .upgrade(userCharacter.getUpgrade())
                .build();
    }
}
