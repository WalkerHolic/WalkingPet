package com.walkerholic.walkingpet.domain.data.Controller;

import com.walkerholic.walkingpet.domain.item.service.ItemService;
import com.walkerholic.walkingpet.domain.users.entity.UserDetail;
import com.walkerholic.walkingpet.domain.users.repository.UserDetailRepository;
import com.walkerholic.walkingpet.domain.users.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/data")
public class DataController {
    private final UserService userService;
    private final ItemService itemService;

    @GetMapping("/set/battleRating")
    private void setBattleRating(){
        userService.setBattleRating();
    }
}
