package com.walkerholic.walkingpet.domain.battle.service;

import static com.walkerholic.walkingpet.global.error.GlobalErrorCode.USER_CHARACTER_NOT_FOUND;
import static com.walkerholic.walkingpet.global.error.GlobalErrorCode.USER_DETAIL_NOT_FOUND;
import static com.walkerholic.walkingpet.global.error.GlobalErrorCode.USER_ITEM_NOT_FOUND_BOX;
import static com.walkerholic.walkingpet.global.error.GlobalErrorCode.USER_NOT_FOUND;

import com.walkerholic.walkingpet.domain.battle.dto.functionDTO.CharacterInfo;
import com.walkerholic.walkingpet.domain.battle.dto.functionDTO.UserRatingDTO;
import com.walkerholic.walkingpet.domain.battle.dto.response.BattleProgressInfo;
import com.walkerholic.walkingpet.domain.battle.dto.response.BattleResponse;
import com.walkerholic.walkingpet.domain.battle.dto.response.BattleResultInfo;
import com.walkerholic.walkingpet.domain.battle.dto.response.EnemyInfo;
import com.walkerholic.walkingpet.domain.battle.dto.response.UserBattleInfoDTO;
import com.walkerholic.walkingpet.domain.battle.function.BattleFunction;
import com.walkerholic.walkingpet.domain.character.entity.Character;
import com.walkerholic.walkingpet.domain.character.entity.UserCharacter;
import com.walkerholic.walkingpet.domain.character.function.UserCharacterFunction;
import com.walkerholic.walkingpet.domain.character.repository.CharacterRepository;
import com.walkerholic.walkingpet.domain.character.repository.UserCharacterRepository;
import com.walkerholic.walkingpet.domain.character.service.TestLevelUpService;
import com.walkerholic.walkingpet.domain.item.entity.UserItem;
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
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 배틀 보상으로 일반 경험치를 줄 때 사용하는 코드
 */

@Service
@RequiredArgsConstructor
@Slf4j
public class BattleItemService {

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
    public UserBattleInfoDTO getUserBattleInfo(Integer userId){

        Users users = usersRepository.findById(userId)
                .orElseThrow(()-> new GlobalBaseException(USER_NOT_FOUND));

        UserDetail userDetail = userDetailRepository.findUserDetailByUser(users)
                .orElseThrow(()-> new GlobalBaseException(USER_DETAIL_NOT_FOUND));

        Character character = characterRepository.findCharacterByCharacterId(userDetail.getSelectUserCharacter().getUserCharacterId())
                .orElseThrow(()-> new GlobalBaseException(USER_CHARACTER_NOT_FOUND));

        UserCharacter userCharacter = userCharacterRepository.findByUserAndCharacter(users,character)
                .orElseThrow(() -> new GlobalBaseException(USER_CHARACTER_NOT_FOUND));


        return UserBattleInfoDTO.from(userCharacter, userDetail);
    }

    //3. 배틀 시작
    public BattleResponse startBattle(int userId){
        Users users = getUser(userId);

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
        BattleResultInfo battleResultInfo = battleFunction.getBattleResult(userId);

        //배틀의 결과로 얻은 보상 데이터 저장

        System.out.println("배틀 결과로 얻은 데이터 저장");

        //배틀을 통해 얻은 상자 아이템 데이터를 저장한다.
        String box = battleResultInfo.getBattleReward().getBox();
        if(box != null){
            System.out.println("박스 획득했어요!");
            UserItem userItem = userItemRepository.findByUserItemWithUserAndItemFetch(userId, box)
                    .orElseThrow(()-> new GlobalBaseException(USER_ITEM_NOT_FOUND_BOX));
            userItem.addItemQuantity(1);
        }
        else{
            System.out.println("박스 획득 실패");
        }
        System.out.println("경치 업데이트 준비");
        //배틀을 통해 얻은 경험치 업데이트
        LevelUpResponse levelUpResponse = levelUpService.getLevelUpResponseByObject(userCharacter, battleResultInfo.getExperience());

        System.out.println("경치 업데이트 완");
        //배틀을 통해 얻은 레이팅 업데이트
        userDetail.updateBattleRating(battleResultInfo.getRating());
        userDetailRepository.save(userDetail);
        System.out.println("레이팅 업데이트 완");
        return BattleResponse.builder()
                .enemyInfo(enemyInfo)
                .battleProgressInfo(battleProgressInfo)
                .battleResultInfo(battleResultInfo)
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
        return usersRepository.findById(userId)
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
