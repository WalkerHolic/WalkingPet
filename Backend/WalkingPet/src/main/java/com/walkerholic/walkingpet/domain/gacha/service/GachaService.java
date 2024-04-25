package com.walkerholic.walkingpet.domain.gacha.service;

import com.walkerholic.walkingpet.domain.character.entity.Character;
import com.walkerholic.walkingpet.domain.character.entity.UserCharacter;
import com.walkerholic.walkingpet.domain.character.repository.CharacterRepository;
import com.walkerholic.walkingpet.domain.character.repository.UserCharacterRepository;
import com.walkerholic.walkingpet.domain.gacha.dto.response.GachaCountResponse;
import com.walkerholic.walkingpet.domain.gacha.dto.response.GachaResultResponse;
import com.walkerholic.walkingpet.domain.item.entity.Item;
import com.walkerholic.walkingpet.domain.item.entity.UserItem;
import com.walkerholic.walkingpet.domain.item.repository.ItemRepository;
import com.walkerholic.walkingpet.domain.item.repository.UserItemRepository;
import com.walkerholic.walkingpet.domain.users.entity.Users;
import com.walkerholic.walkingpet.domain.users.repository.UsersRepository;
import com.walkerholic.walkingpet.global.error.GlobalBaseException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import static com.walkerholic.walkingpet.global.error.GlobalErrorCode.TEAM_NOT_FOUND;
import static com.walkerholic.walkingpet.global.error.GlobalErrorCode.USER_CHARACTER_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Slf4j
public class GachaService {
    private static final int NORMAL_BOX_ID = 1;
    private static final int LUXURY_BOX_ID = 2;
    private static final int UPGRADE_INCREMENT = 1;
    private static final int GRADE2_APPEAR_PERCENTAGE = 90;
    private static final int GRADE1_APPEAR_PERCENTAGE = 70;
    private static final int GRADE1 = 1;
    private static final int GRADE2 = 2;
    private static final int GRADE3 = 3;

    private final UserItemRepository userItemRepository;
    private final ItemRepository itemRepository;
    private final CharacterRepository characterRepository;
    private final UsersRepository usersRepository;
    private final UserCharacterRepository userCharacterRepository;

    @Transactional
    public GachaResultResponse getGachaResult(String boxType, int userId) {

        Users user = usersRepository.findUsersByUserId(userId)
                .orElseThrow(() -> new GlobalBaseException(USER_CHARACTER_NOT_FOUND));

        int grade = decideGrade(user,boxType);

        Character character = characterRepository.findRandomByGrade(grade)
                .orElseThrow(() -> new GlobalBaseException(USER_CHARACTER_NOT_FOUND));

        Optional<UserCharacter> userCharacter = userCharacterRepository.findByUserAndCharacter(user,character);

        if(userCharacter.isPresent()){
            userCharacter.get().setUpgrade(userCharacter.get().getUpgrade() + UPGRADE_INCREMENT);
            userCharacterRepository.save(userCharacter.get());
        }
        else{
            userCharacterRepository.save(new UserCharacter(character,user));
        }

//        if (userCharacter!=null) {
//            userCharacter.setUpgrade(userCharacter.getUpgrade() + UPGRADE_INCREMENT);
//            userCharacterRepository.save(userCharacter);
//        } else {
//            userCharacterRepository.save(new UserCharacter(character,user));
//        }

        return  GachaResultResponse.from(character);
    }

    public GachaCountResponse getGachaCount(int userId){

        List<UserItem> userItems = userItemRepository.findByUserIdWithUserFetch(userId)
                .orElseThrow(() -> new GlobalBaseException(USER_CHARACTER_NOT_FOUND));

        int normalBoxCount = userItems.stream()
                .filter(item -> item.getItem().getItemId() == NORMAL_BOX_ID)
                .mapToInt(UserItem::getQuantity)
                .sum();

        int luxuryBoxCount = userItems.stream()
                .filter(item -> item.getItem().getItemId() == LUXURY_BOX_ID)
                .mapToInt(UserItem::getQuantity)
                .sum();

        return GachaCountResponse.from(normalBoxCount,luxuryBoxCount);
    }

    // 상자 타입에 따라 확률 계산을 해준 뒤, 몇 성의 캐릭터를 뽑아야할지 Return 하는 함수
    public int decideGrade(Users user,String boxType){

        int randomNumber = new Random(System.currentTimeMillis()).nextInt(100) + 1;
        int grade;

        if (boxType.equals("normal")) {

            Item item = itemRepository.findById(1)
                    .orElseThrow(() -> new RuntimeException("Item not found"));
            UserItem userItem = userItemRepository.findByUserAndItem(user, item)
                    .orElseThrow(() -> new RuntimeException("UserItem not found"));

            if (userItem.getQuantity() > 0) {
                userItem.setQuantity(userItem.getQuantity() - 1); // 수량 감소
                userItemRepository.save(userItem); // 변경 사항 저장
            } else {
                throw new GlobalBaseException(TEAM_NOT_FOUND);
            }

            grade = (randomNumber <= GRADE2_APPEAR_PERCENTAGE) ? GRADE2 : GRADE3;

        } else {

            Item item = itemRepository.findById(2)
                    .orElseThrow(() -> new RuntimeException("Item not found"));
            UserItem userItem = userItemRepository.findByUserAndItem(user, item)
                    .orElseThrow(() -> new RuntimeException("UserItem not found"));

            if (userItem.getQuantity() > 0) {
                userItem.setQuantity(userItem.getQuantity() - 1);
                userItemRepository.save(userItem);
            } else {
                throw new GlobalBaseException(TEAM_NOT_FOUND);
            }

            grade = (randomNumber <= GRADE1_APPEAR_PERCENTAGE) ? GRADE1 : GRADE2;

        }
        return grade;
    }
}
