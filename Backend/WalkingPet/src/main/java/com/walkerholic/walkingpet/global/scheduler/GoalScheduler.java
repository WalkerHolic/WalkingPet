package com.walkerholic.walkingpet.global.scheduler;

import com.walkerholic.walkingpet.domain.goal.entity.Goal;
import com.walkerholic.walkingpet.domain.goal.repository.GoalRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GoalScheduler {
    private GoalRepository goalRepository;

    //매일매일 개인목표 초기화
    @Scheduled(cron = "0 0 0 * * *", zone = "Asia/Seoul")
    public void resetDailyGoal(){
        List<Goal> goalList = goalRepository.findAll();
        for(Goal goal : goalList){
            goal.setDailyGoal(0);
            goalRepository.save(goal);
        }
    }

    //매주 월요일마다 주간목표 초기화
    @Scheduled(cron = "0 0 0 * * 1", zone = "Asia/Seoul")
    public void resetWeeklyGoal(){
        List<Goal> goalList = goalRepository.findAll();
        for(Goal goal : goalList){
            goal.setWeeklyGoal(0);
            goalRepository.save(goal);
        }
    }
}
