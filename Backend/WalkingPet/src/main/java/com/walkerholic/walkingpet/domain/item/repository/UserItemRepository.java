package com.walkerholic.walkingpet.domain.item.repository;

import com.walkerholic.walkingpet.domain.item.entity.UserItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserItemRepository extends JpaRepository<UserItem, Long> {
    @Query("SELECT ui FROM UserItem ui WHERE ui.item.itemId IN :itemIds AND ui.user.userId = :userId")
    Optional<List<UserItem>> findByItemItemIdAndUserUserIdIn(List<Integer> itemIds, Integer userId);
}
