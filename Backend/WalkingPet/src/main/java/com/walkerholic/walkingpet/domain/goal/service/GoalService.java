package com.walkerholic.walkingpet.domain.goal.service;

import com.walkerholic.walkingpet.domain.character.entity.UserCharacter;
import com.walkerholic.walkingpet.domain.character.repository.UserCharacterRepository;
import com.walkerholic.walkingpet.domain.goal.dto.response.UserGoalInfo;
import com.walkerholic.walkingpet.domain.goal.entity.Goal;
import com.walkerholic.walkingpet.domain.goal.repository.GoalRepository;
import com.walkerholic.walkingpet.domain.item.entity.UserItem;
import com.walkerholic.walkingpet.domain.item.repository.UserItemRepository;
import com.walkerholic.walkingpet.domain.users.entity.UserDetail;
import com.walkerholic.walkingpet.domain.users.entity.UserStep;
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

import static com.walkerholic.walkingpet.domain.goal.dto.response.UserGoalInfo.DAILY_GOAL_COUNT;
import static com.walkerholic.walkingpet.domain.goal.dto.response.UserGoalInfo.WEEKLY_GOAL_COUNT;
import static com.walkerholic.walkingpet.global.error.GlobalErrorCode.*;


@Service
@RequiredArgsConstructor
@Slf4j
public class    GoalService {
    private final GoalRepository goalRepository;
    private final UsersRepository usersRepository;
    private final UserStepRepository userStepRepository;
    private final UserItemRepository userItemRepository;
    private final UserDetailRepository userDetailRepository;
    private final UserCharacterRepository userCharacterRepository;

    public final int GOAL_REWARD_BOX_QUANTITY = 1;

    public final int GOAL_REWARD_EXP_ATTENDANCE = 3;
    public final int GOAL_REWARD_EXP_NORMAL = 5;

    public final String NORMAL_BOX = "일반상자";
    public final String LUXURY_BOX = "고급상자";


    public UserGoalInfo getUserGoalInfo(int userId){
        Users user = getUserByUserId(userId);
        Goal goal = getUserGoalByUser(user);
        int userStep = getUserStepByUser(user);

        return UserGoalInfo.builder()
                .step(userStep)
                .dailyGoal(getDailyGoalStatus(goal.getDailyGoal()))
                .weeklyGoal(getWeeklyGoalStatus(goal.getWeeklyGoal()))
                .build();
    }

    public UserGoalInfo getGoalReward(int userId, int goalStep){
        Users user = getUserByUserId(userId);
        Goal goal = getUserGoalByUser(user);

        updateDailyGoalStatus(user, goal, goalStep);

        return  getUserGoalInfo(userId);
    }

    public Users getUserByUserId(int userId){
        return usersRepository.findUsersByUserId(userId)
                .orElseThrow(()-> new GlobalBaseException(USER_NOT_FOUND));
    }

    public Goal getUserGoalByUser(Users user){
        return goalRepository.findGoalByUser(user)
                .orElseThrow(()->new GlobalBaseException(USER_GOAL_NOT_FOUND));
    }

    public UserItem getUserItemByUserIdAndItemName(int userId, String itemName){
        return userItemRepository.findByUserItemWithUserAndItemFetch(userId, itemName)
                .orElseThrow(()-> new GlobalBaseException(USER_ITEM_NOT_EXIST));
    }

    public UserDetail getUserDetailByUsers(Users user){
        return userDetailRepository.findUserDetailByUser(user)
                .orElseThrow(()-> new GlobalBaseException(USER_DETAIL_NOT_FOUND));
    }

    public UserCharacter getUserCharacterByUserDetail(UserDetail userDetail){
        return userDetail.getSelectUserCharacter();
    }

    public int getUserStepByUser(Users user){

        return userStepRepository.findUserStepByUser(user)
                .orElseThrow(()->new GlobalBaseException(USER_STEP_NOT_FOUND)).getDailyStep();
    }

    //int형으로 저장되어있는 일일목표 값을 boolean형으로 반환
    public boolean[] getDailyGoalStatus(int dailyGoal){
        boolean[] dailyGoalArray = new boolean[DAILY_GOAL_COUNT];

        String dailyGoalString = String.format("%05d", Integer.parseInt(Integer.toBinaryString(dailyGoal)));
        System.out.println("dailyGoalString == " + dailyGoalString);

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

    //int형으로 저장되어있는 주간목표 값을 boolean형으로 반환
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

    //일일목표 상태 업데이트(0/3000/5000/7000/10000보 걷기에 따라~)
    public void updateDailyGoalStatus(Users user, Goal goal, int step){
        boolean[] dailyGoalStatus = getDailyGoalStatus(goal.getDailyGoal());
        System.out.println("원본 일일목표" +dailyGoalStatus.length);

        try {
            if(step == 0){
                System.out.println("0보");
                dailyGoalStatus[0] = true;
                getGoalRewardExp(user, GOAL_REWARD_EXP_ATTENDANCE);
            }
            else if(step == 3000){
                System.out.println("3000보");
                dailyGoalStatus[1] = true;
                getGoalRewardExp(user, GOAL_REWARD_EXP_NORMAL);
            }
            else if(step == 5000){
                System.out.println("5000보");
                dailyGoalStatus[2] = true;
                getGoalRewardExp(user, GOAL_REWARD_EXP_NORMAL);
                //주간목표 달성 기준
                updateWeeklyGoalStatus(goal);
            }
            else if(step == 7000){
                System.out.println("7000보");
                dailyGoalStatus[3] = true;
                getGoalRewardExp(user, GOAL_REWARD_EXP_NORMAL);
                getGoalRewardBox(user.getUserId(), NORMAL_BOX);
            }
            else if(step == 10000){
                System.out.println("10000보");
                dailyGoalStatus[4] = true;
                getGoalRewardExp(user, GOAL_REWARD_EXP_NORMAL);
                getGoalRewardBox(user.getUserId(), LUXURY_BOX);
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

    //보상으로 받은 박스 아이템 저장
    public void getGoalRewardBox(int userId, String box){
        UserItem userItem = getUserItemByUserIdAndItemName(userId, box);
        userItem.addItemQuantity(GOAL_REWARD_BOX_QUANTITY);
    }

    //보상으로 받은 경험치 유저캐릭터에 저장
    public void getGoalRewardExp(Users user, int rewardExp){
        System.out.println(user.getUserId() + "얻은 경험치 량 = "+ rewardExp);
        UserCharacter userCharacter = getUserCharacterByUserDetail(getUserDetailByUsers(user));
        System.out.println("기존 경험치 = " + userCharacter.getExperience());
        userCharacter.addExperience(rewardExp);
        System.out.println("적용한 뒤 경험치 = " + userCharacter.getExperience());
        userCharacterRepository.save(userCharacter);
    }

    //boolean형 배열을 정수값으로 만들기
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
}
