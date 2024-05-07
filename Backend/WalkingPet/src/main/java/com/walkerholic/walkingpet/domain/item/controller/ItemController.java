package com.walkerholic.walkingpet.domain.item.controller;

import com.walkerholic.walkingpet.domain.item.service.ItemService;
import com.walkerholic.walkingpet.domain.levelup.dto.response.LevelUpResponse;
import com.walkerholic.walkingpet.global.error.GlobalSuccessCode;
import com.walkerholic.walkingpet.global.error.response.CommonResponseEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/item")
public class ItemController {
    private final ItemService itemService;

    @GetMapping("/use/expitem")
    public ResponseEntity<CommonResponseEntity> useExpItem(@RequestParam("userId") int userId, @RequestParam("quantity") int quantity){
        log.info("경험치 아이템 사용 확인 useExpItem - userId: {}, quantity: {}", userId, quantity);
        LevelUpResponse levelUpResponse = itemService.usingExpItem(userId, quantity);
        return CommonResponseEntity.toResponseEntity(GlobalSuccessCode.SUCCESS, levelUpResponse);
    }
}
