package com.walkerholic.walkingpet.domain.team.repository;

import com.walkerholic.walkingpet.domain.team.entity.Team;
import com.walkerholic.walkingpet.domain.team.entity.TeamUser;
import com.walkerholic.walkingpet.domain.users.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TeamUserRepository extends JpaRepository<TeamUser, Integer> {
    @Query("SELECT COUNT(tu) FROM TeamUser tu WHERE tu.team.teamId = :teamId")
    Optional<Integer> countByTeamId(@Param("teamId") Integer teamId);

    @Query("SELECT tu.team FROM TeamUser tu WHERE tu.user = :user")
    Optional<List<Team>> findTeamsByUser(@Param("user") Users user);
}
