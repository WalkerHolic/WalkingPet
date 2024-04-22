package com.walkerholic.walkingpet.domain.character.entity;

import com.walkerholic.walkingpet.domain.users.entity.Users;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user_character")
public class UserCharacter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_character_id")
    private Integer userCharacterId;

    @Column(name = "level")
    private Integer level;

    @Column(name = "stat_point")
    private Integer statPoint;

    @Column(name = "experience")
    private Integer experience;

    @Column(name = "health")
    private Integer health;

    @Column(name = "defense")
    private Integer defense;

    @Column(name = "power")
    private Integer power;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "character_id")
    private Character character;

}
