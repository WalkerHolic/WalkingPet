package com.walkerholic.walkingpet.domain.gacha.service;

import com.walkerholic.walkingpet.domain.character.entity.Character;
import com.walkerholic.walkingpet.domain.character.entity.UserCharacter;
import com.walkerholic.walkingpet.domain.character.repository.CharacterRepository;
import com.walkerholic.walkingpet.domain.character.repository.UserCharacterRepository;
import com.walkerholic.walkingpet.domain.gacha.dto.response.GachaCountResponse;
import com.walkerholic.walkingpet.domain.gacha.dto.response.GachaResultResponse;
import com.walkerholic.walkingpet.domain.item.entity.UserItem;
import com.walkerholic.walkingpet.domain.item.repository.UserItemRepository;
import com.walkerholic.walkingpet.domain.users.entity.UserDetail;
import com.walkerholic.walkingpet.domain.users.entity.Users;
import com.walkerholic.walkingpet.domain.users.repository.UsersRepository;
import com.walkerholic.walkingpet.global.error.GlobalBaseException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static com.walkerholic.walkingpet.global.error.GlobalErrorCode.USER_CHARACTER_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Slf4j
public class GachaService {

    private final UserItemRepository userItemRepository;
    private final CharacterRepository characterRepository;
    private final UsersRepository usersRepository;
    private final UserCharacterRepository userCharacterRepository;

    @Transactional
    public GachaResultResponse getGachaResult(String boxType, int userId) {

        int randomNumber = new Random(System.currentTimeMillis()).nextInt(100) + 1;
        int grade;
        if (boxType.equals("luxury")) {
            grade = (randomNumber <= 90) ? 2 : 3;
        } else {
            grade = (randomNumber <= 70) ? 1 : 2;
        }

        Character character = characterRepository.findRandomByGrade(grade)
                .orElseThrow(() -> new GlobalBaseException(USER_CHARACTER_NOT_FOUND));
        Users user = usersRepository.findUsersByUserId(userId)
                .orElseThrow(() -> new GlobalBaseException(USER_CHARACTER_NOT_FOUND));

        UserCharacter userCharacter = userCharacterRepository.findByUserAndCharacter(user,character);

        if (userCharacter!=null) {
            userCharacter.setUpgrade(userCharacter.getUpgrade() + 1);
            userCharacterRepository.save(userCharacter);
        } else {
            userCharacterRepository.save(new UserCharacter(character,user));
        }

        return  GachaResultResponse.from(character);
    }

    public GachaCountResponse getGachaCount(int userId){

       List<UserItem> userItems = userItemRepository.findByUserIdWithUserFetch(userId)
                .orElseThrow(() -> new GlobalBaseException(USER_CHARACTER_NOT_FOUND));

        int normalBoxCount = userItems.stream()
                .filter(item -> item.getItem().getItemId() == 1)
                .mapToInt(UserItem::getQuantity)
                .sum();

        int luxuryBoxCount = userItems.stream()
                .filter(item -> item.getItem().getItemId() == 2)
                .mapToInt(UserItem::getQuantity)
                .sum();

        return GachaCountResponse.from(normalBoxCount,luxuryBoxCount);
    }
}
