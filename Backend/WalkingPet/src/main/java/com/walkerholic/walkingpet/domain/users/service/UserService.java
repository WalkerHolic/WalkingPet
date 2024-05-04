package com.walkerholic.walkingpet.domain.users.service;

import com.walkerholic.walkingpet.domain.auth.dto.request.SocialLoginDTO;
import com.walkerholic.walkingpet.domain.users.dto.UsersDto;
import com.walkerholic.walkingpet.domain.users.entity.Users;
import com.walkerholic.walkingpet.domain.users.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UsersRepository usersRepository;

    // 해당 사용자가 존재하면 그 데이터를 반환하고 없으면 가입 후 데이터 반환
    @Transactional(readOnly = false)
    public UsersDto socialLogin(SocialLoginDTO socialLoginDTO) {
        Optional<Users> user = usersRepository.findByEmail(socialLoginDTO.getSocialEmail());
        if (user.isEmpty()) {
            System.out.println("socialLogin - 첫 유저");
            // 회원가입 로직
            // TODO: 회원가입 데이터 세팅
            Users saveUser = usersRepository.save(Users.createNewMember(socialLoginDTO.getSocialEmail(), socialLoginDTO.getNickname()));
            return UsersDto.from(saveUser);
        } else {
            System.out.println("socialLogin - 이미 가입한 유저");
            return UsersDto.from(user.get());
        }
    }
}
