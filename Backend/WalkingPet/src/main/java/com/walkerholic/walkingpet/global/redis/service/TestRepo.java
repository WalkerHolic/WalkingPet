package com.walkerholic.walkingpet.global.redis.service;

import com.walkerholic.walkingpet.domain.ranking.dto.AccStepRankingInfo;
import com.walkerholic.walkingpet.domain.ranking.dto.response.AccStepRankingResponse;
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


    public AccStepRankingResponse getRedisAccStepRanking(int startRanking, int endRanking) {
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

//    public Long getRankByUserId(int userId) {
//        return zSetOperations.reverseRank("userdata:steps", new AccStepRankingInfo(userId));
//    }
}
