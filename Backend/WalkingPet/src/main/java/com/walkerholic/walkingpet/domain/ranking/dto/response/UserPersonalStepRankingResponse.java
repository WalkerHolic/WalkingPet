package com.walkerholic.walkingpet.domain.ranking.dto.response;

import lombok.Builder;
import lombok.Getter;

//@Builder
@Getter
public class UserPersonalStepRankingResponse {
    private int ranking;
    private int step;

    public UserPersonalStepRankingResponse() {
        this.ranking = 13;
        this.step = 3112;
    }
}
