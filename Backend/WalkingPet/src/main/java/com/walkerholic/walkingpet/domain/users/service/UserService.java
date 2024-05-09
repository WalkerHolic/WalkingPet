package com.walkerholic.walkingpet.domain.users.service;

import com.walkerholic.walkingpet.domain.users.entity.UserDetail;
import com.walkerholic.walkingpet.domain.users.entity.Users;
import com.walkerholic.walkingpet.domain.users.repository.UsersRepository;
import com.walkerholic.walkingpet.global.error.GlobalBaseException;
import com.walkerholic.walkingpet.global.error.GlobalErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final LoginService loginService;
    private final UsersRepository usersRepository;

    public boolean modifyNickname(int userId, String nickname){
        if(loginService.checkAvailableNickname(nickname)){
            return false;
        }
        Users user = geUsers(userId);
        user.modifyNickname(nickname);
        usersRepository.save(user);
        return true;
    }

    private Users geUsers(int userId){
        return usersRepository.findById(userId)
                .orElseThrow(()-> new GlobalBaseException(GlobalErrorCode.USER_NOT_FOUND));
    }
}
