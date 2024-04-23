package com.walkerholic.walkingpet.domain.gacha.service;

import com.walkerholic.walkingpet.domain.gacha.dto.response.GachaCountResponse;
import com.walkerholic.walkingpet.domain.item.entity.UserItem;
import com.walkerholic.walkingpet.domain.item.repository.UserItemRepository;
import com.walkerholic.walkingpet.domain.users.entity.UserDetail;
import com.walkerholic.walkingpet.global.error.GlobalBaseException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

import static com.walkerholic.walkingpet.global.error.GlobalErrorCode.USER_CHARACTER_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Slf4j
public class GachaService {

    private final UserItemRepository userItemRepository;
    public GachaCountResponse getGachaCount(int userId){

        List<UserItem> userItems = userItemRepository.findByItemItemIdAndUserUserIdIn(Arrays.asList(1, 2), userId)
                .orElseThrow(() -> new GlobalBaseException(USER_CHARACTER_NOT_FOUND));

        int normalBoxCount = 0;
        int luxuryBoxCount = 0;

        for (UserItem userItem : userItems) {
            if (userItem.getItem().getItemId() == 1) {
                normalBoxCount += userItem.getQuantity();
            } else if (userItem.getItem().getItemId() == 2) {
                luxuryBoxCount += userItem.getQuantity();
            }
        }

        return GachaCountResponse.from(normalBoxCount, luxuryBoxCount);
    }
}
