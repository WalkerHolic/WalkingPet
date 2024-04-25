package com.walkerholic.walkingpet.domain.gacha.controller;

import com.walkerholic.walkingpet.domain.gacha.dto.response.GachaCountResponse;
import com.walkerholic.walkingpet.domain.gacha.dto.response.GachaResultResponse;
import com.walkerholic.walkingpet.domain.gacha.service.GachaService;
import com.walkerholic.walkingpet.global.error.GlobalSuccessCode;
import com.walkerholic.walkingpet.global.error.response.CommonResponseEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/gacha")
public class GachaController {

    private final GachaService gachaService;

    @GetMapping("/count/{userId}")
    public ResponseEntity<CommonResponseEntity> getGachaCount(@PathVariable int userId){
        GachaCountResponse gachaCount = gachaService.getGachaCount(userId);
        return CommonResponseEntity.toResponseEntity(GlobalSuccessCode.SUCCESS, gachaCount);
    }

    @GetMapping("/result/{userId}")
    public ResponseEntity<CommonResponseEntity> getGachaResult(@PathVariable int userId, @RequestParam("box") String boxType){
        GachaResultResponse gachaResult = gachaService.getGachaResult(boxType, userId);
        return CommonResponseEntity.toResponseEntity(GlobalSuccessCode.SUCCESS,gachaResult);
    }
}
