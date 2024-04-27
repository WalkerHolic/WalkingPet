package com.walkerholic.walkingpet.domain.character.service;

import com.walkerholic.walkingpet.domain.character.dto.request.ChangeUserCharacterIdRequest;
import com.walkerholic.walkingpet.domain.character.dto.request.ResetInitStatusRequest;
import com.walkerholic.walkingpet.domain.character.dto.response.ResetStatResponse;
import com.walkerholic.walkingpet.domain.character.dto.response.UserCharacterInfoResponse;
import com.walkerholic.walkingpet.domain.character.dto.response.UserCharacterStatResponse;
import com.walkerholic.walkingpet.domain.character.dto.response.UserStepResponse;
import com.walkerholic.walkingpet.domain.character.entity.Character;
import com.walkerholic.walkingpet.domain.character.entity.UserCharacter;
import com.walkerholic.walkingpet.domain.character.repository.CharacterRepository;
import com.walkerholic.walkingpet.domain.character.repository.UserCharacterRepository;
import com.walkerholic.walkingpet.domain.users.entity.UserDetail;
import com.walkerholic.walkingpet.domain.users.entity.UserStep;
import com.walkerholic.walkingpet.domain.users.repository.UserDetailRepository;
import com.walkerholic.walkingpet.domain.users.repository.UserStepRepository;
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

    private static final int REDUCE_STAT_POINT = 1; // 스탯 사용시 줄어 드는 스탯 포인트
    private static final int ADD_STAT = 1; // 스탯 사용시 줄어 드는 스탯 포인트

    private final UserCharacterRepository userCharacterRepository;
    private final UserDetailRepository userDetailRepository;
    private final CharacterRepository characterRepository;
    private final UserStepRepository userStepRepository;

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
        //TODO: 인가 처리 -> userId 값도 받아야할듯
        UserCharacter userCharacterInfo = getUserCharacter(userCharacterId);

        if (userCharacterInfo.getStatPoint() < REDUCE_STAT_POINT) {
            throw new GlobalBaseException(GlobalErrorCode.INSUFFICIENT_STAT_POINT);
        }

        userCharacterInfo.useStatPoint(REDUCE_STAT_POINT);
        if (value.equals("health")) {
            userCharacterInfo.raiseHealth(ADD_STAT);
        } else if (value.equals("defense")) {
            userCharacterInfo.raiseDefense(ADD_STAT);
        } else if (value.equals("power")) {
            userCharacterInfo.raisePower(ADD_STAT);
        }

        return UserCharacterStatResponse.from(userCharacterInfo);
    }

    /**
     * 사용자의 캐릭터 변경 메서드
     */
    @Transactional(readOnly = false)
    public void changeUserCharacter(int userId, ChangeUserCharacterIdRequest changeUserCharacterIdRequest) {
        //TODO: 인가처리
        UserDetail userDetail = userDetailRepository.findByUserUserId(userId)
                .orElseThrow(() -> new GlobalBaseException(GlobalErrorCode.USER_NOT_FOUND));

        UserCharacter userCharacterInfo = getUserCharacter(changeUserCharacterIdRequest.getUserCharacterId());

        userDetail.changeUserCharacter(userCharacterInfo);
    }

    /**
     * 스탯 초기화 버튼 클릭
     */
    @Transactional(readOnly = false)
    public ResetStatResponse resetInitStatus(ResetInitStatusRequest resetInitStatusRequest) {
        //TODO: userId 를 받아서 인가처리
        UserCharacter userCharacterInfo = getUserCharacter(resetInitStatusRequest.getUserCharacterId());
        Character character = characterRepository.findByCharacterId(userCharacterInfo.getCharacter().getCharacterId())
                .orElseThrow(() -> new GlobalBaseException(GlobalErrorCode.CHARACTER_NOT_FOUND));
//        UserDetail userDetail = userDetailRepository.findBySelectUserCharacterUserCharacterId(resetInitStatusRequest.getUserCharacterId())
        UserDetail userDetail = userDetailRepository.findUserDetailByUser(userCharacterInfo.getUser())
                .orElseThrow(() -> new GlobalBaseException(GlobalErrorCode.USER_NOT_FOUND));

        if (userDetail.getInitStatus() == 1) {
            throw new GlobalBaseException(GlobalErrorCode.STAT_INIT_LIMIT_EXCEEDED);
        }

        int resetStatPoint = userCharacterInfo.getStatPoint();
        resetStatPoint += userCharacterInfo.getPower() - character.getFixPower();
        resetStatPoint += userCharacterInfo.getHealth() - character.getFixHealth();
        resetStatPoint += userCharacterInfo.getDefense() - character.getFixDefense();

        userCharacterInfo.resetStat(resetStatPoint, character.getFixPower(), character.getFixDefense(), character.getFixHealth());
        userDetail.changeInitStatus();

        return ResetStatResponse.from(userCharacterInfo);
    }

    /**
     * 유저의 일일 걸음수 가져오기
     */
    public UserStepResponse checkUserStep(int userId, int frontStep) {
        UserStep userStep = userStepRepository.findUserStepByUserUserId(userId)
                .orElseThrow(() -> new GlobalBaseException(GlobalErrorCode.USER_STEP_NOT_FOUND));

        // 휴대폰이 재부팅 될 때를 가정
        if (frontStep < userStep.getDailyStep()) {
            return UserStepResponse.from(userStep.getDailyStep(), true);
        } else {
            return UserStepResponse.from(frontStep, false);
        }
    }

    /**
     * 유저의 걸음수 저장
     */
    @Transactional(readOnly = false)
    public void saveUserStep(int userId, int frontStep) {
        UserStep userStep = userStepRepository.findUserStepByUserUserId(userId)
                .orElseThrow(() -> new GlobalBaseException(GlobalErrorCode.USER_STEP_NOT_FOUND));

        userStep.updateDailyStep(frontStep);
    }

    /**
     * 사용자의 캐릭터 정보 가져오기(내부 메서드)
     */
    public UserCharacter getUserCharacter(int userCharacterId) {
        return userCharacterRepository.findByUserCharacterId(userCharacterId)
                .orElseThrow(() -> new GlobalBaseException(GlobalErrorCode.USER_CHARACTER_NOT_FOUND));
    }
}
