package com.walkerholic.walkingpet.domain.auth.dto.request;

import lombok.Getter;

@Getter
public class SocialLoginDTO {
    private String socialEmail;
    private String nickname;
//    private String socialProvider;
}
