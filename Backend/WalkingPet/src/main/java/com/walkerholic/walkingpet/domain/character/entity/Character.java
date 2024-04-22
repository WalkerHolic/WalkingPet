package com.walkerholic.walkingpet.domain.character.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "character")
public class Character {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "character_id")
    private Integer characterId;

    @Column(name = "character_name")
    private String characterName;

    @Column(name = "grade")
    private Integer grade;

    @Column(name = "character_health")
    private Integer characterHealth;

    @Column(name = "character_power")
    private Integer characterPower;

    @Column(name = "character_defense")
    private Integer characterDefense;

    @OneToMany(mappedBy = "character")
    private List<Character> characters = new ArrayList<>();
}