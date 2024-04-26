package com.walkerholic.walkingpet.domain.goal.controller;

import com.walkerholic.walkingpet.domain.goal.dto.response.UserGoalInfo;
import com.walkerholic.walkingpet.domain.goal.repository.GoalRepository;
import com.walkerholic.walkingpet.domain.goal.service.GoalService;
import com.walkerholic.walkingpet.global.error.GlobalSuccessCode;
import com.walkerholic.walkingpet.global.error.response.CommonResponseEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/goal")
public class GoalController {
    private final GoalService goalService;

    @GetMapping("/info")
    public ResponseEntity<CommonResponseEntity> getGoalInfo(@RequestParam(name = "userId") int userId){
        UserGoalInfo userGoalInfo = goalService.getUserGoalInfo(userId);
        log.info("GaolController userGoalInfo - userId: {}", userId);

        return CommonResponseEntity.toResponseEntity(GlobalSuccessCode.SUCCESS, userGoalInfo);
    }

    @GetMapping("/reward")
    public ResponseEntity<CommonResponseEntity> getGoalReward(@RequestParam(name = "userId")int userId, @RequestParam(name = "step")int goalStep){
        UserGoalInfo userGoalInfo = goalService.getGoalReward(userId, goalStep);
        log.info("GaolController userGoalInfo - userId: {}", userId);

        return CommonResponseEntity.toResponseEntity(GlobalSuccessCode.SUCCESS, userGoalInfo);
    }

}
