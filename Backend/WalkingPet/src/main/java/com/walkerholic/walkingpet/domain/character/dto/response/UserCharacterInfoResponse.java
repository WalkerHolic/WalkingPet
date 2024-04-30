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
    private int addHealth;
    private int addPower;
    private int addDefense;
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
                .addHealth(userCharacter.getHealth() - userCharacter.getCharacter().getFixHealth())
                .addPower(userCharacter.getPower() - userCharacter.getCharacter().getFixPower())
                .addDefense(userCharacter.getDefense() - userCharacter.getCharacter().getFixDefense())
                .upgrade(userCharacter.getUpgrade())
                .build();
    }
}
