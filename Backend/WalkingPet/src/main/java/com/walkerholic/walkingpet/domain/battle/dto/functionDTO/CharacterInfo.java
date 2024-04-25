package com.walkerholic.walkingpet.domain.battle.dto.functionDTO;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@ToString
@Getter
public class CharacterInfo {
    private int health;
    private int power;
    private int defense;
}
