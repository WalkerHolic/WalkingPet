package com.walkerholic.walkingpet.domain.auth.dto.request;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class SocialLoginDTO {
    private String socialEmail;
    private String nickname;
//    private String socialProvider;
}
