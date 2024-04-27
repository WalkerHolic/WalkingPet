package com.walkerholic.walkingpet.domain.users.repository;

import com.walkerholic.walkingpet.domain.users.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Integer> {
    Optional<Users> findUsersByUserId(int userId);

}
