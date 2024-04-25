package com.walkerholic.walkingpet.domain.character.function;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class UserCharacterFunction {
    static final int INCREASE_MAX_EXP = 5;
    public int getMaxExperience(int level){
        if(level == 1){
            return 10;
        }
        else{
            return level*(level-1)*5/2 + 10;
        }
    }
}
