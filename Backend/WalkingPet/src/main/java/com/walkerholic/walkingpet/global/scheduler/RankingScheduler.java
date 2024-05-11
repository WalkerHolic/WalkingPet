package com.walkerholic.walkingpet.global.scheduler;

import com.walkerholic.walkingpet.global.redis.service.RankingRedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class RankingScheduler {
    private RankingRedisService rankingRedisService;

    // 어제/누적 top10, top3 캐싱 제거
    @Scheduled(cron = "0 46 0 * * ?")  // 초, 분, 시, 일, 월, 요일, 연도(생략 가능)
    @CacheEvict(value = {"accStepRankingTop10", "accStepRankingTop3", "yesterdayStepRankingTop10", "yesterdayStepRankingTop3", "teamTop10"}, allEntries = true)
    public void clearCacheAtMidnight() {
        log.info("오전 12시 3분 - 어제/누적 top10, top3 및 그룹 랭킹 캐싱 제거 완료");
    }

    @Scheduled(cron = "0 48 0 * * ?")
    public void updateRedisRankingInfo() {
       log.info("오전 12시 5분 - mysql 걸음수 데이터 redis로 이동");
       rankingRedisService.saveRedisAllRanking();
    }
}
