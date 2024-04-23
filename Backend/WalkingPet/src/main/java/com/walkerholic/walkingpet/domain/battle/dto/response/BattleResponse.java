package com.walkerholic.walkingpet.domain.battle.dto.response;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class BattleResponse {
    private int[] attackDamage;
    private int[] recieveDamage;

    private BattleResult battleResult;
}
