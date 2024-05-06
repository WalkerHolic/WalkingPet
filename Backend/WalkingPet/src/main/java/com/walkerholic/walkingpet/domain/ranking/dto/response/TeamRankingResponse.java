package com.walkerholic.walkingpet.domain.ranking.dto.response;

import com.walkerholic.walkingpet.domain.ranking.dto.TeamRanking;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class TeamRankingResponse {
    private List<TeamRanking> rankings;

    public static TeamRankingResponse from(List<TeamRanking> teamTop10) {
        return TeamRankingResponse.builder()
                .rankings(teamTop10)
                .build();
    }
}
