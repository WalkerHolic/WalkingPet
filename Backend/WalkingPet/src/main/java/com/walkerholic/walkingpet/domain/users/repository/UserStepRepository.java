package com.walkerholic.walkingpet.domain.users.repository;

import com.walkerholic.walkingpet.domain.users.entity.UserStep;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserStepRepository extends JpaRepository<UserStep, Integer> {
    Optional<UserStep> findUserStepByUser(User user);
}
