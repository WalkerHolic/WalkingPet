package com.walkerholic.walkingpet.domain.battle.dto.response;

import lombok.*;

@Builder
@ToString
@Getter
public class BattleResponse {
    private BattleProgressInfo battleProgressInfo;
    private EnemyInfo enemyInfo;
    private BattleResult battleResult;
}
