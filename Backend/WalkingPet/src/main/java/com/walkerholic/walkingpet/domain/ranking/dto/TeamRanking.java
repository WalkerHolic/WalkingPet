package com.walkerholic.walkingpet.domain.ranking.dto;

import com.walkerholic.walkingpet.domain.team.entity.Team;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class TeamRanking {
    private Integer ranking;
    private String teamName;
    private String explain;
    private Integer teamPoint;

    public static TeamRanking from(Team team, Integer ranking){
        return TeamRanking.builder()
                .ranking(ranking)
                .teamName(team.getName())
                .explain(team.getExplanation())
                .teamPoint(team.getPoint())
                .build();
    }
}
