package com.walkerholic.walkingpet.domain.users.repository;

import com.walkerholic.walkingpet.domain.users.entity.UserStep;
import com.walkerholic.walkingpet.domain.users.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserStepRepository extends JpaRepository<UserStep, Integer> {
    Optional<UserStep> findUserStepByUser(Users user);

    Optional<UserStep> findUserStepByUserUserId(Integer userId);

    List<UserStep> findTop10ByOrderByAccumulationStepDesc();
}