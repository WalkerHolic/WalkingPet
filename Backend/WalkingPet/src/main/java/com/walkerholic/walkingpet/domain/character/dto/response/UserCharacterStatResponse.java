package com.walkerholic.walkingpet.domain.character.dto.response;

import com.walkerholic.walkingpet.domain.character.entity.UserCharacter;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserCharacterStatResponse {
    private int health;
    private int power;
    private int defense;
    private int statPoint;

    public static UserCharacterStatResponse from(UserCharacter userCharacter){
        return UserCharacterStatResponse.builder()
                .health(userCharacter.getHealth())
                .power(userCharacter.getPower())
                .defense(userCharacter.getDefense())
                .statPoint(userCharacter.getStatPoint())
                .build();
    }
}
