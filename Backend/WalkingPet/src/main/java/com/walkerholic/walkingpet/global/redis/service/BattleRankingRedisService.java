package com.walkerholic.walkingpet.global.redis.service;

import com.walkerholic.walkingpet.domain.ranking.dto.BattleRankingList;
import com.walkerholic.walkingpet.domain.ranking.dto.response.BattleRankingResponse;
import com.walkerholic.walkingpet.domain.users.dto.UserRedisDto;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class BattleRankingRedisService {
    private static final String RANKING_KEY = "BATTLE_RANKING_INFO";

    @Resource(name = "redisTemplateForStepCount")
    private final RedisTemplate<String, Integer> rankigRedisTemplate;

    private final UserInfoRedisService userInfoRedisService;

    // redis에 userId와 battle 점수 저장
    @Transactional(readOnly = false)
    public void saveUserBattleScore(int userId, int battleRanking) {
        rankigRedisTemplate.opsForZSet().add(RANKING_KEY, userId, battleRanking);
    }

}
