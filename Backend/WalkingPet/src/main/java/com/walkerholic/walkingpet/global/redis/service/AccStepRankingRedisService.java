package com.walkerholic.walkingpet.global.redis.service;

import com.walkerholic.walkingpet.domain.ranking.dto.AccStepRankingInfo;
import com.walkerholic.walkingpet.domain.ranking.dto.StepRankingList;
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
public class AccStepRankingRedisService {
//    private static final String USERS_KEY = "USER";
    private static final String USERS_KEY = "USER_ACC_STEP_RANKING_INFO";
    private static final String RANKING_KEY = "RANKING";

    @Resource(name = "redisTemplateForStepCount")
    private final RedisTemplate<String, Integer> rankigRedisTemplate;


    // redis에 사용자들의 누적 걸음수 정보 저장
    public void saveAccStepList(AccStepRankingInfo accStepInfo) {
        HashOperations<String, Integer, AccStepRankingInfo> hashOperation = rankigRedisTemplate.opsForHash();
        hashOperation.put(RANKING_KEY, accStepInfo.getUserId(), accStepInfo);
        rankigRedisTemplate.opsForZSet().add(USERS_KEY, accStepInfo.getUserId(), accStepInfo.getStep());
    }

    public StepRankingResponse getRedisAccStepRankingList(int startRanking, int endRanking) {
        List<StepRankingList> accStepRankingList = new ArrayList<>();
        Set<Integer> top10users = rankigRedisTemplate.opsForZSet().reverseRange(USERS_KEY, startRanking, endRanking);
        assert top10users != null;
        for (Integer userId: top10users) {
            AccStepRankingInfo userStepInfo = getUser(userId);
            int userRanking = getUserRanking(userId);
            accStepRankingList.add(StepRankingList.from(userStepInfo, userRanking));
        }

        return StepRankingResponse.from(accStepRankingList);
    }

    public StepRankingList getUserAccStepRanking(int userId) {
        AccStepRankingInfo userStepInfo = getUser(userId);
        int userRanking = getUserRanking(userId);
        return StepRankingList.from(userStepInfo, userRanking);
    }

    public AccStepRankingInfo getUser(int userId) {
        HashOperations<String, Integer, AccStepRankingInfo> hashOperations = rankigRedisTemplate.opsForHash();

        return hashOperations.get(RANKING_KEY, userId);
    }

    public int getUserRanking(int userId) {
        Long userRanking = rankigRedisTemplate.opsForZSet().reverseRank(USERS_KEY, userId);

        return (userRanking != null) ? userRanking.intValue() + 1 : -1;
    }

    public int test(AccStepRankingInfo stepInfo) {
        Long userRanking = rankigRedisTemplate.opsForZSet().reverseRank(USERS_KEY, stepInfo.getUserId());

        return (userRanking != null) ? userRanking.intValue() + 1 : -1;
    }

    public List<AccStepRankingInfo> redisAccStepRankingToDto(Set<AccStepRankingInfo> accStepLankingList) {
        List<AccStepRankingInfo> accStepRankingList = new ArrayList<>();
        for (AccStepRankingInfo accStepRankingInfo : accStepLankingList) {
            accStepRankingList.add(AccStepRankingInfo.redisFrom(accStepRankingInfo));
        }
        return accStepRankingList;
    }
}
