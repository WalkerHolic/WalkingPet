package com.walkerholic.walkingpet.domain.record.dto.response;

import com.walkerholic.walkingpet.domain.record.dto.MyRecordResponse;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Builder
@Slf4j
public class AllUserRecordResponse {
    @Builder.Default
    List<MyRecordResponse> myRecordResponsesList = new ArrayList<>();
}
