package com.walkerholic.walkingpet.domain.battle.function;

import com.walkerholic.walkingpet.domain.battle.dto.functionDTO.CharacterInfo;
import com.walkerholic.walkingpet.domain.battle.dto.functionDTO.UserRatingDTO;
import com.walkerholic.walkingpet.domain.battle.dto.response.BattleProgressInfo;
import com.walkerholic.walkingpet.domain.battle.dto.response.BattleResultInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RequiredArgsConstructor
@Component
public class BattleFunction {
    static final int STANDARD_RATING_MATCHING_GAP = 20;    //레이딩 범위 상수
    //데미지 배율
    static final int DAMAGE_SCALE = 5;
    //데미지 최소/최대 값 조정 상수
    static final int DAMAGE_GAP = 3;
    static final int WIN_EXP = 10;
    static final int WIN_RATING = 10;

    static final int LOSE_EXP = 5;
    static final int LOSE_RATING = -5;

    static boolean win = true;

    // 수가 비슷한 유저와 매칭시키는 함수
    public int matchingBattleUser(UserRatingDTO userRatingDTO, List<UserRatingDTO> userRatingList){
        int userId = userRatingDTO.getUserId();
        int userRating = userRatingDTO.getRating();

        //1. userDetailRepository로 전부 가져오기
        List<Integer> matchingUserIdList = new ArrayList<>();   //매칭이 될 유저 아이디 리스트

        //레이딩 범위
        int ratingGap = STANDARD_RATING_MATCHING_GAP;

        do{
            for(UserRatingDTO enemyList : userRatingList){
                int enemyRating = enemyList.getRating();
                int enemyUserId = enemyList.getUserId();

                //본인을 제외하면서
                if(enemyUserId != userId){
                    //레이팅 범위 내라면~
                    if(userRating-ratingGap <= enemyRating || enemyRating <= userRating + ratingGap)
                        matchingUserIdList.add(enemyUserId);
                }
            }

            //만약 해당 레이팅에 검색된 유저가 없다면 매칭 레이팅 범위를 늘린다.
            ratingGap += STANDARD_RATING_MATCHING_GAP;

        }while(matchingUserIdList.isEmpty());

        System.out.print("mathcing user Id list =");
        System.out.println(matchingUserIdList.toString());

        int enemyCount = matchingUserIdList.size();
        Random random = new Random();

        int enemyUserIdPos = random.nextInt(enemyCount-1);

        return matchingUserIdList.get(enemyUserIdPos);
    }


    /**
     *
     * @param power 공격하는 유저의 power값
     * @param defense 방어하는 유저의 power값
     * @return 최종적으로 방어하는 유저에게 들어가는 공격 데미지 값
     */
    public int getAttackDamage(int power, int defense){
        int minDamage = power*DAMAGE_SCALE - DAMAGE_GAP;
        int maxDamage = power*DAMAGE_SCALE + DAMAGE_GAP;

        int originAttackDamage = (int)(Math.random()*(maxDamage-minDamage+1)) + minDamage;
        System.out.println("originAttackDamage = " + originAttackDamage);

        int resultAttackDamage = 100*originAttackDamage/(100+defense);
        System.out.println("result Attack damage = " + resultAttackDamage);

        return resultAttackDamage;
    }

    //전투과정 정보 얻는 함수 -> 선공 / 후공
    public BattleProgressInfo getBattleProgress(CharacterInfo userCharacterInfo, CharacterInfo enemyCharacterInfo){
        List<Integer> userAttackDamageList = new ArrayList<>();
        List<Integer> enemyAttackDamageList = new ArrayList<>();
        List<Integer> userHealthList = new ArrayList<>();
        List<Integer> enemyHealthList = new ArrayList<>();
        List<Double> userLoseDamage = new ArrayList<>();
        List<Double> enemyLoseDamage = new ArrayList<>();

        int userHealth = userCharacterInfo.getHealth();
        int userPower = userCharacterInfo.getPower();
        int userDefense = userCharacterInfo.getDefense();
        int userAccumulateDamage = 0;

        int enemyHealth = enemyCharacterInfo.getHealth();
        int enemyPower = enemyCharacterInfo.getPower();
        int enemyDefense = enemyCharacterInfo.getDefense();
        int enemyAccumulateDamage = 0;

        boolean strikeFirst = true;

        System.out.printf("UserPower : %d, EnemyPower : %d", userCharacterInfo.getPower(), enemyCharacterInfo.getPower());

        while(userHealth != 0 && enemyHealth != 0){
            if(strikeFirst){
                //나의 공격
                int attackDamage = getAttackDamage(userPower, enemyDefense);
                enemyHealth -= attackDamage;
                enemyAccumulateDamage += attackDamage;
                //받는 데미지가 음수가 되면 체력을 0으로 만들어준다.
                if(enemyHealth <= 0){
                    enemyHealth = 0;
                    enemyAccumulateDamage = enemyCharacterInfo.getHealth();
                    win = true;
                }

                userAttackDamageList.add(attackDamage);
                enemyAttackDamageList.add(-1);

                userHealthList.add(userHealth);
                userLoseDamage.add(getPercentage(userCharacterInfo.getHealth(),userAccumulateDamage));
                enemyHealthList.add(enemyHealth);
                enemyLoseDamage.add(getPercentage(enemyCharacterInfo.getHealth(),enemyAccumulateDamage));
            }
            else{
                //상대방의 공격
                int attackDamage = getAttackDamage(enemyPower, userDefense);
                userHealth -= attackDamage;
                userAccumulateDamage += attackDamage;
                //받는 데미지가 음수가 되면 체력을 0으로 만들어준다.
                if(userHealth <= 0){
                    userHealth = 0;
                    userAccumulateDamage = userCharacterInfo.getHealth();
                    win = false;
                }

                enemyAttackDamageList.add(attackDamage);
                userAttackDamageList.add(-1);

                System.out.println();
                System.out.println("적이 때린 데미지: " + attackDamage);
                System.out.println("유저 누적 데미지: " + userAccumulateDamage);

                userHealthList.add(userHealth);
                userLoseDamage.add(getPercentage(userCharacterInfo.getHealth(),userAccumulateDamage));
                enemyHealthList.add(enemyHealth);
                enemyLoseDamage.add(getPercentage(enemyCharacterInfo.getHealth(),enemyAccumulateDamage));
            }
            strikeFirst = !strikeFirst;
        }

        System.out.println(userAttackDamageList.toString());
        System.out.println(enemyAttackDamageList.toString());

        return BattleProgressInfo.builder()
                .userAttackDamage(userAttackDamageList)
                .enemyAttackDamage(enemyAttackDamageList)
                .userHealth(userHealthList)
                .enemyHealth(enemyHealthList)
                .userLoseDamage(userLoseDamage)
                .enemyLoseDamage(enemyLoseDamage)
                .build();
    }

    public BattleResultInfo getBattleResult(Integer userId){
        if(win){
            return BattleResultInfo.builder()
                    .battleResult(win)
                    .experience(WIN_EXP)
                    .rating(WIN_RATING)
                    .build();
        }
        else{
            return BattleResultInfo.builder()
                    .battleResult(win)
                    .experience(LOSE_EXP)
                    .rating(LOSE_RATING)
                    .build();
        }
    }

    public double getPercentage(int maxHealth, int accumulateDamage){
        double result = (double)accumulateDamage/maxHealth;

        return Math.round(result * 100.0) / 100.0;
    }
}
