package com.walkerholic.walkingpet.domain.character.service;

import com.walkerholic.walkingpet.domain.character.dto.UserCharacterListInfo;
import com.walkerholic.walkingpet.domain.character.dto.request.ChangeUserCharacterIdRequest;
import com.walkerholic.walkingpet.domain.character.dto.request.ResetInitStatusRequest;
import com.walkerholic.walkingpet.domain.character.dto.response.*;
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

import java.util.ArrayList;
import java.util.List;


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
    public UserCharacterInfoResponse getUserCharacterInfo(int userId) {
        UserDetail userDetail = userDetailRepository.findByJoinFetchByUserId(userId)
                .orElseThrow(() -> new GlobalBaseException(GlobalErrorCode.USER_DETAIL_NOT_FOUND));

        return UserCharacterInfoResponse.from(userDetail);
    }

    /**
     * 사용자가 가지고 있는 스탯 포인트로 능력치 올리기
     */
    @Transactional(readOnly = false)
    public UserCharacterStatResponse addStatPoint(int userId, String value) {
        UserDetail userDetail = userDetailRepository.findUserCharacterByUserId(userId)
                .orElseThrow(() -> new GlobalBaseException(GlobalErrorCode.USER_DETAIL_NOT_FOUND));

        UserCharacter userCharacterInfo = userDetail.getSelectUserCharacter();

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
        UserDetail userDetail = userDetailRepository.findByUserUserId(userId)
                .orElseThrow(() -> new GlobalBaseException(GlobalErrorCode.USER_NOT_FOUND));

        UserCharacter userCharacterInfo = getUserCharacter(changeUserCharacterIdRequest.getUserCharacterId());

        userDetail.changeUserCharacter(userCharacterInfo);
    }

    /**
     * 스탯 초기화 버튼 클릭
     */
    @Transactional(readOnly = false)
    public ResetStatResponse resetInitStatus(int userId) {
        UserDetail userDetail = userDetailRepository.findUserAndUserCharacterByUserId(userId)
                .orElseThrow(() -> new GlobalBaseException(GlobalErrorCode.USER_NOT_FOUND));

        if (userDetail.getInitStatus() == 1) {
            throw new GlobalBaseException(GlobalErrorCode.STAT_INIT_LIMIT_EXCEEDED);
        }

        UserCharacter userCharacterInfo = userDetail.getSelectUserCharacter();
        System.out.println("-------------------------------------------------");
        Character character = userCharacterInfo.getCharacter();

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

    /*
        사용자의 캐릭터 정보 가져오기
     */
    @Transactional(readOnly = true)
    public UserCharacterListInfoResponse getUserCharacterInfoList(int userId) {
        List<UserCharacterListInfo> userCharacterListInfos = new ArrayList<>();

        // 해당 사용자가 장착하고 있는 캐릭터 찾기
        UserDetail userDetail = userDetailRepository.findByUserUserId(userId)
                .orElseThrow(()-> new GlobalBaseException(GlobalErrorCode.USER_DETAIL_NOT_FOUND));

        // 해당 사용자가 가지고 있는 캐릭터 정보 가져오기
        List<UserCharacter> haveCharacterList = userCharacterRepository.findByUserUserId(userId);
        for (UserCharacter userCharacter : haveCharacterList) {
            if (userDetail.getSelectUserCharacter().getUserCharacterId() == userCharacter.getUserCharacterId()) {
                // 해당 사용자가 장착한 캐릭터
                userCharacterListInfos.add(UserCharacterListInfo.userCharacterFrom(userCharacter, true));
            } else {
                userCharacterListInfos.add(UserCharacterListInfo.userCharacterFrom(userCharacter, false));
            }
        }

        // 해당 사용자가 가지고 있지 않은 캐릭터 정보 가져오기
        List<Character> notHaveUserCharacterList = characterRepository.findNotHaveUserCharacterList(userId);
        for (Character userCharacter : notHaveUserCharacterList) {
            userCharacterListInfos.add(UserCharacterListInfo.characterFrom(userCharacter));
        }

        return UserCharacterListInfoResponse.from(userCharacterListInfos);
    }

    /**
     * 사용자의 캐릭터 정보 가져오기(내부 메서드)
     */
    public UserCharacter getUserCharacter(int userId) {
        UserDetail userDetail = userDetailRepository.findByJoinFetchByUserId(userId)
                .orElseThrow(() -> new GlobalBaseException(GlobalErrorCode.USER_DETAIL_NOT_FOUND));

        return userDetail.getSelectUserCharacter();
    }


}
