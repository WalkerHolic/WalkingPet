package com.walkerholic.walkingpet.global.redis.service;

import com.walkerholic.walkingpet.domain.ranking.dto.AccStepTop10Ranking;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class RedisRankingService {


//    private final ZSetOperations<String, Map<String, Integer>> zSetOperations;
//    @Resource(name = "redisTemplateForStepCount")
//    private final ZSetOperations<String, AccStepTop10Ranking> zSetOperations;

//    @Resource(name = "redisTemplateForStepCount")
//    private HashOperations<String, String, Integer> hashOperations;
    private final RedisTemplate<String, String> redisTemplate;
    private final String USER_STEP_KEY = "user_step";

    /**
     * Redis에 사용자의 걸음 수 저장
     * @param userStepsInfo: 사용자의 정보와 걸음 수 맵
     */
//    public void saveUserSteps(Map<String, AccStepTop10Ranking> userStepsInfo) {
//    public void saveUserSteps(Map<String, Integer> userStepsInfo) {
    public void saveUserStepsTest() {
//        userStepsInfo.forEach((userId, userStep) -> {
//            zSetOperations.add(USER_STEP_KEY, userStep, userStep.getStep());
//        });
//        zSetOperations.add(USER_STEP_KEY, userStepsInfo, userStepsInfo.get("step"));
//        hashOperations.putAll(USER_STEP_KEY, userStepsInfo);

        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        String key = "stringKey";

        // when
        valueOperations.set(key, "hello");

        // then
        String value = valueOperations.get(key);
        System.out.println("value: " + value);
    }

    public void saveUserSteps() {

    }

}
