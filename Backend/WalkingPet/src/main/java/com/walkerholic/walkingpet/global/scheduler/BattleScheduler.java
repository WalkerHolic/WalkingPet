package com.walkerholic.walkingpet.global.scheduler;

import com.walkerholic.walkingpet.domain.users.entity.UserDetail;
import com.walkerholic.walkingpet.domain.users.repository.UserDetailRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class BattleScheduler {

    private final UserDetailRepository userDetailRepository;

    //일일 배틀 횟수 초기화 스케줄러(매일 0시 0분에 실행)
    @Scheduled(cron = "0 0 0 * * *", zone = "Asia/Seoul")
    public void resetBattleCount(){
        List<UserDetail> userDetailList = userDetailRepository.findAll();
        for(UserDetail userDetail : userDetailList){
            userDetail.resetBattleCount();
            userDetailRepository.save(userDetail);
        }
    }
}
