package com.walkerholic.walkingpet.domain.ranking.service;

import com.walkerholic.walkingpet.domain.ranking.dto.AccStepRankingResponse;
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
//    public void getAccStepRanking() {
//        Objects[] accStepRanking = (Objects[]) usersRepository.findUserDetailsByUserId(1);
//        for (Objects test: accStepRanking) {
////        for (AccStepRankingResponse test: accStepRanking) {
//            System.out.println(test);
//        }
//
//        System.out.println("----------------------------");
//    }

    public List<AccStepRankingResponse> getAccStepRanking() {
        //TODO: 동점 순위 일경우 가입 시간순으로
        int rankLimit = 10;
        List<UserStep> topUsers = userStepRepository.findTop10ByOrderByAccumulationStepDesc();


        List<AccStepRankingResponse> accStepRankingList = new ArrayList<>();
        for (UserStep test : topUsers) {
            accStepRankingList.add(AccStepRankingResponse.from(test));
        }
        return accStepRankingList;
    }
}
