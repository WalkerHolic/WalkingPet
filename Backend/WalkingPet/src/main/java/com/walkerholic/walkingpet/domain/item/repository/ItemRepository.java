package com.walkerholic.walkingpet.domain.item.repository;

import com.walkerholic.walkingpet.domain.item.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Integer> {
}
