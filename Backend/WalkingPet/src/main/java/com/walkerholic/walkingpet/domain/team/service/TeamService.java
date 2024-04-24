package com.walkerholic.walkingpet.domain.team.service;

import com.walkerholic.walkingpet.domain.team.dto.AllTeamResponse;
import com.walkerholic.walkingpet.domain.team.entity.Team;
import com.walkerholic.walkingpet.domain.team.repository.TeamRepository;
import com.walkerholic.walkingpet.domain.team.repository.TeamUserRepository;
import com.walkerholic.walkingpet.global.error.GlobalBaseException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.walkerholic.walkingpet.global.error.GlobalErrorCode.TEAM_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Slf4j
public class TeamService {

    private final TeamRepository teamRepository;
    private final TeamUserRepository teamUserRepository;

    @Transactional
    public List<AllTeamResponse> getAllTeam() {

            List<Team> teams = teamRepository.findAll();
            List<AllTeamResponse> teamResponses = new ArrayList<>();

            for (Team team : teams) {
                Integer userCount = teamUserRepository.countByTeamId(team.getTeamId())
                        .orElseThrow(() -> new GlobalBaseException(TEAM_NOT_FOUND));
                AllTeamResponse teamResponse = AllTeamResponse.from(team, userCount);
                teamResponses.add(teamResponse);
            }

            return teamResponses;

    }
}
