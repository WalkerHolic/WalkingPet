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
    @Column(nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "character_id")
    private Character character;


    @Column(name = "character_level")
    private Integer characterLevel;

    @Column(name = "stat_point", nullable = false)
    private Integer statPoint;

    @Column(nullable = false)
    private Integer experience;

    @Column(nullable = false)
    private Integer health;

    @Column(nullable = false)
    private Integer defense;

    @Column(nullable = false)
    private Integer power;
}
