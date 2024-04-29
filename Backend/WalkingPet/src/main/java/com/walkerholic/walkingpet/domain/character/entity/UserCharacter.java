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

    @Column(name = "level", nullable = false)
    private Integer level;

    @Column(name = "upgrade", nullable = false)
    private Integer upgrade;

    @Column(name = "stat_point", nullable = false)
    private Integer statPoint;

    @Column(name = "experience", nullable = false)
    private Integer experience;

    @Column(name = "health", nullable = false)
    private Integer health;

    @Column(name = "power", nullable = false)
    private Integer power;

    @Column(name = "defense", nullable = false)
    private Integer defense;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "character_id", nullable = false)
    private Character character;

    public UserCharacter(Character character, Users user){
        this.character = character;
        this.user = user;
        this.level = 1;
        this.upgrade = 0;
        this.experience = 0;
        this.statPoint = 0;
        this.health = character.getFixHealth();
        this.power = character.getFixPower();
        this.defense = character.getFixDefense();

    }

    public void setUpgrade(int i) {
        this.upgrade = i;
    }

    public void useStatPoint(int statPoint) { this.statPoint -= statPoint;}

    public void raisePower(int statPoint) { this.power += statPoint; }

    public void raiseDefense(int statPoint) { this.defense += statPoint; }

    public void raiseHealth(int statPoint) { this.health += statPoint; }

    public void resetStat(int statPoint, int power, int defense, int health) {
        this.statPoint = statPoint;
        this.power = power;
        this.defense = defense;
        this.health = health;
    }

    public void addExperience(int experience) {this.experience += experience;}

    public void levelUp(int experience){
        this.experience = experience;
        this.statPoint += 5;
        this.level += 1;
    }

    public void updateLevelUp(int nextLevel, int experience, int statPoint){
        this.level = nextLevel;
        this.experience = experience;
        this.statPoint = statPoint;
    }

    public void addStatPoint(int statPoint){
        this.statPoint += statPoint;
    }
}
