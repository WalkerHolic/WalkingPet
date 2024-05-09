package com.walkerholic.walkingpet.domain.character.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ChangeCharacterIdResponse {
    private int changeCharacterId;

    public static ChangeCharacterIdResponse from(int changeCharacterId){
        return ChangeCharacterIdResponse.builder()
                .changeCharacterId(changeCharacterId)
                .build();
    }
}
