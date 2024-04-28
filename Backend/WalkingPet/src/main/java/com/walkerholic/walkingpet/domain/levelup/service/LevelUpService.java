package com.walkerholic.walkingpet.domain.levelup.service;

import com.walkerholic.walkingpet.domain.character.entity.UserCharacter;
import com.walkerholic.walkingpet.domain.character.repository.UserCharacterRepository;
import com.walkerholic.walkingpet.domain.item.entity.UserItem;
import com.walkerholic.walkingpet.domain.item.repository.UserItemRepository;
import com.walkerholic.walkingpet.domain.levelup.dto.CharacterLevelExperienceInfo;
import com.walkerholic.walkingpet.domain.levelup.dto.LevelUpReward;
import com.walkerholic.walkingpet.domain.levelup.dto.response.LevelUpInfo;
import com.walkerholic.walkingpet.domain.levelup.dto.response.LevelUpResponse;
import com.walkerholic.walkingpet.domain.levelup.function.LevelUpFunction;
import com.walkerholic.walkingpet.global.error.GlobalBaseException;
import com.walkerholic.walkingpet.global.error.GlobalErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class LevelUpService {
    private final UserCharacterRepository userCharacterRepository;
    private final LevelUpFunction levelUpFunction;
    private final UserItemRepository userItemRepository;

    //1. 레벨업 여부 & 레벨업 정보 반환 서비스(현재 레벨, 다음 레벨, 보상 정보)
    //레벨업 된 정보를 저장한다
    public LevelUpResponse getLevelUpResponse(int userId, int getExperience){
        UserCharacter userCharacter = getUserCharacter(userId);

        CharacterLevelExperienceInfo characterLevelExperienceInfo = CharacterLevelExperienceInfo.builder()
                .nowLevel(userCharacter.getLevel())
                .nowExperience(userCharacter.getExperience())
                .build();

        if(levelUpFunction.checkLevelUp(characterLevelExperienceInfo,getExperience)){
            return LevelUpResponse.builder()
                    .isLevelUp(false)
                    .levelUpInfo(getLevelUpInfo(userId, userCharacter, getExperience))
                    .build();
        }
        else{
            return LevelUpResponse.builder()
                    .isLevelUp(false)
                    .levelUpInfo(null)
                    .build();
        }
    }

    //2. 레벨업 정보 결과 가져오기
    //가져오면서 캐릭터, 아이템 정보 업데이트하기.
    public LevelUpInfo getLevelUpInfo(int userId, UserCharacter userCharacter, int getExperience){
        CharacterLevelExperienceInfo characterInfo = CharacterLevelExperienceInfo.builder()
                .nowLevel(userCharacter.getLevel())
                .nowExperience(userCharacter.getExperience())
                .build();

        CharacterLevelExperienceInfo updateCharacterInfo = levelUpFunction.getNextLevel(characterInfo, getExperience);
        LevelUpReward levelUpReward = levelUpFunction.getLevelUpReward(characterInfo.getNowLevel(), updateCharacterInfo.getNowLevel());

        //1. 캐릭터 정보 업데이트하기
        userCharacter.updateLevelUp(updateCharacterInfo.getNowLevel(), updateCharacterInfo.getNowExperience(), levelUpReward.getStatPoint());
        userCharacterRepository.save(userCharacter);

        //2. 레벨업 보상 업데이트하기
        for(String itemName : levelUpReward.getItemReward().keySet()){
            UserItem userItem = getUserItem(userId,itemName);
            userItem.addItemQuantity(levelUpReward.getItemReward().get(itemName));
            userItemRepository.save(userItem);
        }

        return LevelUpInfo.builder()
                .nowLevel(characterInfo.getNowLevel())
                .nextLevel(updateCharacterInfo.getNowLevel())
                .levelUpReward(levelUpReward)
                .build();
    }

    //[1] UserId로 UserCharacter 찾기
    private UserCharacter getUserCharacter(int userId){
        return userCharacterRepository.findUserCharacterByUserId(userId)
                .orElseThrow(()-> new GlobalBaseException(GlobalErrorCode.USER_CHARACTER_NOT_FOUND));
    }

    //[2] 유저 아이템 찾기
    private UserItem getUserItem(int userId, String itemName){
        return userItemRepository.findByUserItemWithUserAndItemFetch(userId, itemName)
                .orElseThrow(()-> new GlobalBaseException(GlobalErrorCode.USER_ITEM_NOT_FOUND_BOX));
    }
}
