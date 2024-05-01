package com.walkerholic.walkingpet.domain.character.dto;

import com.walkerholic.walkingpet.domain.character.dto.response.UserCharacterInfoResponse;
import com.walkerholic.walkingpet.domain.character.entity.Character;
import com.walkerholic.walkingpet.domain.character.entity.UserCharacter;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserCharacterListInfo {
    private int characterId;
    private String characterName;
    private int characterGrade;
    private int userCharacterId;
    private int userCharacterLevel;
    private int userCharacterUpgrade;
    private boolean userCharacterStatus;

    // 해당 캐릭터를 사용자가 가지고 있지 않은 경우
    public static UserCharacterListInfo characterFrom(Character character){
        return UserCharacterListInfo.builder()
                .characterId(character.getCharacterId())
                .characterName(character.getName())
                .characterGrade(character.getGrade())
                .userCharacterStatus(false)
                .build();
    }

    // 해당 캐릭터를 사용자가 가지고 있는 경우
    public static UserCharacterListInfo userCharacterFrom(UserCharacter userCharacter){
        return UserCharacterListInfo.builder()
                .characterId(userCharacter.getCharacter().getCharacterId())
                .characterName(userCharacter.getCharacter().getName())
                .characterGrade(userCharacter.getCharacter().getGrade())
                .userCharacterId(userCharacter.getUserCharacterId())
                .userCharacterLevel(userCharacter.getLevel())
                .userCharacterUpgrade(userCharacter.getUpgrade())
                .userCharacterStatus(true)
                .build();
    }

    /*
        charaterId(이미지)
        characterName(캐릭터 고유 이름)
        characterGrade(캐릭터 고유 레벨)
        userCharacterId(사용자의 보유 캐릭터 id)
        userCharacterLevel(사용자의 캐릭터 레벨)
        userCharacterUpgrade(사용자의 해당 캐릭터 보유 개수)
        userCharacterStatus(해당 캐릭터의 보유 여부)
     */
}
