package com.walkerholic.walkingpet.domain.record.repository;

import com.walkerholic.walkingpet.domain.record.entity.Record;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RecordRepository extends JpaRepository<Record, Integer> {
    @Query("SELECT R FROM Record R WHERE R.user.userId = :userId")
    List<Record> findAllByUserId(int userId);
}
