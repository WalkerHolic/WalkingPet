package com.walkerholic.walkingpet.domain.users.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user_step")
public class UserStep {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_step_id")
    private Integer userStepId;

    @Column(name = "yesterday_step", nullable = false)
    private Integer yesterdayStep;

    @Column(name = "daily_step", nullable = false)
    private Integer dailyStep;

    @Column(name = "accumulation_step", nullable = false)
    private Integer accumulationStep;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

}
