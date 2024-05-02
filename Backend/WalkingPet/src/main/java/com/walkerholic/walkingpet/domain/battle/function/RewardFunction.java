package com.walkerholic.walkingpet.domain.battle.function;

import com.walkerholic.walkingpet.domain.battle.dto.response.RewardItem;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class RewardFunction {
    static final int BOX_PROBABILITY = 10;
    static final int LUXURY_BOX_PROBABILITY = 30;
    static final int NORMAL_BOX_PROBABILITY = 70;
    static final int WIN_EXP_ITEM_QUANTITY = 2;
    static final int LOSE_EXP_ITEM_QUANTITY = 1;

    public RewardItem getRewardItem(boolean batlleResult){
        Random random = new Random();
        int boxProbability = random.nextInt(100);

        String box = null;

        if(batlleResult){
            if(boxProbability <= BOX_PROBABILITY){
                int randomBoxCheck = random.nextInt(100);
                if(randomBoxCheck <= LUXURY_BOX_PROBABILITY){
                    box = "luxury";
                }
                else if(randomBoxCheck <= NORMAL_BOX_PROBABILITY){
                    box = "normal";
                }
            }
            return RewardItem.builder()
                    .expItem(LUXURY_BOX_PROBABILITY)
                    .box(box)
                    .build();
        }
        else{
            return RewardItem.builder()
                    .expItem(LOSE_EXP_ITEM_QUANTITY)
                    .box(box)
                    .build();
        }
    }
}
