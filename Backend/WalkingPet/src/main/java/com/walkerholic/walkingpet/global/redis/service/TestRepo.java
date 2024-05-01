package com.walkerholic.walkingpet.global.redis.service;

import com.walkerholic.walkingpet.domain.ranking.dto.AccStepRankingInfo;
import com.walkerholic.walkingpet.domain.ranking.dto.response.AccStepRankingResponse;
import com.walkerholic.walkingpet.domain.ranking.dto.StepRankingList;
import com.walkerholic.walkingpet.domain.ranking.dto.response.StepRankingResponse;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Repository
public class TestRepo {
    private final RedisTemplate<String, AccStepRankingInfo> redisTemplate;
    private final ZSetOperations<String, AccStepRankingInfo> zSetOperations;

    public TestRepo(RedisTemplate<String, AccStepRankingInfo> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.zSetOperations = redisTemplate.opsForZSet();
    }

    public void saveUserData(AccStepRankingInfo userData) {
        zSetOperations.add("userdata:steps", userData, userData.getStep());
        redisTemplate.opsForValue().set("userdata:steps:" + userData.getUserId(), userData);
    }


    public StepRankingResponse getRedisAccStepRanking(int startRanking, int endRanking) {
        Set<AccStepRankingInfo> accStepRankingInfos = zSetOperations.reverseRange("userdata:steps", 0, 9);
        assert accStepRankingInfos != null;
        System.out.println("null 아님");

        return StepRankingResponse.from(redisAccStepRankingToDto(accStepRankingInfos));
    }

    public List<StepRankingList> redisAccStepRankingToDto(Set<AccStepRankingInfo> accStepLankingList) {
        List<StepRankingList> accStepRankingList = new ArrayList<>();
        for (AccStepRankingInfo userStep : accStepLankingList) {
//            accStepRankingList.add(AccStepRankingInfo.redisFrom(userStep));
            int userRanking = getUserRanking(userStep);
            StepRankingList.from(userStep, userRanking);
            accStepRankingList.add(StepRankingList.from(userStep, userRanking));
        }

        return accStepRankingList;
    }

//    public Long getRankByUserId(int userId) {
//        return zSetOperations.reverseRank("userdata:steps", new AccStepRankingInfo(userId));
//    }

    public int getUserRanking(AccStepRankingInfo test) {
        Long userRanking = zSetOperations.reverseRank("userdata:steps", test);
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
