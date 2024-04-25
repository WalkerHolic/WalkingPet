package com.walkerholic.walkingpet.domain.gacha.service;

import com.walkerholic.walkingpet.domain.character.entity.Character;
import com.walkerholic.walkingpet.domain.character.entity.UserCharacter;
import com.walkerholic.walkingpet.domain.character.repository.CharacterRepository;
import com.walkerholic.walkingpet.domain.character.repository.UserCharacterRepository;
import com.walkerholic.walkingpet.domain.gacha.dto.response.GachaCountResponse;
import com.walkerholic.walkingpet.domain.gacha.dto.response.GachaResultResponse;
import com.walkerholic.walkingpet.domain.gacha.function.GachaFunction;
import com.walkerholic.walkingpet.domain.item.entity.UserItem;
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

import static com.walkerholic.walkingpet.global.error.GlobalErrorCode.USER_CHARACTER_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Slf4j
public class GachaService {
    private static final int NORMAL_BOX_ID = 1;
    private static final int LUXURY_BOX_ID = 2;
    private static final int UPGRADE_INCREMENT = 1;

    private final UserItemRepository userItemRepository;
    private final CharacterRepository characterRepository;
    private final UsersRepository usersRepository;
    private final UserCharacterRepository userCharacterRepository;

    @Transactional
    public GachaResultResponse getGachaResult(String boxType, int userId) {

        int grade = GachaFunction.decideGrade(boxType);

        Character character = characterRepository.findRandomByGrade(grade)
                .orElseThrow(() -> new GlobalBaseException(USER_CHARACTER_NOT_FOUND));
        Users user = usersRepository.findUsersByUserId(userId)
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
}
