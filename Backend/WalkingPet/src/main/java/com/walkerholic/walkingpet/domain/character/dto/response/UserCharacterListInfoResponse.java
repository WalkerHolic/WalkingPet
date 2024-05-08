package com.walkerholic.walkingpet.domain.character.dto.response;

import com.walkerholic.walkingpet.domain.character.dto.UserCharacterListInfo;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class UserCharacterListInfoResponse {
    private Integer userCharacterId;
    private List<UserCharacterListInfo> characters;

    public static UserCharacterListInfoResponse from(List<UserCharacterListInfo> userCharacterListInfos, int userCharacterId) {
        return UserCharacterListInfoResponse.builder()
                .userCharacterId(userCharacterId)
                .characters(userCharacterListInfos)
                .build();
    }
}
