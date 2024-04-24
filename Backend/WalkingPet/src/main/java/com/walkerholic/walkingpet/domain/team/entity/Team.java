package com.walkerholic.walkingpet.domain.team.entity;

import com.walkerholic.walkingpet.domain.team.dto.response.TeamResponse;
import com.walkerholic.walkingpet.domain.users.entity.Users;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "team")
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_id")
    private Integer teamId;

    @Column(name = "name")
    private String name;

    @Column(name = "explanation")
    private String explanation;

    @Column(name = "status")
    private byte status;

    @Column(name = "password")
    private String password;

    @Column(name = "point")
    private Integer point;

    @Column(name = "create_date")
    private LocalDateTime createDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users user;

    @OneToMany(mappedBy = "team")
    private List<TeamUser> teamUsers = new ArrayList<>();

    @Builder
    public Team(String name, String explanation, String password, Users user) {
        this.name = name;
        this.explanation = explanation;
        this.password = password;
        this.user = user;
    }

}
