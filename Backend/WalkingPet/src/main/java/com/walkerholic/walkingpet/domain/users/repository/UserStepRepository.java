package com.walkerholic.walkingpet.domain.users.repository;

import com.walkerholic.walkingpet.domain.users.entity.UserStep;
import com.walkerholic.walkingpet.domain.users.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public interface UserStepRepository extends JpaRepository<UserStep, Integer> {
    Optional<UserStep> findUserStepByUser(Users user);

    Optional<UserStep> findUserStepByUserUserId(Integer userId);

    List<UserStep> findTop10ByOrderByAccumulationStepDesc();

    List<UserStep> findByOrderByAccumulationStepDesc();

    @Query("""
        SELECT COUNT(*) + 1
        FROM UserStep US1
        WHERE US1.accumulationStep >
        (
            SELECT US2.accumulationStep
            FROM UserStep US2
            WHERE US2.user.userId = :userId
        )
        """)
    int findUserPersonalRankingByAccStep(Integer userId);
}
