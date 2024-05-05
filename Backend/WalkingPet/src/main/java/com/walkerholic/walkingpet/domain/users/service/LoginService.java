package com.walkerholic.walkingpet.domain.users.service;

import com.walkerholic.walkingpet.global.auth.dto.request.SocialLoginDTO;
import com.walkerholic.walkingpet.domain.users.dto.UsersDto;
import com.walkerholic.walkingpet.domain.users.entity.Users;
import com.walkerholic.walkingpet.domain.users.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class LoginService {

    private final UsersRepository usersRepository;
    private final SignInService signInService;
    public boolean checkIsMember(String userEmail){
        return usersRepository.existsByEmail(userEmail);
    }

    public boolean checkAvailableNickname(String nickname) {
        return usersRepository.existsByNickname(nickname);
    }

    // 해당 사용자가 존재하면 그 데이터를 반환하고 없으면 가입 후 데이터 반환
    @Transactional(readOnly = false)
    public UsersDto socialLogin(SocialLoginDTO socialLoginDTO) {
        Optional<Users> user = usersRepository.findByEmail(socialLoginDTO.getSocialEmail());
        
        if (user.isEmpty()) {
            // 회원가입 로직
            System.out.println("socialLogin - 첫 유저");

            Users saveUser = usersRepository.save(Users.createNewMember(socialLoginDTO.getSocialEmail(), socialLoginDTO.getNickname()));
            signInService.signInInItTable(socialLoginDTO.getSocialEmail());

            return UsersDto.from(saveUser);
        } else {
            // 로그인 로직
            System.out.println("socialLogin - 이미 가입한 유저");
            return UsersDto.from(user.get());
        }
    }
}
