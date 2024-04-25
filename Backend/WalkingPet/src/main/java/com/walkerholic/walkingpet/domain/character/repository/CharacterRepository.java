package com.walkerholic.walkingpet.domain.character.repository;

import com.walkerholic.walkingpet.domain.character.entity.Character;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CharacterRepository extends JpaRepository<Character, Long> {
    @Query("SELECT c FROM Character c WHERE c.grade = :grade ORDER BY RAND() LIMIT 1")
    Optional<Character> findRandomByGrade(Integer grade);

    Optional<Character> findByCharacterId(Integer characterId);

    Optional<Character> findCharacterByCharacterId(Integer characterId);
}
