package com.walkerholic.walkingpet.domain.fcmtoken.service;

import com.walkerholic.walkingpet.domain.fcmtoken.entity.FcmToken;
import com.walkerholic.walkingpet.domain.fcmtoken.repository.FcmTokenRepository;
import com.walkerholic.walkingpet.domain.users.entity.Users;
import com.walkerholic.walkingpet.domain.users.repository.UsersRepository;
import com.walkerholic.walkingpet.global.error.GlobalBaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.walkerholic.walkingpet.global.error.GlobalErrorCode.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class FcmTokenService {

    private final FcmTokenRepository fcmTokenRepository;
    private final UsersRepository usersRepository;

    @Transactional(readOnly = false)
    public void saveToken(int userId, String token) {

        Users user =usersRepository.
                findById(userId)
                .orElseThrow(() -> new GlobalBaseException(USER_NOT_FOUND));


            fcmTokenRepository.save(new FcmToken(user,token));
    }

    public List<FcmToken> getAllTokens() {
        return fcmTokenRepository.findAll();
    }

}
