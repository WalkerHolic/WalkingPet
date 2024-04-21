package com.walkerholic.walkingpet.domain.character.controller;

import com.walkerholic.walkingpet.domain.character.dto.response.UserCharacterResponse;
import com.walkerholic.walkingpet.domain.character.service.UserCharacterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/character")
public class CharacterController {

    private final UserCharacterService characterService;

    @GetMapping
    public ResponseEntity<UserCharacterResponse> getUserCharacterInfo(@RequestParam int userCharacterId) {
        log.info("CharacterController getUserCharacterInfo - userCharacterId: {}", userCharacterId);

        UserCharacterResponse userCharacterInfo = characterService.getUserCharacterInfo(userCharacterId);
        return new ResponseEntity<>(userCharacterInfo, HttpStatus.OK);
    }

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        System.out.println("통신 테스트");
        return new ResponseEntity<>("통신 테스트", HttpStatus.OK);
    }

}
