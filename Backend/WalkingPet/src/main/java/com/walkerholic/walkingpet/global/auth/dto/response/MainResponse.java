package com.walkerholic.walkingpet.global.auth.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MainResponse {
    int characterId;

    @Builder
    public MainResponse(int characterId) {
        this.characterId = characterId;
    }

    public static MainResponse from(int characterId) {
        return MainResponse.builder()
                .characterId(characterId)
                .build();
    }
}
