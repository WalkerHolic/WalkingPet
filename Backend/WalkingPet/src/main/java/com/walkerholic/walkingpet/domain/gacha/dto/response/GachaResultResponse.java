package com.walkerholic.walkingpet.domain.gacha.dto.response;

import com.walkerholic.walkingpet.domain.character.entity.Character;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@ToString
@Getter
public class GachaResultResponse {
    private Integer characterId;
    private String characterName;
    private Integer grade;

    public static GachaResultResponse from(Character character){
        return GachaResultResponse.builder()
                .characterId(character.getCharacterId())
                .characterName(character.getName())
                .grade(character.getGrade())
                .build();
    }
}
