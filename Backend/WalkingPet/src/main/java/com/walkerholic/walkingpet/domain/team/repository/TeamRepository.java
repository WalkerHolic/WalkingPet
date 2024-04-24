package com.walkerholic.walkingpet.domain.team.repository;

import com.walkerholic.walkingpet.domain.team.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team, Integer> {
    Optional<List<Team>> findByNameContaining(String name);

    Optional<Team> findByTeamId(Integer teamId);
}
