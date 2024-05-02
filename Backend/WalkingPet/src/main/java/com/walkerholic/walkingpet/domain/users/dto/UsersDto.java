package com.walkerholic.walkingpet.domain.users.dto;

import com.walkerholic.walkingpet.domain.character.dto.UserCharacterListInfo;
import com.walkerholic.walkingpet.domain.character.dto.response.UserCharacterListInfoResponse;
import com.walkerholic.walkingpet.domain.users.entity.Users;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class UsersDto {
    private Integer userId;
    private String email;
    private String nickname;
    private Integer status;
    private LocalDateTime regDate;
    private LocalDateTime delDate;
    private Integer role;

    public Users toEntity() {
        return Users.createNewMember(email, nickname);
    }
}
