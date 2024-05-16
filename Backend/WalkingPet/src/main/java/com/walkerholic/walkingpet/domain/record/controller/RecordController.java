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
    @Operation(summary = "가입한 회원인지 확인", description = "유저의 이메일이 이미 회원가입한 이메일인지 확인하기 ")
    @ApiResponse(responseCode = "200", description = "S200 - 가입한 회원인지 확인 성공", content = @Content(schema = @Schema(implementation = CommonResponseEntity.class)))
    @ApiResponse(responseCode = "404", description = "C400 - 가입한 회원인지 확인 실패")
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


    //2. 사진과 텍스트 캐릭터 아이디 가져오기(userId 와 recordId로 가져오기)


    //3. 유저가 올린 모든 사진 가져오기(유저 id로 저장되어 있는 모든 사진 가져오기)
    @GetMapping("/my-record")
    @Operation(summary = "가입한 회원인지 확인", description = "유저의 이메일이 이미 회원가입한 이메일인지 확인하기 ")
    @ApiResponse(responseCode = "200", description = "S200 - 가입한 회원인지 확인 성공", content = @Content(schema = @Schema(implementation = CommonResponseEntity.class)))
    @ApiResponse(responseCode = "404", description = "C400 - 가입한 회원인지 확인 실패")
    public ResponseEntity<CommonResponseEntity> getAllRecord(@AuthenticationPrincipal CustomUserDetail userDetail){
        int userId = userDetail.getUsers().getUserId();
        log.info("유저가 등록한 모든 기록 가져오기 - userId: {}", userId);
        AllUserRecordResponse allUserRecordResponse = recordService.getAllRecord(userId);

        return CommonResponseEntity.toResponseEntity(GlobalSuccessCode.SUCCESS, allUserRecordResponse);
    }
}
