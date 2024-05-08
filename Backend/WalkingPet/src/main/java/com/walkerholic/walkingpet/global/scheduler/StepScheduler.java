package com.walkerholic.walkingpet.global.scheduler;

import com.walkerholic.walkingpet.global.redis.service.RealtimeStepRankingRedisService;
import com.walkerholic.walkingpet.global.redis.service.StepService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@EnableScheduling
public class StepScheduler {
    private final StepService stepService;
    private final RealtimeStepRankingRedisService realtimeStepRankingRedisService;

//    @Scheduled(cron = "0 0 0 * * ?")
//    public void runDailyStepJob() throws Exception {
//        stepTask.dailyStepJob(jobRepository(), dailyStepStep()).execute(null);
//    }

//    @Scheduled(cron = "0 42 9 * * ?", zone = "Asia/Seoul")
    @Scheduled(cron = "0 4 0 * * ?")
    public void updateMidnightUserStep() {
        System.out.println("업데이트 시작");
//        stepService.updateUserStepInfo();
        realtimeStepRankingRedisService.updateUserStepInfo();
    }
}
