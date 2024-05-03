package com.walkerholic.walkingpet.domain.goal.service;

import com.walkerholic.walkingpet.domain.character.entity.UserCharacter;
import com.walkerholic.walkingpet.domain.character.repository.UserCharacterRepository;
import com.walkerholic.walkingpet.domain.goal.dto.response.GoalRewardDTO;
import com.walkerholic.walkingpet.domain.goal.dto.response.UserGoalInfoDTO;
import com.walkerholic.walkingpet.domain.goal.entity.Goal;
import com.walkerholic.walkingpet.domain.goal.repository.GoalRepository;
import com.walkerholic.walkingpet.domain.item.entity.UserItem;
import com.walkerholic.walkingpet.domain.item.repository.UserItemRepository;
import com.walkerholic.walkingpet.domain.levelup.dto.response.LevelUpResponse;
import com.walkerholic.walkingpet.domain.levelup.function.LevelUpFunction;
import com.walkerholic.walkingpet.domain.levelup.service.LevelUpService;
import com.walkerholic.walkingpet.domain.users.entity.UserDetail;
import com.walkerholic.walkingpet.domain.users.entity.Users;
import com.walkerholic.walkingpet.domain.users.repository.UserDetailRepository;
import com.walkerholic.walkingpet.domain.users.repository.UserStepRepository;
import com.walkerholic.walkingpet.domain.users.repository.UsersRepository;
import com.walkerholic.walkingpet.global.error.GlobalBaseException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;

import static com.walkerholic.walkingpet.domain.goal.dto.response.UserGoalInfoDTO.DAILY_GOAL_COUNT;
import static com.walkerholic.walkingpet.domain.goal.dto.response.UserGoalInfoDTO.WEEKLY_GOAL_COUNT;
import static com.walkerholic.walkingpet.global.error.GlobalErrorCode.*;

/**
 * 보상으로 경험치를 그냥 주는 GoalService
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class GoalService {
    private final GoalRepository goalRepository;
    private final UserStepRepository userStepRepository;
    private final UserItemRepository userItemRepository;
    private final UserDetailRepository userDetailRepository;

    //보상으로 주어지는 박스의 갯수
    public final int GOAL_REWARD_BOX_QUANTITY = 1;

    public final int GOAL_REWARD_EXP_ATTENDANCE = 3;
    public final int GOAL_REWARD_EXP_NORMAL = 5;

    /**
     * Controller - 유저의 목표 정보를 보여주는 service
     * @param userId 유저의 아이디
     * @return 유저의 목표 정보
     */
    public UserGoalInfoDTO getUserGoalInfo(int userId){
        Goal goal = getGoalByUserId(userId);
        int userStep = userStepRepository.findUserDailyStep(userId);

        return UserGoalInfoDTO.builder()
                .step(userStep)
                .dailyGoal(getDailyGoalStatus(goal.getDailyGoal()))
                .weeklyGoal(getWeeklyGoalStatus(goal.getWeeklyGoal()))
                .build();
    }

    /**
     * 일정 걸음수를 달성했을 때 보상을 저장하는 함수
     * @param userId 유저의 아이디
     * @param goalStep 달성한 걸음수
     * @return 유저의 목표 달성 여부가 갱신된 정보
     */
    public GoalRewardDTO getGoalReward(int userId, int goalStep){
        Goal goal = getGoalByUserId(userId);
        //1. 보상 정보 확인하기
        HashMap<String,Integer> goalReward = updateDailyGoalStatus(goal, goalStep);
        //2. 보상 저장하기
        saveReward(goalReward, userId);

        return GoalRewardDTO.builder()
                .goalReward(goalReward)
                .build();
    }

    /**
     * int형으로 저장되어 있는 일일 목표값을 달성여부를 판별해주는 boolean형 배열로 변환
     * @param dailyGoal DB에 저장되어있는 일일 목표 값
     * @return 달성여부를 판별해주는 boolean형 배열
     */
    public boolean[] getDailyGoalStatus(int dailyGoal){
        boolean[] dailyGoalArray = new boolean[DAILY_GOAL_COUNT];

        String dailyGoalString = String.format("%05d", Integer.parseInt(Integer.toBinaryString(dailyGoal)));
        System.out.println("dailyGoalString == " + dailyGoalString);

        System.out.println();
        System.out.println(dailyGoalString);
        System.out.println();

        for(int i = DAILY_GOAL_COUNT-1 ; i >= 0 ; i--){
            try{
                if(dailyGoalString.charAt(i) == '1'){
                    dailyGoalArray[DAILY_GOAL_COUNT-1-i] = true;
                }
                else if(dailyGoalString.charAt(i) == '0'){
                    dailyGoalArray[DAILY_GOAL_COUNT-1-i]= false;
                }
                else{
                    throw new Exception("이진수로 변환하는 중 문제가 발생했습니다.");
                }
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }

        System.out.println(Arrays.toString(dailyGoalArray));

        return dailyGoalArray;
    }

    /**
     * int형으로 저장되어 있는 주간 목표값을 달성여부를 판별해주는 boolean형 배열로 변환
     * @param weeklyGoal DB에 저장되어있는 일일 목표 값
     * @return 달성여부를 판별해주는 boolean형 배열
     */
    public boolean[] getWeeklyGoalStatus(int weeklyGoal){
        boolean[] weeklyGoalArray = new boolean[WEEKLY_GOAL_COUNT];

        String weeklyGoalString = String.format("%07d", Integer.parseInt(Integer.toBinaryString(weeklyGoal)));

        for(int i = WEEKLY_GOAL_COUNT-1; i >=0 ; i--){
            try{
                if(weeklyGoalString.charAt(i) == '1'){
                    weeklyGoalArray[WEEKLY_GOAL_COUNT-1-i] = true;
                }
                else if(weeklyGoalString.charAt(i) == '0'){
                    weeklyGoalArray[WEEKLY_GOAL_COUNT-1-i]= false;
                }
                else{
                    throw new Exception("이진수로 변환하는 중 문제가 발생했습니다.");
                }
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
        return weeklyGoalArray;
    }

    /**
     * 일일목표 상태 업데이트(0/3000/5000/7000/10000보 걷기에 따라~)
     * @param goal 목표
     * @param goalStep 걸음 수
     * return 경험치를 얻었는데 레벨업이 되었다면 true 반환
     */
    public HashMap<String, Integer> updateDailyGoalStatus(Goal goal, int goalStep){
        HashMap<String, Integer> goalReward = new HashMap<>();

        //int로 저장되어있는 일일 목표 값을 달성여부 boolean형 배열로 변환
        boolean[] dailyGoalStatus = getDailyGoalStatus(goal.getDailyGoal());
        System.out.println("원본 일일목표" +dailyGoalStatus.length);
        //아이템기능이 들어왔을 때 사용할 코드(이하 I)
        HashMap<String, Integer> reward = new HashMap<>();
        try {
            if(goalStep == 0){
                System.out.println("0보");
                dailyGoalStatus[0] = true;
                goalReward.put("Exp Item", 1);
            }
            else if(goalStep == 3000){
                System.out.println("3000보");
                dailyGoalStatus[1] = true;
                goalReward.put("Exp Item", 2);
            }
            else if(goalStep == 5000){
                System.out.println("5000보");
                dailyGoalStatus[2] = true;
                goalReward.put("Exp Item", 2);
                //주간목표 달성 기준
                updateWeeklyGoalStatus(goal);
            }
            else if(goalStep == 7000){
                System.out.println("7000보");
                dailyGoalStatus[3] = true;
                goalReward.put("Exp Item", 2);
                goalReward.put("Normal Box", 1);
            }
            else if(goalStep == 10000){
                System.out.println("10000보");
                dailyGoalStatus[4] = true;
                goalReward.put("Exp Item", 2);
                goalReward.put("Luxury Box", 1);
            }
            else{
                throw new Exception("보상에 해당하지 않는 걸음수가 입력값으로 들어왔습니다.");
            }

            System.out.println("일일목표 적용 결과 : " + Arrays.toString(dailyGoalStatus));

            int resultDailyGoalValue = booleanArrayStatusToInt(dailyGoalStatus);
            goal.setDailyGoal(resultDailyGoalValue);
            goalRepository.save(goal);

            System.out.println("변경후 일일목표" + goal.getDailyGoal().toString());
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }

        return goalReward;
     }

    //주간목표 업데이트
    public void updateWeeklyGoalStatus(Goal goal){
        LocalDate today = LocalDate.now();
        DayOfWeek dayOfWeek = today.getDayOfWeek();

        //그러면 일단 weekly 값을 가져오고 이 친구를 boolean형 배열로 바꾸고 그리고 해당 인덱스-1에 해당하는 부분을 true로 바꿔주고
        //바꾼애를 다시 정수로 바꾸고, 그 친구를 다시 save해야겠네.

        int weeklyGoalValue = goal.getWeeklyGoal();
        boolean[] weeklyGoalStatus = getWeeklyGoalStatus(weeklyGoalValue);
        weeklyGoalStatus[dayOfWeek.getValue()-1] = true;

        int resultWeeklyGoalValue = booleanArrayStatusToInt(weeklyGoalStatus);

        goal.setWeeklyGoal(resultWeeklyGoalValue);
        //어짜피 데일리 목표를 업데이트 하면서 주간 목표를 업데이트 하는거임!
        //골에는 데일리와 주간 동시에 가지고 있으므로 어짜피 데일리 마지막에서 save해줄거라면 굳이 여기서 안해줘도 될것 같다는 생각.
        //goalRepository.save(goal);
    }

    /**
     * 보상으로 받은 박스 아이템 저장
     * @param
     * @param
     */
    public void saveReward(HashMap<String, Integer> goalReward, int userId){
        HashMap<String, Integer> result = new HashMap<>();
        for(String itemName : goalReward.keySet()){
            UserItem userItem = getUserItemByUserIdAndItemName(userId, itemName);
            userItem.addItemQuantity(goalReward.get(itemName));
            userItemRepository.save(userItem);
        }
    }

    /**
     * true false로 이루어진 배열을 2진수로 바꿔서 정수로 표현해주는 함수
     * @param status true false로 표현한 일일/주간 목표 현황
     * @return 정수값
     */
    public int booleanArrayStatusToInt(boolean[] status){
        int result = 0;
        System.out.println(Arrays.toString(status));
        for(int i = 0; i < status.length ; i++){
            if(status[i]){
                result += (int)Math.pow(2,i);
            }
        }

        return result;
    }

    public UserItem getUserItemByUserIdAndItemName(int userId, String itemName){
        return userItemRepository.findByUserItemWithUserAndItemFetch(userId, itemName)
                .orElseThrow(()-> new GlobalBaseException(USER_ITEM_NOT_EXIST));
    }

    public UserDetail getUserDetailByUserId(int userId){
        return userDetailRepository.findByUserUserId(userId)
                .orElseThrow(()-> new GlobalBaseException(USER_DETAIL_NOT_FOUND));
    }

    public Goal getGoalByUserId(int userId){
        return goalRepository.findGoalByUserUserId(userId)
                .orElseThrow(()-> new GlobalBaseException(USER_GOAL_NOT_FOUND));
    }
}
