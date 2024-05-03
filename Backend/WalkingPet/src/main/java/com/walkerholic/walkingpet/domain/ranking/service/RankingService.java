package com.walkerholic.walkingpet.domain.ranking.service;

import com.walkerholic.walkingpet.domain.ranking.dto.AccStepRankingInfo;
import com.walkerholic.walkingpet.domain.ranking.dto.AccStepTop3Ranking;
import com.walkerholic.walkingpet.domain.ranking.dto.ReailtimeStepRankingInfo;
import com.walkerholic.walkingpet.domain.ranking.dto.YesterdayStepRankingInfo;
import com.walkerholic.walkingpet.domain.ranking.dto.response.AccStepRankingResponse;
import com.walkerholic.walkingpet.domain.ranking.dto.response.AccStepTop3RankingResponse;
import com.walkerholic.walkingpet.domain.ranking.dto.response.PersonalStepRankingResponse;
import com.walkerholic.walkingpet.domain.ranking.dto.response.UserPersonalStepRankingResponse;
import com.walkerholic.walkingpet.domain.users.entity.UserDetail;
import com.walkerholic.walkingpet.domain.users.entity.UserStep;
import com.walkerholic.walkingpet.domain.users.repository.UserDetailRepository;
import com.walkerholic.walkingpet.domain.users.repository.UserStepRepository;
import com.walkerholic.walkingpet.global.error.GlobalBaseException;
import com.walkerholic.walkingpet.global.error.GlobalErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RankingService {
    private final UserStepRepository userStepRepository;
    private final UserDetailRepository userDetailRepository;

    /*
     * 누적 걸음수 랭킹 가져오기
     */
    @Transactional(readOnly = true)
    public PersonalStepRankingResponse getAccStepRanking(int userId) {
        //TODO: 동점 순위 일경우 가입 시간순으로
        List<UserStep> topUsers = userStepRepository.findTop10ByOrderByAccumulationStepDesc();

        List<AccStepRankingInfo> accStepRankingList = new ArrayList<>();
        for (UserStep userStepInfo : topUsers) {
            UserDetail userDetailInfo = userDetailRepository.findUserDetailByUser(userStepInfo.getUser())
                    .orElseThrow(() -> new GlobalBaseException(GlobalErrorCode.USER_DETAIL_NOT_FOUND));

            accStepRankingList.add(AccStepRankingInfo.entityFrom(userDetailInfo, userStepInfo));
        }

        UserPersonalStepRankingResponse userAccStepRanking = getUserAccStepRanking(userId);
        return PersonalStepRankingResponse.from(userAccStepRanking, accStepRankingList);
    }

    /*
     * 누적 걸음수 기준으로 top 10 랭킹 가져오기
     */
    @Transactional(readOnly = true)
    public AccStepRankingResponse getAccStepRankingTop10() {
        //TODO: 동점 순위 일경우 가입 시간순으로
        List<UserStep> topUsers = userStepRepository.findTop10ByOrderByAccumulationStepDesc();

        List<AccStepRankingInfo> accStepRankingList = new ArrayList<>();
        for (UserStep userStepInfo : topUsers) {
            UserDetail userDetailInfo = userDetailRepository.findUserDetailByUser(userStepInfo.getUser())
                    .orElseThrow(() -> new GlobalBaseException(GlobalErrorCode.USER_DETAIL_NOT_FOUND));

            accStepRankingList.add(AccStepRankingInfo.entityFrom(userDetailInfo, userStepInfo));
        }

        return AccStepRankingResponse.from(accStepRankingList);
    }

    /*
     * 누적 걸음수 기준으로 top 3 개인 랭킹 가져오기
     */
    @Transactional(readOnly = true)
    public AccStepTop3RankingResponse getAccStepRankingTop3() {
        //TODO: Redis 데이터로 변경
        List<UserStep> topUsers = userStepRepository.findTop10ByOrderByAccumulationStepDesc();

        List<AccStepTop3Ranking> accStepRankingList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            accStepRankingList.add(AccStepTop3Ranking.from(topUsers.get(i)));
        }

        return AccStepTop3RankingResponse.from(accStepRankingList);
    }

    /*
     * 누적 걸음수 기준으로 개인 랭킹에서 해당 유저의 랭킹 가져오기
     */
    @Transactional(readOnly = true)
    public UserPersonalStepRankingResponse getUserAccStepRanking(int userId) {
        //TODO: Redis 데이터로 변경
        int userRanking = userStepRepository.findUserPersonalRankingByAccStep(userId);
        UserStep userStep = userStepRepository.findUserStepByUserUserId(userId)
                .orElseThrow(() -> new GlobalBaseException(GlobalErrorCode.USER_STEP_NOT_FOUND));

        return UserPersonalStepRankingResponse.from(userRanking, userStep.getAccumulationStep());
    }

    /*
    사용자들의 누적 걸음수 데이터 가져오기
    userId, nickname, step
     */
    @Transactional(readOnly = true)
    public List<AccStepRankingInfo> getUserAccStepList() {
        List<UserDetail> allByUser = userDetailRepository.findAllByUserStatus(1);

        List<AccStepRankingInfo> accStepRankingList = new ArrayList<>();
        for (UserDetail userDetailInfo : allByUser) {
            UserStep userStepInfo = userStepRepository.findUserStepByUser(userDetailInfo.getUser())
                    .orElseThrow(() -> new GlobalBaseException(GlobalErrorCode.USER_STEP_NOT_FOUND));
            accStepRankingList.add(AccStepRankingInfo.entityFrom(userDetailInfo, userStepInfo));
        }

        return accStepRankingList;
    }

    /*
        사용자들의 어제 걸음수 데이터 가져오기
        yesterdayStep
     */
    @Transactional(readOnly = true)
    public List<YesterdayStepRankingInfo> getUserYseterdayStepList() {
        List<UserDetail> allByUser = userDetailRepository.findAllByUserStatus(1);

        List<YesterdayStepRankingInfo> accStepRankingList = new ArrayList<>();
        for (UserDetail userDetailInfo : allByUser) {
            UserStep userStepInfo = userStepRepository.findUserStepByUser(userDetailInfo.getUser())
                    .orElseThrow(() -> new GlobalBaseException(GlobalErrorCode.USER_STEP_NOT_FOUND));
            accStepRankingList.add(YesterdayStepRankingInfo.entityFrom(userStepInfo));
        }

        return accStepRankingList;
    }

    /*
    사용자들의 실시간 걸음수 데이터 가져오기
    yesterdayStep
 */
    @Transactional(readOnly = true)
    public List<ReailtimeStepRankingInfo> getUserRealtimeStepList() {
        List<UserDetail> allByUser = userDetailRepository.findAllByUserStatus(1);

        List<ReailtimeStepRankingInfo> reailtimeStepRankingList = new ArrayList<>();
        for (UserDetail userDetailInfo : allByUser) {
            UserStep userStepInfo = userStepRepository.findUserStepByUser(userDetailInfo.getUser())
                    .orElseThrow(() -> new GlobalBaseException(GlobalErrorCode.USER_STEP_NOT_FOUND));

            reailtimeStepRankingList.add(ReailtimeStepRankingInfo.entityFrom(userStepInfo));
        }

        return reailtimeStepRankingList;
    }
}
