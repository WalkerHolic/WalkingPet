package com.walkerholic.walkingpet.domain.character.repository;

import com.walkerholic.walkingpet.domain.character.entity.Character;
import com.walkerholic.walkingpet.domain.character.entity.UserCharacter;
import com.walkerholic.walkingpet.domain.users.entity.UserDetail;
import com.walkerholic.walkingpet.domain.users.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserCharacterRepository extends JpaRepository<UserCharacter, Integer> {
    Optional<UserCharacter> findByUserCharacterId(int userCharacterId);

    Optional<UserCharacter> findByUserAndCharacter(Users user, Character character);

    @Query("select uc from UserCharacter uc where uc.user.userId = :userId and uc.userCharacterId = :userCharacterId")
    Optional<UserCharacter> findUserCharacterByUserIdAndUserCharacterId(int userId, int userCharacterId);
}
