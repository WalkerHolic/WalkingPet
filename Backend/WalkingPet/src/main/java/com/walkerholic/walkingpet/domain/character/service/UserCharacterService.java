package com.walkerholic.walkingpet.domain.character.service;

import com.walkerholic.walkingpet.domain.character.dto.response.UserCharacterResponse;
import com.walkerholic.walkingpet.domain.character.dto.response.UserCharacterStatResponse;
import com.walkerholic.walkingpet.domain.character.entity.UserCharacter;
import com.walkerholic.walkingpet.domain.character.repository.UserCharacterRepository;
import com.walkerholic.walkingpet.global.error.GlobalBaseException;
import com.walkerholic.walkingpet.global.error.GlobalErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Slf4j
public class UserCharacterService {

    private static final int REDUCE_STAT_POINT = 5; // 스탯 사용시 줄어 드는 스탯 포인트

    private final UserCharacterRepository userCharacterRepository;

    /**
     * 사용자의 캐릭터 정보 가져오기
     */
    @Transactional(readOnly = true)
    public UserCharacterResponse getUserCharacterInfo(int userCharacterId) {
        UserCharacter userCharacter = userCharacterRepository.findByUserCharacterId(userCharacterId)
                .orElseThrow(() -> new GlobalBaseException(GlobalErrorCode.USER_CHARACTER_NOT_FOUND));

        return UserCharacterResponse.from(userCharacter);
    }

    /**
     * 사용자가 가지고 있는 스탯 포인트로 능력치 올리기
     */
    @Transactional(readOnly = false)
    public UserCharacterStatResponse addStatPoint(int userCharacterId, String value) {
        UserCharacter userCharacter = userCharacterRepository.findByUserCharacterId(userCharacterId)
                .orElseThrow(() -> new GlobalBaseException(GlobalErrorCode.USER_CHARACTER_NOT_FOUND));

        if (userCharacter.getStatPoint() < REDUCE_STAT_POINT) {
            throw new GlobalBaseException(GlobalErrorCode.INSUFFICIENT_STAT_POINT);
        }

        userCharacter.useStatPoint(REDUCE_STAT_POINT);
        if (value.equals("health")) {
            userCharacter.raiseHealth();
        } else if (value.equals("defense")) {
            userCharacter.raiseDefense();
        } else if (value.equals("power")) {
            userCharacter.raisePower();
        }

        return UserCharacterStatResponse.from(userCharacter);
    }
}
