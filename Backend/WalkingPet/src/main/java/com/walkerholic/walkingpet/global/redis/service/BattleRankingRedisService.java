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

    // redis에서 배틀 점수 기준 top 정보 가져오기
    @Transactional(readOnly = true)
    public BattleRankingResponse getRedisBattleRankingList(int startRanking, int endRanking) {
        System.out.println("배틀 랭킹 top redis 접근");
        List<BattleRankingList> battleRankingList = new ArrayList<>();
        Set<Integer> top10users = rankigRedisTemplate.opsForZSet().reverseRange(RANKING_KEY, startRanking, endRanking);
        assert top10users != null;

        int userRanking = 0;
        int previousBattleRating = -1;
        int sameRankCount = 0;
        for (Integer userId: top10users) {
            UserRedisDto user = userInfoRedisService.getUser(userId);

            Double dBattleRating = rankigRedisTemplate.opsForZSet().score(RANKING_KEY, userId);
            int battleRating = dBattleRating != null ? dBattleRating.intValue() : 0;

            if (battleRating != previousBattleRating) {
                userRanking += sameRankCount + 1;
                sameRankCount = 0;
            } else {
                sameRankCount++;
            }

            battleRankingList.add(BattleRankingList.redisFrom(user, battleRating, userRanking));
            previousBattleRating = battleRating;
        }

        return BattleRankingResponse.from(battleRankingList);
    }

}
