package com.walkerholic.walkingpet.domain.team.dto.response;

import com.walkerholic.walkingpet.domain.users.entity.UserDetail;
import com.walkerholic.walkingpet.domain.users.entity.UserStep;
import com.walkerholic.walkingpet.domain.users.entity.Users;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@ToString
@Getter
public class TeamUsersResponse {

    private Integer characterId;
    private String nickname;
    private Integer level;
    private Integer step;

    public static TeamUsersResponse from(UserDetail userDetail,UserStep userStep){
        return TeamUsersResponse.builder()
                .characterId(userDetail.getSelectUserCharacter().getCharacter().getCharacterId())
                .nickname(userDetail.getUser().getNickname())
                .level(userDetail.getSelectUserCharacter().getLevel())
                .step(userStep.getDailyStep())
                .build();
    }

}
