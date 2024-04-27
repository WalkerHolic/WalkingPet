package com.walkerholic.walkingpet.domain.ranking.service;

import com.walkerholic.walkingpet.domain.ranking.dto.AccStepTop10Ranking;
import com.walkerholic.walkingpet.domain.ranking.dto.AccStepTop3Ranking;
import com.walkerholic.walkingpet.domain.ranking.dto.response.*;
import com.walkerholic.walkingpet.domain.users.entity.UserStep;
import com.walkerholic.walkingpet.domain.users.repository.UserStepRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RankingService {
    private final UserStepRepository userStepRepository;

    /**
     * 누적 걸음수 랭킹 가져오기
     */
    public PersonalStepRankingResponse getAccStepRanking() {
        //TODO: 동점 순위 일경우 가입 시간순으로
        List<UserStep> topUsers = userStepRepository.findTop10ByOrderByAccumulationStepDesc();

        List<AccStepTop10Ranking> accStepRankingList = new ArrayList<>();
        for (UserStep userStep : topUsers) {
            accStepRankingList.add(AccStepTop10Ranking.from(userStep));
        }

        UserPersonalStepRankingResponse userPersonalStepRanking = new UserPersonalStepRankingResponse();
        return PersonalStepRankingResponse.from(userPersonalStepRanking, accStepRankingList);
    }

    /**
     * 누적 걸음수 기준으로 top 10 랭킹 가져오기
     */
    public AccStepTop10RankingResponse getAccStepRankingTop10() {
        //TODO: 동점 순위 일경우 가입 시간순으로
        List<UserStep> topUsers = userStepRepository.findTop10ByOrderByAccumulationStepDesc();

        List<AccStepTop10Ranking> accStepRankingList = new ArrayList<>();
        for (UserStep userStep : topUsers) {
            accStepRankingList.add(AccStepTop10Ranking.from(userStep));
        }

        return AccStepTop10RankingResponse.from(accStepRankingList);
    }

    /**
     * 누적 걸음수 기준으로 top 3 랭킹 가져오기
     */
    public AccStepTop3RankingResponse getAccStepRankingTop3() {
        //TODO: Redis 데이터로 변경
        List<UserStep> topUsers = userStepRepository.findTop10ByOrderByAccumulationStepDesc();

        List<AccStepTop3Ranking> accStepRankingList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            accStepRankingList.add(AccStepTop3Ranking.from(topUsers.get(i)));
        }

        return AccStepTop3RankingResponse.from(accStepRankingList);
    }
}
