package com.walkerholic.walkingpet.domain.team.service;

import com.walkerholic.walkingpet.domain.team.dto.request.CreateGroupRequest;
import com.walkerholic.walkingpet.domain.team.dto.request.JoinGroupRequest;
import com.walkerholic.walkingpet.domain.team.dto.response.TeamResponse;
import com.walkerholic.walkingpet.domain.team.entity.Team;
import com.walkerholic.walkingpet.domain.team.entity.TeamUser;
import com.walkerholic.walkingpet.domain.team.repository.TeamRepository;
import com.walkerholic.walkingpet.domain.team.repository.TeamUserRepository;
import com.walkerholic.walkingpet.domain.users.entity.Users;
import com.walkerholic.walkingpet.domain.users.repository.UsersRepository;
import com.walkerholic.walkingpet.global.error.GlobalBaseException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.walkerholic.walkingpet.global.error.GlobalErrorCode.TEAM_NOT_FOUND;
import static com.walkerholic.walkingpet.global.error.GlobalErrorCode.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Slf4j
public class TeamService {

    private static final int MAX_ALLOWED_TEAMS  = 3;

    private final TeamRepository teamRepository;
    private final TeamUserRepository teamUserRepository;
    private final UsersRepository usersRepository;

    @Transactional
    public List<TeamResponse> getAllTeam() {
        List<Team> teams = teamRepository.findAll();
        if (teams.isEmpty()) {
            // 현재 등록된 팀이 없으면 빈 배열 반환
            return Collections.emptyList();
        } else {
            return getTeamResponses(teams);
        }
    }

    @Transactional
    public List<TeamResponse> getUserTeams(int userId) {

        Users user = usersRepository.findUsersByUserId(userId)
                .orElseThrow(() -> new GlobalBaseException(USER_NOT_FOUND));

        List<Team> teams = teamUserRepository.findTeamsByUser(user);

        if (teams.isEmpty()) {
            // 사용자가 가입한 팀이 없으면 빈 배열 반환
            return Collections.emptyList();
        } else {
            return getTeamResponses(teams);
        }

    }
    @Transactional
    public List<TeamResponse> getSearchTeams(String content) {
        List<Team> searchTeams = teamRepository.findByNameContaining(content);

        if (searchTeams.isEmpty()) {
            // 검색 결과에 맞는 팀이 없으면 빈 배열 반환
            return Collections.emptyList();
        } else {
            return getTeamResponses(searchTeams);
        }
    }

    // Team 객체에 userCount를 추가해서 TeamResponse 형식에 맞추는 메소드
    private List<TeamResponse> getTeamResponses(List<Team> teams) {
        return teams.stream()
                .map(team -> {
                    Integer userCount = teamUserRepository.countByTeamId(team.getTeamId())
                            .orElseThrow(() -> new GlobalBaseException(TEAM_NOT_FOUND));
                    return TeamResponse.from(team, userCount);
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public void joinGroup(JoinGroupRequest joinGroupRequest,int userId) {

        Users user = usersRepository.findUsersByUserId(userId)
                .orElseThrow(() -> new GlobalBaseException(USER_NOT_FOUND));

        int currentUserTeams = teamUserRepository.countByUser(user);

        // 사용자가 가입할 수 있는 최대 팀 수를 초과했다면 예외처리
        if (currentUserTeams >= MAX_ALLOWED_TEAMS) {
            throw new GlobalBaseException(USER_NOT_FOUND);
        }

        Team team = teamRepository.findByTeamId(joinGroupRequest.getTeamId())
                .orElseThrow(() -> new GlobalBaseException(TEAM_NOT_FOUND));

        // 사용자가 이미 가입한 팀에 가입 요청을 보냈다면 예외처리
        boolean userBelongsToTeam = teamUserRepository.existsByUserAndTeam(user, team);
        if (userBelongsToTeam) {
            throw new GlobalBaseException(TEAM_NOT_FOUND);
        }

        TeamUser teamUser = TeamUser.builder()
                .team(team)
                .user(user)
                .build();
        teamUserRepository.save(teamUser);

    }

    @Transactional
    public void createGroup(CreateGroupRequest createGroupRequest, int userId) {

        Users user = usersRepository.findUsersByUserId(userId)
                .orElseThrow(() -> new GlobalBaseException(USER_NOT_FOUND));

        int currentUserTeams = teamUserRepository.countByUser(user);

        // 사용자가 가입할 수 있는 최대 팀 수를 초과했다면 예외처리
        if (currentUserTeams >= MAX_ALLOWED_TEAMS) {
            throw new GlobalBaseException(USER_NOT_FOUND);
        }

        byte status =(byte)(!createGroupRequest.getPassword().isEmpty() ? 1 : 0);

        Team team = Team.builder()
                .name(createGroupRequest.getTeamName())
                .explanation(createGroupRequest.getExplanation())
                .password(createGroupRequest.getPassword())
                .status(status)
                .user(user)
                .build();
        teamRepository.save(team);

        TeamUser teamUser = TeamUser.builder()
                .team(team)
                .user(user)
                .build();
        teamUserRepository.save(teamUser);

    }

}
