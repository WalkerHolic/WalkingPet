package com.walkerholic.walkingpet.domain.character.service;

import com.walkerholic.walkingpet.domain.character.dto.response.UserCharacterResponse;
import com.walkerholic.walkingpet.domain.character.entity.UserCharacter;
import com.walkerholic.walkingpet.domain.character.repository.UserCharacterRepository;
import com.walkerholic.walkingpet.global.error.GlobalBaseException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.walkerholic.walkingpet.global.error.GlobalErrorCode.USER_CHARACTER_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserCharacterService {

    private final UserCharacterRepository userCharacterRepository;

    public UserCharacterResponse getUserCharacterInfo(int userCharacterId) {
        UserCharacter userCharacter = userCharacterRepository.findByUserCharacterId(userCharacterId)
                .orElseThrow(() -> new GlobalBaseException(USER_CHARACTER_NOT_FOUND));

        System.out.println(userCharacter.toString());
//        return null;
        return UserCharacterResponse.from(userCharacter);
    }
}
