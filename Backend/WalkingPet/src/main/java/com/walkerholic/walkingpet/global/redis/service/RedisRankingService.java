package com.walkerholic.walkingpet.global.redis.service;

import com.walkerholic.walkingpet.domain.ranking.dto.AccStepRankingInfo;
import com.walkerholic.walkingpet.domain.ranking.dto.response.AccStepRankingResponse;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RedisRankingService {
    private static final String RANKING_KEY = "ACC_STEP_RANKING";
    private static final String TEST = "test2";
    private static final String RANKING_TEST = "ranking";
//    private static final int START_RANKING = 0;
//    private static final int END_RANKING = 9;

    @Resource(name = "accStepRankingList")
    private final RedisTemplate<String, AccStepRankingInfo> redisTemplate;

    @Resource(name = "accStepRankingList")
    private final RedisTemplate<String, AccStepRankingInfo> redisTemplate2;

    @Resource(name = "redisTemplateForStepCount")
    private final RedisTemplate<String, Integer> test;

    @Resource(name = "accStepRankingList")
    private final RedisTemplate<String, AccStepRankingInfo> test2;

//    private final RedisTemplate<String, Integer> test;

//    @Resource(name = "redisTemplateObject")
//    private final RedisTemplate<String, Object> redisTemplate;

    // redis에 사용자들의 누적 걸음수 정보 저장
    public void saveAccStepList(AccStepRankingInfo accStepInfo) {
//        redisTemplate.opsForHash().put(HASH_KEY, user.getUserId(), user);

//        redisTemplate.opsForZSet().add(RANKING_KEY, accStepInfo, accStepInfo.getStep()); //성공

        HashOperations<String, Integer, AccStepRankingInfo> hashOperation = test.opsForHash();
        hashOperation.put(RANKING_TEST, accStepInfo.getUserId(), accStepInfo);
    }

    public AccStepRankingInfo getUser(int userId) {
        return (AccStepRankingInfo) redisTemplate.opsForHash().get(RANKING_KEY, userId);
    }

    public AccStepRankingResponse getRedisAccStepRanking(int startRanking, int endRanking) {
//        Set<AccStepRankingInfo> accStepTop10 = redisTemplate.opsForZSet().reverseRange(RANKING_KEY, startRanking, endRanking);
        Set<AccStepRankingInfo> accStepTop10 = redisTemplate2.opsForZSet().reverseRange(TEST, startRanking, endRanking);

        return AccStepRankingResponse.from(redisAccStepRankingToDto(accStepTop10));

//        ZSetOperations<String, AccStepRankingInfo> zSetOperations = test2.opsForZSet();
//        ZSetOperations<String, AccStepRankingInfo> zSetOperations = redisTemplate2.opsForZSet();

//        ZSetOperations<String, Integer> zSetOperations = redisTemplate2.opsForZSet();
//        Set<AccStepRankingInfo> test1 = zSetOperations.reverseRange(TEST, 0, 9);
//        System.out.println("tests1: " + test1);
//        assert test1 != null;
//        return AccStepRankingResponse.from(redisAccStepRankingToDto(test1));
//        return new ArrayList<>(Objects.requireNonNull(zSetOperations.reverseRange("TEST", 0, 9)));
//        return new ArrayList<>(accStepTop10);
    }

    public List<AccStepRankingInfo> redisAccStepRankingToDto(Set<AccStepRankingInfo> accStepLankingList) {
        List<AccStepRankingInfo> accStepRankingList = new ArrayList<>();
        for (AccStepRankingInfo userStep : accStepLankingList) {
            accStepRankingList.add(AccStepRankingInfo.redisFrom(userStep));
        }
        return accStepRankingList;
    }

    public int getUserRanking(AccStepRankingInfo test) {
        Long userRanking = redisTemplate.opsForZSet().reverseRank(RANKING_KEY, test);
        // 특정 사용자의 ID에 해당하는 순위가 존재하는 경우
        if (userRanking != null) {
            // 0부터 시작하는 순위를 1부터 시작하는 순위로 변환하여 반환
            return userRanking.intValue() + 1;
        } else {
            // 순위를 찾을 수 없는 경우 -1을 반환하거나 예외 처리를 수행할 수 있음
            return -1;
        }
    }
}
