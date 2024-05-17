package com.walkerholic.walkingpet.domain.record.dto;

import com.walkerholic.walkingpet.domain.record.entity.Record;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import java.time.LocalDateTime;

@Getter
@Slf4j
@Builder
public class EventRecord {
    private String imageUrl;
    private String content;
    private int characterId;
    private double latitude;
    private double longitude;
    private LocalDateTime regDate;

    public static EventRecord from(Record record){
        return EventRecord.builder()
                .imageUrl(record.getImageUrl())
                .content(record.getContent())
                .characterId(record.getCharacterId())
                .latitude(record.getLatitude().doubleValue())
                .longitude(record.getLongitude().doubleValue())
                .regDate(record.getRecordRegDate())
                .build();
    }
}
