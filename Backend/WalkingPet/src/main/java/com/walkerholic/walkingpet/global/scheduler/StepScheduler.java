package com.walkerholic.walkingpet.global.scheduler;

import com.walkerholic.walkingpet.global.redis.service.RealtimeStepRankingRedisService;
import com.walkerholic.walkingpet.global.redis.service.StepService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@RequiredArgsConstructor
@Slf4j
public class StepScheduler {
    private final StepService stepService;
    private final RealtimeStepRankingRedisService realtimeStepRankingRedisService;

//    @Scheduled(cron = "0 0 0 * * ?", zone = "Asia/Seoul") // 3시 55분
    public void schedulerTest() {
        System.out.println("자정 스케줄러 테스트입니다. ");
    }

    // redis에 있는 사용자의 걸음수를 mysql의 yesterday_step에 저장, daily_step에 0 초기화, accumulation_step에 값 더함
    @Scheduled(cron = "0 58 23 * * ?", zone = "Asia/Seoul")
    public void updateMidnightUserStep() {
        log.info("오후 11시 58분 걸음수 업데이트 시작");
        realtimeStepRankingRedisService.updateUserStepInfo();
    }

    // 오전 1시부터 밤 11시까지 1시간마다 redis에 있는 걸음수 mysql에 저장
    @Scheduled(cron = "0 0 1-23 * * ?")
    public void saveDailyStepFromRedisToMysql() {
        LocalDateTime currentTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedTime = currentTime.format(formatter);

        log.info("{} - 사용자 걸음수 mysql 저장", formattedTime);
        realtimeStepRankingRedisService.saveUserDailyStep();
    }

    //    @Scheduled(cron = "0 0 0 * * ?")
//    public void runDailyStepJob() throws Exception {
//        stepTask.dailyStepJob(jobRepository(), dailyStepStep()).execute(null);
//    }
}
