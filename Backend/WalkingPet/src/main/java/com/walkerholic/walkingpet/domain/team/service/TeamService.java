package com.walkerholic.walkingpet.domain.team.service;

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
        return getTeamResponses(teams);

    }

    @Transactional
    public List<TeamResponse> getUserTeams(int userId) {

        Users user = usersRepository.findUsersByUserId(userId)
                .orElseThrow(() -> new GlobalBaseException(USER_NOT_FOUND));

        List<Team> teams = teamUserRepository.findTeamsByUser(user)
                .orElseThrow(() -> new GlobalBaseException(TEAM_NOT_FOUND));

        return getTeamResponses(teams);

    }
    @Transactional
    public List<TeamResponse> getSearchTeams(String content) {
        List<Team> searchTeams = teamRepository.findByNameContaining(content)
                .orElseThrow(() -> new GlobalBaseException(TEAM_NOT_FOUND));

        return getTeamResponses(searchTeams);
    }

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

        int currentUserTeams = teamUserRepository.countByUser(user)
                .orElseThrow(() -> new GlobalBaseException(TEAM_NOT_FOUND));

        if (currentUserTeams >= MAX_ALLOWED_TEAMS) {
            throw new GlobalBaseException(USER_NOT_FOUND);
        }

        Team team = teamRepository.findByTeamId(joinGroupRequest.getTeamId())
                .orElseThrow(() -> new GlobalBaseException(TEAM_NOT_FOUND));

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

//    public void joinGroup(JoinGroupRequest joinGroupRequest,int userId) {
//
//        Users user = usersRepository.findUsersByUserId(userId)
//                .orElseThrow(() -> new GlobalBaseException(USER_NOT_FOUND));
//
//        Team team = Team.builder()
//                .name(joinGroupRequest.getTeamName())
//                .explanation(joinGroupRequest.getExplanation())
//                .password(joinGroupRequest.getPassword())
//                .user(user)
//                .build();
//        teamRepository.save(team);
//
//        TeamUser teamUser = TeamUser.builder()
//                .team(team)
//                .user(user)
//                .build();
//        teamUserRepository.save(teamUser);
//
//    }
}
