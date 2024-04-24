package com.walkerholic.walkingpet.domain.team.controller;

import com.walkerholic.walkingpet.domain.team.dto.AllTeamResponse;
import com.walkerholic.walkingpet.domain.team.service.TeamService;
import com.walkerholic.walkingpet.global.error.GlobalSuccessCode;
import com.walkerholic.walkingpet.global.error.response.CommonResponseEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/team")
public class TeamController {

    private final TeamService teamService;
    @GetMapping("/all")
    public ResponseEntity<CommonResponseEntity> getAllTeam(){
        List<AllTeamResponse> allTeam = teamService.getAllTeam();
        return CommonResponseEntity.toResponseEntity(GlobalSuccessCode.SUCCESS,allTeam);
    }
}
