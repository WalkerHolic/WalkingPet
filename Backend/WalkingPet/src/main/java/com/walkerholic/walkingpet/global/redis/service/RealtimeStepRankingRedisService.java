package com.walkerholic.walkingpet.global.redis.service;

import com.walkerholic.walkingpet.domain.ranking.dto.AccStepRankingInfo;
import com.walkerholic.walkingpet.domain.ranking.dto.ReailtimeStepRankingInfo;
import com.walkerholic.walkingpet.domain.ranking.dto.StepRankingList;
import com.walkerholic.walkingpet.domain.ranking.dto.request.RealtimeStepRequest;
import com.walkerholic.walkingpet.domain.ranking.dto.response.RedisStepRankingResponse;
import com.walkerholic.walkingpet.domain.users.entity.UserStep;
import com.walkerholic.walkingpet.domain.users.repository.UserStepRepository;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RealtimeStepRankingRedisService {
    private static final String USERS_KEY = "USER_REALTIME_STEP_RANKING_INFO";
    private static final String RANKING_KEY = "RANKING";

    @Resource(name = "redisTemplateForStepCount")
    private final RedisTemplate<String, Integer> rankigRedisTemplate;

    private final UserStepRepository userStepRepository;

    // redis에 모든 사용자들의 누적 걸음수 정보 저장
    public void saveAllUserYesterdayStepList(ReailtimeStepRankingInfo rankingInfo) {
        //TODO: 한번에 저장하는 방식으로 변경
        rankigRedisTemplate.opsForZSet().add(USERS_KEY, rankingInfo.getUserId(), rankingInfo.getRealtimeStep());
    }

    // redis에 특정 사용자들의 누적 걸음수 정보 저장
    public void saveUserYesterdayStep(RealtimeStepRequest realtimeStepRequest) {
        rankigRedisTemplate.opsForZSet().add(USERS_KEY, realtimeStepRequest.getUserId(), realtimeStepRequest.getRealtimeStep());
    }

    // 실시간 걸음수 기준 상위 랭킹 래스트 가져오기
    public RedisStepRankingResponse getRedisRealtimeStepRankingList(int startRanking, int endRanking) {
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

        return RedisStepRankingResponse.from(accStepRankingList);
    }

    // 실시간 걸음수를 기준으로 특정 유저의 순위를 가져오기
    public StepRankingList getUserRealtimeStepRanking(int userId) {
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

    public void updateUserStepInfo() {
//        ValueOperations<String, Integer> dailySteps = rankigRedisTemplate.opsForValue();
//        ZSetOperations<String, Integer> dailySteps = rankigRedisTemplate.opsForZSet();
//        Set<Integer> integers = rankigRedisTemplate.opsForZSet().rangeByScore(USERS_KEY, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);

        Set<ZSetOperations.TypedTuple<Integer>> rankingData = rankigRedisTemplate.opsForZSet()
                .rangeByScoreWithScores(USERS_KEY, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);

        for (ZSetOperations.TypedTuple<Integer> tuple : rankingData) {
            String userId = String.valueOf(tuple.getValue()); // userId 가져오기
            double dailyStep = tuple.getScore() == null ? 0 : tuple.getScore(); // step 가져오기
            System.out.println("User ID: " + userId + ", dailyStep: " + dailyStep);

            UserStep userStep = userStepRepository.findById(Integer.valueOf(userId)).orElse(null);
            if (userStep != null) {
                userStep.updateUserStepInfo((int) dailyStep);
                userStepRepository.save(userStep);
            }
        }
//        for (ValueOperations<String, Integer> entry : dailySteps.ent) {
//            String userId = (String) entry.getKey();
//            int dailyStep = (int) entry.getValue();
//
//            UserStep userStep = userStepRepository.findById(Integer.valueOf(userId)).orElse(null);
//            if (userStep != null) {
//                userStep.updateUserStepInfo(dailyStep);
//                userStepRepository.save(userStep);
//            }
//        }
    }
}
