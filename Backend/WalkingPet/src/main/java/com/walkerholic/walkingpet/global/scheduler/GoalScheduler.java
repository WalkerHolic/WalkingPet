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
@Slf4j
public class GoalScheduler {
    private final GoalRepository goalRepository;

    //매일매일 개인목표 초기화
    @Scheduled(cron = "0 0 0 * * *", zone = "Asia/Seoul")
    public void resetDailyGoal(){
        log.info("오전 12시 0분 - 매일매일 개인목표 초기화");
        List<Goal> goalList = goalRepository.findAll();
        for(Goal goal : goalList){
            goal.setDailyGoal(0);
            goalRepository.save(goal);
        }
    }

    //매주 월요일마다 주간목표 초기화
    @Scheduled(cron = "0 1 0 * * 1", zone = "Asia/Seoul")
    public void resetWeeklyGoal(){
        log.info("오전 12시 1분 - 매주 월요일마다 주간목표 초기화");
        List<Goal> goalList = goalRepository.findAll();
        for(Goal goal : goalList){
            goal.setWeeklyGoal(0);
            goalRepository.save(goal);
        }
    }
}
