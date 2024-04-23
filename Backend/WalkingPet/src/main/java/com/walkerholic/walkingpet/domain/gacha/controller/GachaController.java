package com.walkerholic.walkingpet.domain.gacha.controller;

import com.walkerholic.walkingpet.domain.gacha.dto.response.GachaCountResponse;
import com.walkerholic.walkingpet.domain.gacha.dto.response.GachaResultResponse;
import com.walkerholic.walkingpet.domain.gacha.service.GachaService;
import com.walkerholic.walkingpet.global.error.GlobalSuccessCode;
import com.walkerholic.walkingpet.global.error.response.CommonResponseEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/gacha")
public class GachaController {

    private final GachaService gachaService;

    @GetMapping("/count")
    public ResponseEntity<CommonResponseEntity> getGachaCount(){
        GachaCountResponse gachaCount = gachaService.getGachaCount(2);
        return CommonResponseEntity.toResponseEntity(GlobalSuccessCode.SUCCESS, gachaCount);
    }

    @GetMapping("/result")
    public ResponseEntity<CommonResponseEntity> getGachaResult(@RequestParam("box") String boxType){
        GachaResultResponse gachaResult = gachaService.getGachaResult(boxType, 1);
        return CommonResponseEntity.toResponseEntity(GlobalSuccessCode.SUCCESS,gachaResult);
    }
}
