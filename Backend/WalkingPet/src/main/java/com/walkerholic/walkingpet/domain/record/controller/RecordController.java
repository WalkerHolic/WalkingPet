package com.walkerholic.walkingpet.domain.record.controller;

import com.walkerholic.walkingpet.domain.record.dto.response.AllUserRecordResponse;
import com.walkerholic.walkingpet.domain.record.dto.response.UploadRecordResponse;
import com.walkerholic.walkingpet.domain.record.service.RecordService;
import com.walkerholic.walkingpet.global.auth.dto.CustomUserDetail;
import com.walkerholic.walkingpet.global.error.GlobalSuccessCode;
import com.walkerholic.walkingpet.global.error.response.CommonResponseEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/record")
public class RecordController {

    private final RecordService recordService;

    //1. 기록 올리기
    @PostMapping("/upload")
    @Operation(summary = "기록 업로드하기", description = "유저가 찍은 이미지와 유저의 정보를 저장")
    @ApiResponse(responseCode = "200", description = "S200 - 업로드 성공", content = @Content(schema = @Schema(implementation = CommonResponseEntity.class)))
    @ApiResponse(responseCode = "404", description = "R400 - 업로드 실패")
    public ResponseEntity<CommonResponseEntity> uploadRecord(@AuthenticationPrincipal CustomUserDetail userDetail,
                                                             @RequestParam("image") MultipartFile multipartFile,
                                                             @RequestParam("characterId") int characterId,
                                                             @RequestParam("latitude") double latitude,
                                                             @RequestParam("longitude") double longitude) {
        int userId = userDetail.getUsers().getUserId();
        log.info("유저의 기록 등록하기 - userId: {}", userId);

        UploadRecordResponse uploadRecordResponse = recordService.uploadRecord(userId, multipartFile, characterId, latitude, longitude);
        return CommonResponseEntity.toResponseEntity(GlobalSuccessCode.SUCCESS, uploadRecordResponse);
    }

    //2. 유저가 올린 모든 사진 가져오기(유저 id로 저장되어 있는 모든 사진 가져오기)
    @GetMapping("/my-record")
    @Operation(summary = "유저의 모든 기록 가져오기", description = "유저가 작성하고 업로드한 모든 기록을 가져옵니다.")
    @ApiResponse(responseCode = "200", description = "S200 - 가입한 회원인지 확인 성공", content = @Content(schema = @Schema(implementation = CommonResponseEntity.class)))
    @ApiResponse(responseCode = "404", description = "R400 - 유저가 작성한 기록 없음")
    public ResponseEntity<CommonResponseEntity> getAllRecord(@AuthenticationPrincipal CustomUserDetail userDetail) {
        int userId = userDetail.getUsers().getUserId();
        log.info("유저가 등록한 모든 기록 가져오기 - userId: {}", userId);
        AllUserRecordResponse allUserRecordResponse = recordService.getAllRecord(userId);

        return CommonResponseEntity.toResponseEntity(GlobalSuccessCode.SUCCESS, allUserRecordResponse);
    }

    //3. 기록 삭제하기
    @PostMapping("/delete")
    @Operation(summary = "기록 삭제", description = "유저가 등록한 기록 삭제")
    @ApiResponse(responseCode = "200", description = "S200 - 기록 삭제 성공", content = @Content(schema = @Schema(implementation = CommonResponseEntity.class)))
    @ApiResponse(responseCode = "404", description = "R400 - 기록 삭제 실패")
    public ResponseEntity<CommonResponseEntity> deleteRecord(@AuthenticationPrincipal CustomUserDetail userDetail, @RequestParam("fileName") String fileName){
        int userId = userDetail.getUsers().getUserId();
        log.info("유저가 등록한 기록 삭제하기 - userId: {}", userId);
        Boolean canDelete = recordService.deleteImage(userId, fileName);

        return CommonResponseEntity.toResponseEntity(GlobalSuccessCode.SUCCESS, canDelete);
    }
}
