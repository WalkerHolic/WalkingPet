package com.walkerholic.walkingpet.domain.battle.service;

import com.walkerholic.walkingpet.domain.battle.dto.function.CharacterInfo;
import com.walkerholic.walkingpet.domain.battle.dto.response.*;
import com.walkerholic.walkingpet.domain.battle.function.BattleFunction;
import com.walkerholic.walkingpet.domain.character.entity.UserCharacter;
import com.walkerholic.walkingpet.domain.character.repository.UserCharacterRepository;
import com.walkerholic.walkingpet.domain.levelup.dto.response.LevelUpResponse;
import com.walkerholic.walkingpet.domain.levelup.service.LevelUpService;
import com.walkerholic.walkingpet.domain.users.entity.UserDetail;
import com.walkerholic.walkingpet.domain.users.entity.Users;
import com.walkerholic.walkingpet.domain.users.repository.UserDetailRepository;
import com.walkerholic.walkingpet.domain.users.repository.UsersRepository;
import com.walkerholic.walkingpet.global.error.GlobalBaseException;
import com.walkerholic.walkingpet.global.error.GlobalErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@Slf4j
@RequiredArgsConstructor
public class BattleService {
    static final int STANDARD_RATING_MATCHING_GAP = 20;    //레이딩 범위 상수

    private final UsersRepository usersRepository;
    private final UserCharacterRepository userCharacterRepository;
    private final UserDetailRepository userDetailRepository;

    private final BattleFunction battleFunction;
    private final LevelUpService levelUpService;

    /**
     * 사용자의 배틀 정보 반환
     * @param userId 유저 아이디
     * @return UserBattleInfo(유저 id, 유저 레이팅, 유저캐릭터 아이디, 캐릭터 아이디)
     */
    //1. 사용자의 배틀 정보 반환
    public UserBattleInfoDTO getUserBattleInfo(int userId){
        UserDetail userDetail = getUserDetail(userId);

        return UserBattleInfoDTO.from(userDetail);
    }

    //2. 배틀 결과 반환
    public BattleResponseDTO getBattleResponse(int userId){
        UserDetail userDetail = getAllInfoByUserDetail(userId);

        //여기서 배틀 횟수 차감하는걸 생각해줘야함.
        try {
            if(userDetail.getBattleCount() > 0){
                //배틀 횟수 1회 차감
                userDetail.battleCountDeduction();

                UserCharacter userCharacter = userDetail.getSelectUserCharacter();
                List<UserDetail> userDetailList = userDetailRepository.findAll();
                int enemyUserId = matchingBattleUser(userId, userDetail.getBattleRating(), userDetailList);
                UserDetail enemyUserDetail = getAllInfoByUserDetail(enemyUserId);

                //1. 적 유저 캐릭터 정보
                EnemyInfo enemyInfo = EnemyInfo.from(enemyUserDetail);

                //2. 배틀 전투 과정
                CharacterInfo userCharacterInfo = getCharacterInfo(userCharacter);
                CharacterInfo enemyCharacterInfo = getCharacterInfo(enemyUserDetail.getSelectUserCharacter());
                BattleProgressInfo battleProgressInfo = battleFunction.getBattleProgress(userCharacterInfo, enemyCharacterInfo);

                //3. 배틀 결과 가져오기
                BattleResultInfo battleResultInfo = battleFunction.getBattleResult(userDetail);

                //4. 배틀 결과 저장하기
                saveBattleResult(userDetail, battleResultInfo);

                //5. 레벨업 여부 확인하기
                LevelUpResponse levelUpResponse = levelUpService.getLevelUpResponseByObject(userId, userCharacter, battleResultInfo.getRewardExperience());


                return BattleResponseDTO.builder()
                        .enemyInfo(enemyInfo)
                        .battleProgressInfo(battleProgressInfo)
                        .battleResultInfo(battleResultInfo)
                        .levelUpResponse(levelUpResponse)
                        .build();
            }
            else throw new GlobalBaseException(GlobalErrorCode.USER_BATTLE_COUNT_LACK);
        }
        catch (GlobalBaseException globalBaseException){
            globalBaseException.printStackTrace(); // 예외를 콘솔에 출력하여 로깅
            throw globalBaseException;
        }
    }

    /**
     * 레이팅이 비슷한 유저와 매칭시키는 함수
     * @param userId 배틀 시작한 유저 아이디
     * @param userRating 유저 레이팅 점수
     * @param userDetailList 유저 상세정보 리스트
     * @return 매칭이 확정된 유저의 유저아이디
     */
    public int matchingBattleUser(int userId, int userRating, List<UserDetail> userDetailList){

        //레이딩 범위
        int ratingGap = STANDARD_RATING_MATCHING_GAP;

        //매칭이 되는 유저의 id 리스트
        List<Integer> enemyUserIdList = new ArrayList<>();

        do{
            for(UserDetail enemyList : userDetailList){
                int enemyRating = enemyList.getBattleRating();
                int enemyUserId = enemyList.getUser().getUserId();

                //본인을 제외하면서
                if(enemyUserId != userId){
                    //레이팅 범위 내라면~
                    if(userRating-ratingGap <= enemyRating || enemyRating <= userRating + ratingGap)
                        enemyUserIdList.add(enemyUserId);
                }
            }

            //만약 해당 레이팅에 검색된 유저가 없다면 매칭 레이팅 범위를 늘린다.
            ratingGap += STANDARD_RATING_MATCHING_GAP;

        }while(enemyUserIdList.isEmpty());

        System.out.print("mathcing user Id list =");
        System.out.println(enemyUserIdList.toString());

        int enemyCount = enemyUserIdList.size();
        Random random = new Random();

        int enemyUserIdPos = random.nextInt(enemyCount-1);

        //매칭 확정된 적 유저 아이디 반환
        return enemyUserIdList.get(enemyUserIdPos);
    }

    /**
     * UserCharacter 받아서 전투 과정 정보를 얻기 위해 필요한 체/공/방 반환하는 함수
     * @param userCharacter 체/공/방을 얻을 유저캐릭터
     * @return CharacterInfo(유저 캐릭터의 체/공/방)
     */
    public CharacterInfo getCharacterInfo(UserCharacter userCharacter){
        return  CharacterInfo.builder()
                .health(userCharacter.getHealth())
                .power(userCharacter.getPower())
                .defense(userCharacter.getDefense())
                .build();

    }

    public void saveBattleResult(UserDetail userDetail, BattleResultInfo battleResultInfo){
        userDetail.updateBattleRating(battleResultInfo.getRewardRating());
        userDetailRepository.save(userDetail);

        UserCharacter selectUserCharacter = userDetail.getSelectUserCharacter();
        selectUserCharacter.addExperience(battleResultInfo.getRewardExperience());
        userCharacterRepository.save(selectUserCharacter);
    }

    /**
     * userId로 Users 가져오는 메소드
     * @param userId 유저 아이디
     * @return Users
     */
    private Users getUserById(int userId){
        return usersRepository.findById(userId)
            .orElseThrow(()-> new GlobalBaseException(GlobalErrorCode.USER_NOT_FOUND));
    }

    /**
     * userId로 UserDetail 찾기
     * @param userId 유저 아이디
     * @return UserDetail
     */
    private UserDetail getUserDetail(int userId){
        return userDetailRepository.findByUserUserId(userId)
                .orElseThrow(()-> new GlobalBaseException(GlobalErrorCode.USER_DETAIL_NOT_FOUND));
    }

    /**
     * userId로 UserCharacter 정보를 가지고 있는 UserDetail 가져오는 메소드
     * @param userId 유저 아이디
     * @return UserDetail(UserCharacter 정보 포함)
     */
    private UserDetail getAllInfoByUserDetail(int userId){
        return userDetailRepository.findByJoinFetchByUserId(userId)
                .orElseThrow(()-> new GlobalBaseException(GlobalErrorCode.USER_DETAIL_NOT_FOUND));
    }

    /**
     * userId와 userCharacterId로 UserCharacter 가져오기
     * @param userId 유저 아이디
     * @param userCharacterId 유저 캐릭터 아이디
     * @return UserCharacter
     */
    private UserCharacter getUserCharacter(int userId, int userCharacterId){
        return userCharacterRepository.findUserCharacterByUserIdAndUserCharacterId(userId, userCharacterId)
                .orElseThrow(()-> new GlobalBaseException(GlobalErrorCode.USER_CHARACTER_NOT_FOUND));
    }
}