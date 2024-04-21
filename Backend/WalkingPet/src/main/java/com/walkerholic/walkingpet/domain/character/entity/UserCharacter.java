package com.walkerholic.walkingpet.domain.character.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "user_character")
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserCharacter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_character_id")
    private Integer userCharacterId; // 유저의 캐릭터 식별번호

    @Column(name = "character_level")
    private Integer characterLevel; // 캐릭터 레벨

    @Column(name = "stat_point")
    private Integer statPoint; // 스탯

    @Column(name = "experience")
    private Integer experience; // 경험치

    @Column(name = "health")
    private Integer health; // 체력

    @Column(name = "defense")
    private Integer defense; // 방어력

    @Column(name = "power")
    private Integer power; // 방어력

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id")
//    private User user;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "character_id")
//    private Character character;
}
