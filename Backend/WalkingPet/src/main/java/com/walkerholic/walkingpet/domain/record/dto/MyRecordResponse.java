package com.walkerholic.walkingpet.domain.record.dto;

import com.walkerholic.walkingpet.domain.record.entity.Record;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@Builder
@Slf4j
public class MyRecordResponse {
    private String imageUrl;
    private String detail;
    private int characterId;
    private LocalDateTime regDate;

    public static MyRecordResponse from(Record record){
        return MyRecordResponse.builder()
                .imageUrl(record.getImageUrl())
                .detail(record.getDetail())
                .characterId(record.getRecordId())
                .regDate(record.getRecordRegDate())
                .build();
    }
}
