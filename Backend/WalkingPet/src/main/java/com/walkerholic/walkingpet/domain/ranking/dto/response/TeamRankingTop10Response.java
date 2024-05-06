package com.walkerholic.walkingpet.domain.ranking.dto.response;

import com.walkerholic.walkingpet.domain.ranking.dto.TeamRankingTop10;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class TeamRankingTop10Response {
    private List<TeamRankingTop10> teamTop10;

    public static TeamRankingTop10Response from(List<TeamRankingTop10> teamTop10) {
        return TeamRankingTop10Response.builder()
                .teamTop10(teamTop10)
                .build();
    }
}
