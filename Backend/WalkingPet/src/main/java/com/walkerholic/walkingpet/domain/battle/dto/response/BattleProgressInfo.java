package com.walkerholic.walkingpet.domain.battle.dto.response;

import lombok.*;

@Builder
@ToString
@Getter
public class BattleProgressInfo {
    private int[] attackDamage;
    private int[] recieveDamage;
}
