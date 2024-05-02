package com.walkerholic.walkingpet.global.redis.service;

import com.walkerholic.walkingpet.domain.ranking.dto.AccStepRankingInfo;
import com.walkerholic.walkingpet.domain.ranking.dto.response.AccStepRankingResponse;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class TestRedisService {
    private static final String RANKING_KEY = "ACC_STEP_RANKING";
    private static final String TEST = "test2";
    private static final String RANKING_TEST = "ranking";

    private final RedisTemplate<String, AccStepRankingInfo> redisTemplate = new RedisTemplate<>();
    private ZSetOperations<String, AccStepRankingInfo> zSetOperations;

//    public TestRedisService(RedisTemplate<String, AccStepRankingInfo> redisTemplate) {
//        this.redisTemplate = redisTemplate;
//        this.zSetOperations = redisTemplate.opsForZSet();
//    }
    @PostConstruct
    private void init() {
        zSetOperations = redisTemplate.opsForZSet();
    }

    public void saveAccStepInfo(AccStepRankingInfo accStepInfo) {
        zSetOperations = redisTemplate.opsForZSet();
        zSetOperations.add("userdata:steps", accStepInfo, accStepInfo.getStep());
        redisTemplate.opsForValue().set("userdata:steps:" + accStepInfo.getUserId(), accStepInfo);
    }

//    public Set<AccStepRankingInfo> getTop10ByStep() {
//        // ZSET에서 상위 10개 데이터를 가져옵니다.
//       zSetOperations.reverseRange("userdata:steps", 0, 9);
//    }

    public AccStepRankingResponse getRedisAccStepRanking(int startRanking, int endRanking) {
        zSetOperations = redisTemplate.opsForZSet();
        Set<AccStepRankingInfo> accStepRankingInfos = zSetOperations.reverseRange("userdata:steps", 0, 9);
        assert accStepRankingInfos != null;
        System.out.println("null 아님");

        return AccStepRankingResponse.from(redisAccStepRankingToDto(accStepRankingInfos));
    }

    public List<AccStepRankingInfo> redisAccStepRankingToDto(Set<AccStepRankingInfo> accStepLankingList) {
        List<AccStepRankingInfo> accStepRankingList = new ArrayList<>();
        for (AccStepRankingInfo userStep : accStepLankingList) {
            accStepRankingList.add(AccStepRankingInfo.redisFrom(userStep));
        }
        return accStepRankingList;
    }
}
