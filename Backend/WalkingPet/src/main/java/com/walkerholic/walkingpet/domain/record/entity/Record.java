package com.walkerholic.walkingpet.domain.record.entity;

import com.walkerholic.walkingpet.domain.users.entity.Users;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "record")
public class Record {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "goal_id")
    private Integer recordId;

    @Column(name = "detail", nullable = true)
    private Integer detail;

    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    @Column(name = "record_reg_date", nullable = false)
    private LocalDateTime recordRegDate;

    @Column(name = "latitude", nullable = false)
    private BigDecimal latitude;

    @Column(name = "longitude", nullable = false)
    private BigDecimal longitude;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "area", nullable = false)
    private String area;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;
//    @Builder
//    public Record(Users user){
//    }
}
