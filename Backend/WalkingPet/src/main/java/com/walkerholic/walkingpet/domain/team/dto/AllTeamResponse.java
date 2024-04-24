package com.walkerholic.walkingpet.domain.team.dto;

import com.walkerholic.walkingpet.domain.team.entity.Team;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@ToString
@Getter
public class AllTeamResponse {

    private Integer teamId;
    private String teamName;
    private String explain;
    private Integer teamPoint;
    private Integer userCount;

    public static AllTeamResponse from(Team team, Integer userCount){
        return AllTeamResponse.builder()
                .teamId(team.getTeamId())
                .teamName(team.getName())
                .explain(team.getExplanation())
                .teamPoint(team.getPoint())
                .userCount(userCount)
                .build();
    }
}