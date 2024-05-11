package com.walkerholic.walkingpet.global.scheduler;

import com.walkerholic.walkingpet.global.redis.service.RealtimeStepRankingRedisService;
import com.walkerholic.walkingpet.global.redis.service.StepService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StepScheduler {
    private final StepService stepService;
    private final RealtimeStepRankingRedisService realtimeStepRankingRedisService;

//    @Scheduled(cron = "0 0 0 * * ?")
//    public void runDailyStepJob() throws Exception {
//        stepTask.dailyStepJob(jobRepository(), dailyStepStep()).execute(null);
//    }

//    @Scheduled(cron = "0 0 0 * * ?", zone = "Asia/Seoul") // 3시 55분
    public void schedulerTest() {
        System.out.println("자정 스케줄러 테스트입니다. ");
    }

    @Scheduled(cron = "0 58 23 * * ?", zone = "Asia/Seoul")
    public void updateMidnightUserStep() {
        System.out.println("오후 11시 58분 걸음수 업데이트 시작");
//        stepService.updateUserStepInfo();
        realtimeStepRankingRedisService.updateUserStepInfo();
    }
}
