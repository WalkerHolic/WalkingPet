package com.walkerholic.walkingpet.domain.users.entity;

import com.walkerholic.walkingpet.domain.character.entity.Character;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user_detail")
public class UserDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_detail_id")
    private Integer userDetailId;

    @Column(name = "battle_rating")
    private Integer battleRating;

    //0: 초기화 안함, 1: 초기화 함
    @Column(name = "init_status", columnDefinition = "TINYINT(1)")
    private byte  initStatus;

    @Column(name = "battle_count")
    private byte battleCount;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "character_id")
    private Character selectCharacter;

}
