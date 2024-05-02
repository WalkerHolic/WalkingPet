package com.walkerholic.walkingpet.global.redis.service;

import com.walkerholic.walkingpet.domain.ranking.dto.AccStepRankingInfo;
import com.walkerholic.walkingpet.domain.ranking.dto.StepRankingList;
import com.walkerholic.walkingpet.domain.ranking.dto.YesterdayStepRankingInfo;
import com.walkerholic.walkingpet.domain.ranking.dto.response.StepRankingResponse;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class YesterdayStepRankingRedisService {
    private static final String USERS_KEY = "USER_YESTERDAY_STEP_RANKING_INFO";
    private static final String RANKING_KEY = "RANKING";

    @Resource(name = "redisTemplateForStepCount")
    private final RedisTemplate<String, Integer> rankigRedisTemplate;


    // redis에 사용자들의 누적 걸음수 정보 저장
    public void saveYesterdayStepList(YesterdayStepRankingInfo stepRankingInfo) {
        rankigRedisTemplate.opsForZSet().add(USERS_KEY, stepRankingInfo.getUserId(), stepRankingInfo.getYesterdayStep());
    }

    public StepRankingResponse getRedisYesterdayStepRankingList(int startRanking, int endRanking) {
        List<StepRankingList> accStepRankingList = new ArrayList<>();
        Set<Integer> top10users = rankigRedisTemplate.opsForZSet().reverseRange(USERS_KEY, startRanking, endRanking);
        assert top10users != null;
        for (Integer userId: top10users) {
            AccStepRankingInfo userStepInfo = getUser(userId);

            Double dStep = rankigRedisTemplate.opsForZSet().score(USERS_KEY, userId);
            int step = dStep != null ? dStep.intValue() : 0;
            AccStepRankingInfo changeAccStepRankingInfo = userStepInfo.changeStep(step);

            int userRanking = getUserRanking(userId);
            accStepRankingList.add(StepRankingList.from(changeAccStepRankingInfo, userRanking));
        }

        return StepRankingResponse.from(accStepRankingList);
    }

    public StepRankingList getUserYesterdayStepRanking(int userId) {
        AccStepRankingInfo userStepInfo = getUser(userId);

        Double dStep = rankigRedisTemplate.opsForZSet().score(USERS_KEY, userId);
        int step = dStep != null ? dStep.intValue() : 0;
        AccStepRankingInfo changeAccStepRankingInfo = userStepInfo.changeStep(step);

        int userRanking = getUserRanking(userId);
        return StepRankingList.from(changeAccStepRankingInfo, userRanking);
    }

    public AccStepRankingInfo getUser(int userId) {
        HashOperations<String, Integer, AccStepRankingInfo> hashOperations = rankigRedisTemplate.opsForHash();

        return hashOperations.get(RANKING_KEY, userId);
    }

    public int getUserRanking(int userId) {
        Long userRanking = rankigRedisTemplate.opsForZSet().reverseRank(USERS_KEY, userId);

        return (userRanking != null) ? userRanking.intValue() + 1 : -1;
    }

}
