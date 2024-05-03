package com.walkerholic.walkingpet.domain.users.service;

import com.walkerholic.walkingpet.domain.users.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class LoginService {

    private final UsersRepository usersRepository;
    public boolean checkIsMember(String userEmail){
        return usersRepository.existsByEmail(userEmail);
    }

    public boolean checkAvailableNickname(String nickname) {
        return usersRepository.existsByNickname(nickname);
    }
}
