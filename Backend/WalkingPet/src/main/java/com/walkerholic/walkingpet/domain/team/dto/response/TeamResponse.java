package com.walkerholic.walkingpet.domain.team.dto.response;

import com.walkerholic.walkingpet.domain.team.entity.Team;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@ToString
@Getter
public class TeamResponse {

    private Integer teamId;
    private String teamName;
    private String explain;
    private Integer teamPoint;
    private Integer userCount;

    public static TeamResponse from(Team team, Integer userCount){
        return TeamResponse.builder()
                .teamId(team.getTeamId())
                .teamName(team.getName())
                .explain(team.getExplanation())
                .teamPoint(team.getPoint())
                .userCount(userCount)
                .build();
    }
}
