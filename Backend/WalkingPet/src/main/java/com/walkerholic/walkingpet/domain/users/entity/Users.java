package com.walkerholic.walkingpet.domain.users.entity;

import com.walkerholic.walkingpet.domain.character.entity.UserCharacter;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

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

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "nickname", nullable = false)
    private String nickname;

    @Column(name = "status", nullable = false)
    private Integer status;

    @CreationTimestamp
    @Column(name = "reg_date", nullable = false)
    private LocalDateTime regDate;

    @Column(name = "del_date")
    private LocalDateTime delDate;

    @Column(name = "role", nullable = false)
    private Integer role;

    @OneToMany(mappedBy = "user")
    private List<UserCharacter> userCharacters = new ArrayList<>();

    @Builder
    public Users(String email, String nickname) {
        this.email = email;
        this.nickname = nickname;
        this.status = 1;
        this.role = 0;
    }

}
