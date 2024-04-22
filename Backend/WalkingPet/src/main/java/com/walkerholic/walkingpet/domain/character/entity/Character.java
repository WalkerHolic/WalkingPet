package com.walkerholic.walkingpet.domain.character.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "character")
public class Character {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "character_id")
    private Integer characterId;

    @Column(name = "name")
    private String name;

    @Column(name = "grade")
    private Integer grade;

    @Column(name = "fix_health")
    private Integer fixHealth;

    @Column(name = "fix_power")
    private Integer fixPower;

    @Column(name = "fix_defense")
    private Integer fixDefense;

    @OneToMany(mappedBy = "character")
    private List<Character> characters = new ArrayList<>();
}