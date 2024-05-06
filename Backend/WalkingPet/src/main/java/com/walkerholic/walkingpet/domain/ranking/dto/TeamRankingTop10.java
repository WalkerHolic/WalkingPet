package com.walkerholic.walkingpet.domain.ranking.dto;

import com.walkerholic.walkingpet.domain.team.entity.Team;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class TeamRankingTop10 {
    private Integer ranking;
    private String teamName;
    private String explain;
    private Integer teamPoint;

    public static TeamRankingTop10 from(Team team, Integer ranking){
        return TeamRankingTop10.builder()
                .ranking(ranking)
                .teamName(team.getName())
                .explain(team.getExplanation())
                .teamPoint(team.getPoint())
                .build();
    }
}
