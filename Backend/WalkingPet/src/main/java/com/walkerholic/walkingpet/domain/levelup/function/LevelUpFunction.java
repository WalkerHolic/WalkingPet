package com.walkerholic.walkingpet.domain.levelup.function;

import com.walkerholic.walkingpet.domain.character.function.UserCharacterFunction;
import com.walkerholic.walkingpet.domain.levelup.dto.CharacterLevelExperienceInfo;
import com.walkerholic.walkingpet.domain.levelup.dto.LevelUpReward;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
@RequiredArgsConstructor
public class LevelUpFunction {
    private final UserCharacterFunction userCharacterFunction;

    //레벨업을 할 수 있는지 체크해주는 함수.
    public boolean checkLevelUp(CharacterLevelExperienceInfo requiredCheckLevelUpInfo, int getExperience){
        int requiredExperience = userCharacterFunction.getMaxExperience(requiredCheckLevelUpInfo.getNowLevel());

        //지금 경험치 + 얻은 경험치 양이 레벨업에 필요한 경험치보다 크다면 레벨업 가능
        if(requiredCheckLevelUpInfo.getNowExperience() + getExperience >= requiredExperience)
            return true;
        else return false;
    }

    //레벨업을 했을 때 레벨 몇까지 업하고 남는 경험치는 몇인지 알려주는 함수
    public CharacterLevelExperienceInfo getNextLevel(CharacterLevelExperienceInfo requiredCheckLevelUpInfo, int getExperience){
        //현재 레벨
        int nowLevel = requiredCheckLevelUpInfo.getNowLevel();
        //경험치를 얻었을 때 가지고 있는 경험치 양
        int nowExperience = requiredCheckLevelUpInfo.getNowExperience()+getExperience;
        //레벨업이 되기 위해 필요한 경험치 양
        int requiredLevelUpExperience = userCharacterFunction.getMaxExperience(nowLevel);

        while(nowExperience >= requiredLevelUpExperience){
            //1. 레벨업
            nowLevel++;
            //2. 레벨업에 필요한 경험치 양 만큼 현재 경험치에서 깎기
            nowExperience -= requiredLevelUpExperience;
            //3. 다음 레벨업에 필요한 경험치 양 갱시낳기
            requiredLevelUpExperience = userCharacterFunction.getMaxExperience(nowLevel);
        }

        return CharacterLevelExperienceInfo.builder()
                .nowLevel(nowLevel)
                .nowExperience(nowExperience)
                .build();
    }

    //레벨업이 되었을 때 얻게되는 보상의 정보
    public LevelUpReward getLevelUpReward(int nowLevel, int nextLevel){
        HashMap<String, Integer> itemReward = new HashMap<>();

        for(int i = nowLevel+1; i <= nextLevel ; i++){
            //5레벨 배수마다 고급상자 1개 지급
            if(i % 5 == 0){
                if(itemReward.containsKey("고급상자"))
                    itemReward.put("고급상자", 1);
                else{
                    int nowRewardQuantity = itemReward.get("고급상자");
                    itemReward.replace("고급상자", nowRewardQuantity+1);
                }
            }
            //아니면 일반상자 1개 지급
            else{
                if(itemReward.containsKey("일반상자"))
                    itemReward.put("일반상자", 1);
                else{
                    int nowRewardQuantity = itemReward.get("일반상자");
                    itemReward.replace("일반상자", nowRewardQuantity+1);
                }
            }
        }

        return LevelUpReward.builder()
                .statPoint(nextLevel - nowLevel)
                .itemReward(itemReward)
                .build();
    }
}
