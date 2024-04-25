package com.walkerholic.walkingpet.domain.team.dto.response;

import com.walkerholic.walkingpet.domain.users.entity.UserDetail;
import com.walkerholic.walkingpet.domain.users.entity.UserStep;
import com.walkerholic.walkingpet.domain.users.entity.Users;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Builder
@ToString
@Getter
public class TeamDetailResponse {

    private Integer teamId;
    private String teamName;
    private String explain;
    private Integer teamPoint;
    private Integer userCount;
    private List<TeamUsersResponse> teamUsers;

    public static TeamDetailResponse from(TeamResponse teamResponse, List<TeamUsersResponse> teamUsers){
        return TeamDetailResponse.builder()
                .teamId(teamResponse.getTeamId())
                .teamName(teamResponse.getTeamName())
                .explain(teamResponse.getExplain())
                .teamPoint(teamResponse.getTeamPoint())
                .userCount(teamResponse.getUserCount())
                .teamUsers(teamUsers)
                .build();
    }

}
