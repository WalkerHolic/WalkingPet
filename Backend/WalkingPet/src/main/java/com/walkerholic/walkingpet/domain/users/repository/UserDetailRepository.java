package com.walkerholic.walkingpet.domain.users.repository;

import com.walkerholic.walkingpet.domain.users.entity.UserDetail;
import com.walkerholic.walkingpet.domain.users.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;


public interface UserDetailRepository extends JpaRepository<UserDetail, Integer> {
    Optional<UserDetail> findUserDetailByUser(Users user);

    Optional<UserDetail> findByUserUserId(int userId);

    @Modifying
    @Query("UPDATE UserDetail ud SET ud.selectUserCharacter.userCharacterId = :userCharacterId WHERE ud.user.userId = :userId")
    void updateUserCharacterIdByUserId(Integer userId, Integer userCharacterId);
}
