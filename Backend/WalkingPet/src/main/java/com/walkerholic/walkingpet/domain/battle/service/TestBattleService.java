package com.walkerholic.walkingpet.domain.battle.service;

import com.walkerholic.walkingpet.domain.battle.dto.response.UserBattleInfoDTO;
import com.walkerholic.walkingpet.domain.character.entity.UserCharacter;
import com.walkerholic.walkingpet.domain.character.repository.UserCharacterRepository;
import com.walkerholic.walkingpet.domain.users.entity.UserDetail;
import com.walkerholic.walkingpet.domain.users.entity.Users;
import com.walkerholic.walkingpet.domain.users.repository.UserDetailRepository;
import com.walkerholic.walkingpet.domain.users.repository.UsersRepository;
import com.walkerholic.walkingpet.global.error.GlobalBaseException;
import com.walkerholic.walkingpet.global.error.GlobalErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class TestBattleService {
    private final UsersRepository usersRepository;
    private final UserCharacterRepository userCharacterRepository;
    private final UserDetailRepository userDetailRepository;

    //1. 사용자의 배틀 정보 반환
//    public UserBattleInfoDTO getUserBattleInfo(int userId){
//
//        return
//    }

    //2. 배틀 결과 반환







    private Users getUserById(int userId){
        return usersRepository.findById(userId)
            .orElseThrow(()-> new GlobalBaseException(GlobalErrorCode.USER_NOT_FOUND));
    }
}
