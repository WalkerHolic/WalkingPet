package com.walkerholic.walkingpet.domain.group.entity;

import com.walkerholic.walkingpet.domain.character.entity.UserCharacter;
import com.walkerholic.walkingpet.domain.users.entity.Users;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "group")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_id")
    private Integer groupId;

    @Column(name = "name")
    private String name;

    @Column(name = "explain")
    private String explain;

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

    @OneToMany(mappedBy = "group")
    private List<GroupUser> groupUsers = new ArrayList<>();
}
