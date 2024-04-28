package com.walkerholic.walkingpet.domain.ranking.service;

import com.walkerholic.walkingpet.domain.ranking.dto.AccStepTop10Ranking;
import com.walkerholic.walkingpet.domain.ranking.dto.AccStepTop3Ranking;
import com.walkerholic.walkingpet.domain.ranking.dto.response.AccStepTop10RankingResponse;
import com.walkerholic.walkingpet.domain.ranking.dto.response.AccStepTop3RankingResponse;
import com.walkerholic.walkingpet.domain.ranking.dto.response.PersonalStepRankingResponse;
import com.walkerholic.walkingpet.domain.ranking.dto.response.UserPersonalStepRankingResponse;
import com.walkerholic.walkingpet.domain.users.entity.UserStep;
import com.walkerholic.walkingpet.domain.users.repository.UserStepRepository;
import com.walkerholic.walkingpet.global.error.GlobalBaseException;
import com.walkerholic.walkingpet.global.error.GlobalErrorCode;
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

        int userId = 1;
        UserPersonalStepRankingResponse userAccStepRanking = getUserAccStepRanking(1);
        return PersonalStepRankingResponse.from(userAccStepRanking, accStepRankingList);
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
     * 누적 걸음수 기준으로 top 3 개인 랭킹 가져오기
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

    /**
     * 누적 걸음수 기준으로 개인 랭킹에서 해당 유저의 랭킹 가져오기
     */
    public UserPersonalStepRankingResponse getUserAccStepRanking(int userId) {
        //TODO: Redis 데이터로 변경
        int userRanking = userStepRepository.findUserPersonalRankingByAccStep(userId);
        UserStep userStep = userStepRepository.findUserStepByUserUserId(userId)
                .orElseThrow(() -> new GlobalBaseException(GlobalErrorCode.USER_STEP_NOT_FOUND));

        return UserPersonalStepRankingResponse.from(userRanking, userStep.getAccumulationStep());
    }
}
