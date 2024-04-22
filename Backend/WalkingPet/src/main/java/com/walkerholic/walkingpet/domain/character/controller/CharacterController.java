package com.walkerholic.walkingpet.domain.character.controller;

import com.walkerholic.walkingpet.domain.character.dto.response.UserCharacterResponse;
import com.walkerholic.walkingpet.domain.character.service.UserCharacterService;
import com.walkerholic.walkingpet.global.error.GlobalSuccessCode;
import com.walkerholic.walkingpet.global.error.response.CommonResponseEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.walkerholic.walkingpet.global.error.response.CommonResponseEntity.toResponseEntity;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/character")
public class CharacterController {

    private final UserCharacterService characterService;

    @GetMapping
    public ResponseEntity<CommonResponseEntity> getUserCharacterInfo(@RequestParam int userCharacterId) {
        log.info("CharacterController getUserCharacterInfo - userCharacterId: {}", userCharacterId);

        UserCharacterResponse userCharacterInfo = characterService.getUserCharacterInfo(userCharacterId);
        return CommonResponseEntity.toResponseEntity(GlobalSuccessCode.SUCCESS, userCharacterInfo);
    }

    @GetMapping("/test")
    public ResponseEntity<CommonResponseEntity> test() {
        System.out.println("통신 테스트");
        return CommonResponseEntity.toResponseEntity(GlobalSuccessCode.SUCCESS, "통신 테스트");
    }

}
