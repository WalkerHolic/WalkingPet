package com.walkerholic.walkingpet.domain.character.repository;

import com.walkerholic.walkingpet.domain.character.entity.Character;
import com.walkerholic.walkingpet.domain.character.entity.UserCharacter;
import com.walkerholic.walkingpet.domain.users.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserCharacterRepository extends JpaRepository<UserCharacter, Integer> {
    Optional<UserCharacter> findByUserCharacterId(int userCharacterId);
    UserCharacter findByUserAndCharacter(Users user, Character character);

}
