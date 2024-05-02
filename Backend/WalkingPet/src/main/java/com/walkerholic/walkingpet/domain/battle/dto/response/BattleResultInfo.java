package com.walkerholic.walkingpet.domain.battle.dto.response;

import lombok.*;

@Builder
@ToString
@Getter
public class BattleResultInfo {
    private boolean battleResult;
    private int experience;
    private int rating;
    private RewardItem battleReward;
}
