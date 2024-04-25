package com.walkerholic.walkingpet.domain.battle.dto.functionDTO;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@ToString
@Getter
public class UserRatingDTO {
    private int userId;
    private int rating;
}
