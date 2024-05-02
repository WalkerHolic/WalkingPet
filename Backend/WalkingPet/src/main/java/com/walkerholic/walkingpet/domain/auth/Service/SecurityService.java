package com.walkerholic.walkingpet.domain.auth.Service;

import com.walkerholic.walkingpet.domain.auth.dto.CustomUserDetail;
import com.walkerholic.walkingpet.domain.auth.dto.request.SocialLoginDTO;
import com.walkerholic.walkingpet.domain.auth.util.JwtUtil;
import com.walkerholic.walkingpet.domain.users.entity.Users;
import com.walkerholic.walkingpet.domain.users.repository.UsersRepository;
import com.walkerholic.walkingpet.global.error.GlobalBaseException;
import com.walkerholic.walkingpet.global.error.GlobalErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;

/*
    1.AuthenticationProvider에서 UserDetailsService 인터페이스를 통해 loadUserByUsername을 호출
    2. loadUserByUsername은 유저 정보를 db에서 불러온 후 해당 유저 정보를 검증
    3. 인증이 성공할 경우 해당 유저정보로 authentication객체를 생성한 후 securityContext에 넣어줌. 그 후 AuthenticationSuccessHandle을 실행
    4. 실패할 경우 AuthenticationFailureHandler을 실행
    5. 그 뒤에는 토큰 검증 등의 작업
 */
@Service
@RequiredArgsConstructor
public class SecurityService {
    private final UsersRepository usersRepository;
    private final JwtUtil jwtUtil;

    public void saveUserInSecurityContext(SocialLoginDTO socialLoginDTO) {
        String socialId = socialLoginDTO.getSocialEmail();
//        String socialProvider = socialLoginDTO.getSocialProvider();
        saveUserInSecurityContext(socialId);
    }

    private void saveUserInSecurityContext(String socialId) {
        UserDetails userDetails = loadUserBySocialIdAndSocialProvider(socialId);
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, authorities);

        if(authentication != null) {
            SecurityContext context = SecurityContextHolder.createEmptyContext();
            context.setAuthentication(authentication);
            SecurityContextHolder.setContext(context);
        }
    }


    private UserDetails loadUserBySocialIdAndSocialProvider(String socialId) {
        Users user = usersRepository.findByEmail(socialId);

        if(user == null) {
            throw new GlobalBaseException(GlobalErrorCode.TOKEN_EXPIRED);
        } else {
            CustomUserDetail userDetails = new CustomUserDetail();
            userDetails.setUser(user);
            return userDetails;
        }

    }
}
