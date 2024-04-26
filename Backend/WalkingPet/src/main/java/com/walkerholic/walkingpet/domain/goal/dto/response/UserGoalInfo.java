package com.walkerholic.walkingpet.domain.goal.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@ToString
@Getter
public class UserGoalInfo {
    public static final int DAILY_GOAL_COUNT = 5;
    public static final int WEEKLY_GOAL_COUNT = 7;

    private int step;
    private boolean[] dailyGoal = new boolean[DAILY_GOAL_COUNT];
    private boolean[] weeklyGoal = new boolean[WEEKLY_GOAL_COUNT];
}
