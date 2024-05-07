package com.walkerholic.walkingpet.domain.users.repository;

import com.walkerholic.walkingpet.domain.users.entity.UserDetail;
import com.walkerholic.walkingpet.domain.users.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface UserDetailRepository extends JpaRepository<UserDetail, Integer> {
    Optional<UserDetail> findUserDetailByUser(Users user);

    @Query("SELECT ud FROM UserDetail ud WHERE ud.user.userId = :userId")
    Optional<UserDetail> findByUserUserId(Integer userId);

    @Modifying
    @Query("UPDATE UserDetail ud SET ud.selectUserCharacter.userCharacterId = :userCharacterId WHERE ud.user.userId = :userId")
    void updateUserCharacterIdByUserId(Integer userId, Integer userCharacterId);

    /**
     * fetch join 사용해서 userId로 UserDetail 찾고 UserCharacter 정보도 가져오기
     * @param userId 유저 아이디
     * @return UserDetail with UserCharacter
     */
    @Query("SELECT UD " +
            "FROM UserDetail UD " +
            "LEFT JOIN FETCH UD.user U " +
            "LEFT JOIN FETCH UD.selectUserCharacter UC " +
            "LEFT JOIN FETCH UC.character " +
            "WHERE U.userId = :userId")
    Optional<UserDetail> findByJoinFetchByUserId(Integer userId);

    @Query("SELECT UD " +
            "FROM UserDetail UD " +
            "LEFT JOIN FETCH UD.selectUserCharacter UC " +
            "WHERE UD.user.userId = :userId")
    Optional<UserDetail> findUserDetailByJoinFetchByUserId(int userId);

    @Query("SELECT ud FROM UserDetail ud LEFT JOIN FETCH ud.user LEFT JOIN FETCH ud.selectUserCharacter WHERE ud.user.status = :status")
    List<UserDetail> findAllByUserStatus(Integer status);

    @Query("SELECT ud FROM UserDetail ud LEFT JOIN FETCH ud.user LEFT JOIN FETCH ud.selectUserCharacter WHERE ud.user.userId = :userId")
    Optional<UserDetail> findUserAndUserCharacterByUserId(Integer userId);

    @Query("SELECT ud FROM UserDetail ud LEFT JOIN FETCH ud.selectUserCharacter WHERE ud.user.userId = :userId")
    Optional<UserDetail> findUserCharacterByUserId(Integer userId);
}
