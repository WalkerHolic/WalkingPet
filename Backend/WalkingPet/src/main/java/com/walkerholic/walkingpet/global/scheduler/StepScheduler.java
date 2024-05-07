package com.walkerholic.walkingpet.global.scheduler;

import com.walkerholic.walkingpet.global.scheduler.task.StepTask;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@EnableScheduling
public class StepScheduler {
    private final StepTask stepTask;

//    @Scheduled(cron = "0 0 0 * * ?")
//    public void runDailyStepJob() throws Exception {
//        stepTask.dailyStepJob(jobRepository(), dailyStepStep()).execute(null);
//    }
}
