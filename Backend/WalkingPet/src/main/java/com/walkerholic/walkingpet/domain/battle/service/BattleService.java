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

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.walkerholic.walkingpet.global.error.GlobalErrorCode.*;


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

        Users users = usersRepository.findUsersByUserId(userId)
                .orElseThrow(()-> new GlobalBaseException(USER_NOT_FOUND));

        UserDetail userDetail = userDetailRepository.findUserDetailByUser(users)
                .orElseThrow(()-> new GlobalBaseException(USER_DETAIL_NOT_FOUND));

        Character character = characterRepository.findCharacterByCharacterId(userDetail.getSelectUserCharacter().getUserCharacterId())
                .orElseThrow(()-> new GlobalBaseException(USER_CHARACTER_NOT_FOUND));

        UserCharacter userCharacter = userCharacterRepository.findByUserAndCharacter(users,character)
                .orElseThrow(() -> new GlobalBaseException(USER_CHARACTER_NOT_FOUND));


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

        Character character = characterRepository.findCharacterByCharacterId(userDetail.getSelectUserCharacter().getUserCharacterId())
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

        //배틀의 결과로 얻은 보상 데이터 저장

        System.out.println("배틀 결과로 얻은 데이터 저장");
        //배틀을 통해 얻은 경험치 아이템 데이터를 저장한다.
        //추후 업데이트 될 경험치 아이템
//        Item expItem = itemRepository.findItemByItemName("경험치 아이템")
//                .orElseThrow(() -> new GlobalBaseException(USER_ITEM_NOT_FOUND_EXP));
//
//        int expItemRewardQuantity = battleResult.getBattleReward().getExpItem();
//        System.out.println("exp 아이템의 양 = " + expItemRewardQuantity);

//        Optional<UserItem> userExpItem = userItemRepository.findByUserItemWithUserAndItemFetch(userId, expItem.getName());
//        if(userExpItem.isPresent()){
//            userExpItem.get().addItemQuantity(expItemRewardQuantity);
//            userItemRepository.save(userExpItem.get());
//        }
//        else{
//            UserItem userItem = new UserItem(users, expItem, expItemRewardQuantity);
//            userItemRepository.save(userItem);
//        }

        //배틀을 통해 얻은 상자 아이템 데이터를 저장한다.
        String box = battleResult.getBattleReward().getBox();
        if(box != null){
            System.out.println("박스 획득했어요!");
            UserItem userItem = userItemRepository.findByUserItemWithUserAndItemFetch(userId, box)
                    .orElseThrow(()-> new GlobalBaseException(USER_ITEM_NOT_FOUND_BOX));
            userItem.addItemQuantity(1);
        }
        else{
            System.out.println("박스 획득 실패");
        }

        //배틀을 통해 얻은 경험치 업데이트
        LevelUpResponse levelUpResponse = levelUpService.getLevelUpResponse(userId, battleResult.getExperience());

        //배틀을 통해 얻은 레이팅 업데이트
        userDetail.updateBattleRating(battleResult.getRating());
        userDetailRepository.save(userDetail);

        return BattleResponse.builder()
                .enemyInfo(enemyInfo)
                .battleProgressInfo(battleProgressInfo)
                .battleResult(battleResult)
                .levelUpResponse(levelUpResponse)
                .build();
    }

    //상대 배틀 정보 가져오기
    public EnemyInfo getEnemyBattleInfo(int userId){
        Users users = usersRepository.findUsersByUserId(userId)
                .orElseThrow(()-> new GlobalBaseException(USER_NOT_FOUND));

        UserDetail userDetail = userDetailRepository.findUserDetailByUser(users)
                .orElseThrow(()-> new GlobalBaseException(USER_DETAIL_NOT_FOUND));

        Character character = characterRepository.findCharacterByCharacterId(userDetail.getSelectUserCharacter().getUserCharacterId())
                .orElseThrow(()-> new GlobalBaseException(USER_CHARACTER_NOT_FOUND));

        UserCharacter userCharacter = userCharacterRepository.findByUserAndCharacter(users,character)
                .orElseThrow(() -> new GlobalBaseException(USER_CHARACTER_NOT_FOUND));

        return EnemyInfo.from(userCharacter, userDetail);
    }

    //배틀로 얻은 보상 저장하기
//    public void updateUserItem()
}
