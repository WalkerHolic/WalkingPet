package com.walkerholic.walkingpet.global.redis.service;

import com.walkerholic.walkingpet.domain.ranking.dto.request.RealtimeStepRequest;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BattleRankingRedisService {
    private static final String RANKING_KEY = "BATTLE_RANKING_INFO";

    @Resource(name = "redisTemplateForStepCount")
    private final RedisTemplate<String, Integer> rankigRedisTemplate;

//    public void saveUserDailyStep(RealtimeStepRequest realtimeStepRequest) {
//        rankigRedisTemplate.opsForZSet().add(STEP_KEY, realtimeStepRequest.getUserId(), realtimeStepRequest.getRealtimeStep());
//    }
}
