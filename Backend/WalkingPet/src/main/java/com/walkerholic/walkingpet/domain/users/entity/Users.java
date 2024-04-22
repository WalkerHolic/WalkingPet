package com.walkerholic.walkingpet.domain.users.entity;

import com.walkerholic.walkingpet.domain.character.entity.UserCharacter;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "email")
    private String email;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "status")
    private Integer status;

    @Column(name = "reg_date")
    private LocalDateTime registDate;

    @Column(name = "del_date")
    private LocalDateTime deleteDate;

    @Column(name = "role")
    private Integer role;

    @OneToMany(mappedBy = "user")
    private List<UserCharacter> userCharacters = new ArrayList<>();

}
