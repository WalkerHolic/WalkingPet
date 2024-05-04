package com.walkerholic.walkingpet.global.redis.service;

import com.walkerholic.walkingpet.domain.ranking.dto.AccStepRankingInfo;
import com.walkerholic.walkingpet.domain.ranking.dto.ReailtimeStepRankingInfo;
import com.walkerholic.walkingpet.domain.ranking.dto.YesterdayStepRankingInfo;
import com.walkerholic.walkingpet.domain.ranking.service.RankingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RankingRedisService {
    private final RankingService rankingService;
    private final AccStepRankingRedisService accStepRankingRedisService;
    private final YesterdayStepRankingRedisService yesterdayStepRankingRedisService;
    private final RealtimeStepRankingRedisService realtimeStepRankingRedisService;

    /*
        redis 모든 랭킹 저장
    */
    @Transactional(readOnly = false)
    public void saveRedisAllRanking() {
        
        // 누적 랭킹 및 사용자 정보 저장
        List<AccStepRankingInfo> userAccStepList = rankingService.getUserAccStepList();

        for (AccStepRankingInfo stepInfo : userAccStepList) {
            accStepRankingRedisService.saveAccStepList(stepInfo);
        }

        // 어제 걸음수 랭킹 저장
        List<YesterdayStepRankingInfo> userYseterdayStepList = rankingService.getUserYseterdayStepList();

        for (YesterdayStepRankingInfo stepInfo : userYseterdayStepList) {
            yesterdayStepRankingRedisService.saveYesterdayStepList(stepInfo);
        }

        // 실시간 걸음수 랭킹 저장
        List<ReailtimeStepRankingInfo> userRealtimeStepList = rankingService.getUserRealtimeStepList();

        for (ReailtimeStepRankingInfo stepInfo : userRealtimeStepList) {
            realtimeStepRankingRedisService.saveAllUserYesterdayStepList(stepInfo);
        }
    }
}
