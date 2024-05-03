package com.walkerholic.walkingpet.domain.character.dto.response;


import com.walkerholic.walkingpet.domain.character.entity.UserCharacter;
import com.walkerholic.walkingpet.domain.character.function.UserCharacterFunction;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

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
    private int initStatus;

    public static UserCharacterInfoResponse from(UserCharacter userCharacter, int initStatus){
        return UserCharacterInfoResponse.builder()
                .nickname("test")
                .characterId(userCharacter.getUserCharacterId())
                .level(userCharacter.getLevel())
                .experience(userCharacter.getExperience())
                .maxExperience(UserCharacterFunction.getMaxExperience(userCharacter.getLevel()))
                .health(userCharacter.getHealth())
                .power(userCharacter.getPower())
                .defense(userCharacter.getDefense())
                .statPoint(userCharacter.getStatPoint())
                .addHealth(userCharacter.getHealth() - userCharacter.getCharacter().getFixHealth())
                .addPower(userCharacter.getPower() - userCharacter.getCharacter().getFixPower())
                .addDefense(userCharacter.getDefense() - userCharacter.getCharacter().getFixDefense())
                .upgrade(userCharacter.getUpgrade())
                .initStatus(initStatus)
                .build();
    }
}
