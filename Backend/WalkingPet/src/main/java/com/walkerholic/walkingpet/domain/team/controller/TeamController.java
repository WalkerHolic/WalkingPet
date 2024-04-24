package com.walkerholic.walkingpet.domain.team.controller;

import com.walkerholic.walkingpet.domain.team.dto.request.JoinGroupRequest;
import com.walkerholic.walkingpet.domain.team.dto.response.TeamResponse;
import com.walkerholic.walkingpet.domain.team.service.TeamService;
import com.walkerholic.walkingpet.global.error.GlobalSuccessCode;
import com.walkerholic.walkingpet.global.error.response.CommonResponseEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/team")
public class TeamController {

    private final TeamService teamService;
    @Operation(summary = "전체 그룹 확인", description = "전체 그룹 정보 가져오기")
    @ApiResponse(responseCode = "200", description = "S200 - 전체 그룹 조회 성공", content = @Content(schema = @Schema(implementation = TeamResponse.class)))
    @ApiResponse(responseCode = "404", description = "C400 - 전체 그룹 조회 실패")
    @GetMapping("/all")
    public ResponseEntity<CommonResponseEntity> getAllTeam(){
        List<TeamResponse> allTeam = teamService.getAllTeam();
        return CommonResponseEntity.toResponseEntity(GlobalSuccessCode.SUCCESS,allTeam);
    }

    @Operation(summary = "소속된 그룹 확인", description = "유저의 userId로 소속된 그룹 정보 가져오기")
    @ApiResponse(responseCode = "200", description = "S200 - 유저가 소속된 그룹들 조회 성공", content = @Content(schema = @Schema(implementation = TeamResponse.class)))
    @ApiResponse(responseCode = "404", description = "C400 - 유저가 소속된 그룹들 조회 실패")
    @GetMapping("/belong")
    public ResponseEntity<CommonResponseEntity> getUserTeams(){
        List<TeamResponse> allTeam = teamService.getUserTeams(1);
        return CommonResponseEntity.toResponseEntity(GlobalSuccessCode.SUCCESS,allTeam);
    }

    @Operation(summary = "그룹 검색", description = "검색어 content가 이름에 포함된 그룹 정보 가져오기")
    @ApiResponse(responseCode = "200", description = "S200 - 그룹 검색 성공", content = @Content(schema = @Schema(implementation = TeamResponse.class)))
    @ApiResponse(responseCode = "404", description = "C400 - 그룹 검색 실패")
    @GetMapping("/search")
    public ResponseEntity<CommonResponseEntity> getSearchTeams(@RequestParam("content") String content){
        List<TeamResponse> searchTeams = teamService.getSearchTeams(content);
        return CommonResponseEntity.toResponseEntity(GlobalSuccessCode.SUCCESS,searchTeams);
    }

    @Operation(summary = "그룹 가입", description = "groupId를 가지고 그룹에 가입하기")
    @ApiResponse(responseCode = "200", description = "S200 - 그룹 가입 성공", content = @Content(schema = @Schema(implementation = CommonResponseEntity.class)))
    @ApiResponse(responseCode = "404", description = "C400 - 그룹 가입 실패")
    @PostMapping("/join")
    public ResponseEntity<CommonResponseEntity> joinGroup(@RequestBody JoinGroupRequest joinGroupRequest){
        teamService.joinGroup(joinGroupRequest,1);
        return CommonResponseEntity.toResponseEntity(GlobalSuccessCode.SUCCESS);
    }
}
