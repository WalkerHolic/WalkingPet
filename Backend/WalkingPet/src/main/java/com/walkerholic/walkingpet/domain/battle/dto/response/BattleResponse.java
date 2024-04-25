package com.walkerholic.walkingpet.domain.battle.dto.response;

import lombok.*;

@Builder
@ToString
@Getter
public class BattleResponse {
    private EnemyInfo enemyInfo;
    private BattleProgressInfo battleProgressInfo;
    private BattleResult battleResult;
}
