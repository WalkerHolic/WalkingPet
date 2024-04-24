package com.walkerholic.walkingpet.domain.character.service;

import com.walkerholic.walkingpet.domain.character.dto.request.ChangeUserCharacterIdRequest;
import com.walkerholic.walkingpet.domain.character.dto.response.UserCharacterInfoResponse;
import com.walkerholic.walkingpet.domain.character.dto.response.UserCharacterStatResponse;
import com.walkerholic.walkingpet.domain.character.entity.UserCharacter;
import com.walkerholic.walkingpet.domain.character.repository.CharacterRepository;
import com.walkerholic.walkingpet.domain.character.repository.UserCharacterRepository;
import com.walkerholic.walkingpet.domain.users.entity.UserDetail;
import com.walkerholic.walkingpet.domain.users.repository.UserDetailRepository;
import com.walkerholic.walkingpet.global.error.GlobalBaseException;
import com.walkerholic.walkingpet.global.error.GlobalErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@RequiredArgsConstructor
@Slf4j
public class UserCharacterService {

    private static final int REDUCE_STAT_POINT = 5; // 스탯 사용시 줄어 드는 스탯 포인트

    private final UserCharacterRepository userCharacterRepository;
    private final UserDetailRepository userDetailRepository;

    /**
     * 사용자의 캐릭터 정보 가져오기(api)
     */
    @Transactional(readOnly = true)
    public UserCharacterInfoResponse getUserCharacterInfo(int userCharacterId) {
        UserCharacter userCharacterInfo = getUserCharacter(userCharacterId);

        return UserCharacterInfoResponse.from(userCharacterInfo);
    }

    /**
     * 사용자가 가지고 있는 스탯 포인트로 능력치 올리기
     */
    @Transactional(readOnly = false)
    public UserCharacterStatResponse addStatPoint(int userCharacterId, String value) {
        //TODO: 인가 처리
        UserCharacter userCharacterInfo = getUserCharacter(userCharacterId);

        if (userCharacterInfo.getStatPoint() < REDUCE_STAT_POINT) {
            throw new GlobalBaseException(GlobalErrorCode.INSUFFICIENT_STAT_POINT);
        }

        userCharacterInfo.useStatPoint(REDUCE_STAT_POINT);
        if (value.equals("health")) {
            userCharacterInfo.raiseHealth();
        } else if (value.equals("defense")) {
            userCharacterInfo.raiseDefense();
        } else if (value.equals("power")) {
            userCharacterInfo.raisePower();
        }

        return UserCharacterStatResponse.from(userCharacterInfo);
    }

    @Transactional(readOnly = false)
    public void changeUserCharacter(int userId, ChangeUserCharacterIdRequest userCharacter) {
        //TODO: 인가처리
        UserDetail userDetail = userDetailRepository.findByUserUserId(userId)
                .orElseThrow(() -> new GlobalBaseException(GlobalErrorCode.USER_NOT_FOUND));

        UserCharacter userCharacterInfo = getUserCharacter(userCharacter.getUserCharacterId());

        userDetail.changeUserCharacter(userCharacterInfo);
    }

    /**
     * 사용자의 캐릭터 정보 가져오기(내부 메서드)
     */
    public UserCharacter getUserCharacter(int userCharacterId) {
        return userCharacterRepository.findByUserCharacterId(userCharacterId)
                .orElseThrow(() -> new GlobalBaseException(GlobalErrorCode.USER_CHARACTER_NOT_FOUND));
    }
}
