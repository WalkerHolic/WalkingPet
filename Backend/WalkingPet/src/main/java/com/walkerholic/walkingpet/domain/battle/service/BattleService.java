package com.walkerholic.walkingpet.domain.battle.service;

import com.walkerholic.walkingpet.domain.battle.dto.functionDTO.CharacterInfo;
import com.walkerholic.walkingpet.domain.battle.dto.functionDTO.UserRatingDTO;
import com.walkerholic.walkingpet.domain.battle.dto.response.*;
import com.walkerholic.walkingpet.domain.battle.function.BattleFunction;
import com.walkerholic.walkingpet.domain.character.entity.Character;
import com.walkerholic.walkingpet.domain.character.entity.UserCharacter;
import com.walkerholic.walkingpet.domain.character.function.UserCharacterFunction;
import com.walkerholic.walkingpet.domain.character.repository.CharacterRepository;
import com.walkerholic.walkingpet.domain.character.repository.UserCharacterRepository;
import com.walkerholic.walkingpet.domain.character.service.TestLevelUpService;
import com.walkerholic.walkingpet.domain.item.repository.ItemRepository;
import com.walkerholic.walkingpet.domain.item.repository.UserItemRepository;
import com.walkerholic.walkingpet.domain.levelup.dto.response.LevelUpResponse;
import com.walkerholic.walkingpet.domain.levelup.function.LevelUpFunction;
import com.walkerholic.walkingpet.domain.levelup.service.LevelUpService;
import com.walkerholic.walkingpet.domain.users.entity.UserDetail;
import com.walkerholic.walkingpet.domain.users.entity.Users;
import com.walkerholic.walkingpet.domain.users.repository.UserDetailRepository;
import com.walkerholic.walkingpet.domain.users.repository.UsersRepository;
import com.walkerholic.walkingpet.global.error.GlobalBaseException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.walkerholic.walkingpet.global.error.GlobalErrorCode.*;

/**
 * 배틀 보상으로 일반 경험치를 줄 때 사용하는 코드
 */

@Service
@RequiredArgsConstructor
@Slf4j
public class BattleService {

    private final UserCharacterRepository userCharacterRepository;
    private final UserDetailRepository userDetailRepository;
    private final UsersRepository usersRepository;
    private final CharacterRepository characterRepository;
    private final BattleFunction battleFunction;
    private final UserItemRepository userItemRepository;
    private final ItemRepository itemRepository;
    private final UserCharacterFunction userCharacterFunction;
    private final TestLevelUpService testLevelUpService;
    private final LevelUpService levelUpService;
    private final LevelUpFunction levelUpFunction;

    //1. 내 배틀 정보 확인
    public UserBattleInfo getUserBattleInfo(Integer userId){

        UserDetail userDetail = getUserDetail(userId);

        UserCharacter userCharacter = getUserCharacter(userDetail.getSelectUserCharacter().getUserCharacterId());

        return UserBattleInfo.from(userCharacter, userDetail);
    }

    //3. 배틀 시작
    public BattleResponse startBattle(int userId){
        Users users = usersRepository.findUsersByUserId(userId)
                .orElseThrow(() -> new GlobalBaseException(USER_NOT_FOUND));

        System.out.println(users.toString());

        UserDetail userDetail = userDetailRepository.findUserDetailByUser(users)
                .orElseThrow(()-> new GlobalBaseException(USER_DETAIL_NOT_FOUND));

        System.out.println(userDetail.toString());

        //배틀횟수 1회 차감 --> 만약 배틀횟수가 0회일때 여기서 예외처리 해줘야함!
        userDetail.battleCountDeduction();

        UserRatingDTO userRatingDTO = UserRatingDTO.builder()
                .userId(userDetail.getUser().getUserId())
                .rating(userDetail.getBattleRating())
                .build();

        Character character = characterRepository.findCharacterByCharacterId(userDetail.getSelectUserCharacter().getCharacter().getCharacterId())
                .orElseThrow(()-> new GlobalBaseException(USER_CHARACTER_NOT_FOUND));

        UserCharacter userCharacter = userCharacterRepository.findByUserAndCharacter(users,character)
                .orElseThrow(() -> new GlobalBaseException(USER_CHARACTER_NOT_FOUND));

        List<UserRatingDTO> userRatingDTOList = new ArrayList<>();

        for(UserDetail ud : userDetailRepository.findAll()){
            userRatingDTOList.add(UserRatingDTO.builder()
                    .userId(ud.getUser().getUserId())
                    .rating(ud.getBattleRating())
                    .build());
        }

        int enemyId = battleFunction.matchingBattleUser(userRatingDTO, userRatingDTOList);
        System.out.println(enemyId);
        System.out.println("배틀 상대의 캐릭터 정보 가져오기");
        //배틀 상대의 캐릭터 정보 가져오기
        EnemyInfo enemyInfo = getEnemyBattleInfo(enemyId);

        CharacterInfo userCharacterInfo = CharacterInfo.builder()
                .health(userCharacter.getHealth())
                .power(userCharacter.getPower())
                .defense(userCharacter.getDefense())
                .build();

        CharacterInfo enemyCharacterInfo = CharacterInfo.builder()
                .health(enemyInfo.getHealth())
                .power(enemyInfo.getPower())
                .defense(enemyInfo.getDefense())
                .build();

        System.out.println("배틀 function 진행!");
        BattleProgressInfo battleProgressInfo = battleFunction.getBattleProgress(userCharacterInfo, enemyCharacterInfo);
        System.out.println("배틀 결과 저장하기");
        BattleResult battleResult = battleFunction.getBattleResult(userId);


        //배틀을 통해 얻은 레이팅 업데이트
        userDetail.updateBattleRating(battleResult.getRating());
        userDetailRepository.save(userDetail);

        userCharacter.addExperience(battleResult.getExperience());
        userCharacterRepository.save(userCharacter);

        //레벨업 response
        LevelUpResponse levelUpResponse = levelUpService.getLevelUpResponseByObject(userCharacter, battleResult.getExperience());

        return BattleResponse.builder()
                .enemyInfo(enemyInfo)
                .battleProgressInfo(battleProgressInfo)
                .battleResult(battleResult)
                .levelUpResponse(levelUpResponse)
                .build();
    }

    /**
     * 상대의 배틀 정보 가져오기
     * @param enemyUserId 상대 유저 아이디
     * @return 닉네임, 캐릭터 아이디, 레이팅, 체력, 힘, 방어력
     */
    public EnemyInfo getEnemyBattleInfo(int enemyUserId){
        Users enemyUser = getUser(enemyUserId);
        int userCharacterId = getUserDetail(enemyUserId).getSelectUserCharacter().getUserCharacterId();
        UserCharacter enemyUserCharacter = getUserCharacter(userCharacterId);
        UserDetail userDetail = getUserDetail(enemyUserId);

        return EnemyInfo.builder()
            .nickname(enemyUser.getNickname())
            .characterId(enemyUserCharacter.getCharacter().getCharacterId())
            .rating(userDetail.getBattleRating())
            .health(enemyUserCharacter.getHealth())
            .power(enemyUserCharacter.getPower())
            .defense(enemyUserCharacter.getDefense())
            .build();
    }

    private Users getUser(int userId){
        return usersRepository.findUsersByUserId(userId)
            .orElseThrow(()-> new GlobalBaseException(USER_NOT_FOUND));
    }

    private UserDetail getUserDetail(int userId){
        return userDetailRepository.findByUserUserId(userId)
            .orElseThrow(()-> new GlobalBaseException(USER_DETAIL_NOT_FOUND));

    }

    private UserCharacter getUserCharacter(int userCharacterId){
        return userCharacterRepository.findByUserCharacterId(userCharacterId)
            .orElseThrow(() -> new GlobalBaseException(USER_CHARACTER_NOT_FOUND));
    }
}
