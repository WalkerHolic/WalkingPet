package com.walkerholic.walkingpet.domain.users.service;

import com.walkerholic.walkingpet.domain.users.entity.UserDetail;
import com.walkerholic.walkingpet.domain.users.entity.Users;
import com.walkerholic.walkingpet.domain.users.repository.UserDetailRepository;
import com.walkerholic.walkingpet.domain.users.repository.UsersRepository;
import com.walkerholic.walkingpet.global.error.GlobalBaseException;
import com.walkerholic.walkingpet.global.error.GlobalErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.FormatFlagsConversionMismatchException;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final LoginService loginService;
    private final UsersRepository usersRepository;
    private final UserDetailRepository userDetailRepository;

    public boolean modifyNickname(int userId, String nickname){
        if(loginService.checkAvailableNickname(nickname)){
            return false;
        }
        Users user = getUsers(userId);
        user.modifyNickname(nickname);
        usersRepository.save(user);
        return true;
    }

    public void setBattleRating(){
        int battleRating = -300;
        List<Users> usersList = usersRepository.findAll();
        for(int i = 1; i < usersList.size()+1; i++){
            if(i != 31 && i != 32 && i != 33 && i != 34 && i != 35 && i != 36 && i != 37){
                UserDetail userDetail = getUserDetail(i);
                //배틀 레이팅 초기화
//                userDetail.setBattleRating(1000);
                userDetail.updateBattleRating(battleRating);
                userDetailRepository.save(userDetail);
                battleRating += 3;
            }
        }
    }

    private Users getUsers(int userId){
        return usersRepository.findById(userId)
                .orElseThrow(()-> new GlobalBaseException(GlobalErrorCode.USER_NOT_FOUND));
    }

    private UserDetail getUserDetail(int userId){
        return userDetailRepository.findUserDetailByJoinFetchByUserId(userId)
                .orElseThrow(()-> new GlobalBaseException(GlobalErrorCode.USER_DETAIL_NOT_FOUND));
    }
}
