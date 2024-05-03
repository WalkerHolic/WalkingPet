import 'package:flutter/material.dart';
import 'package:walkingpet/goal/widgets/daily.dart';
import 'package:walkingpet/goal/widgets/weekly.dart';
// 페이지 로드 시 목표 달성 여부를 로드
import 'package:walkingpet/services/goal/get_goal_info.dart';

class Goal extends StatefulWidget {
  const Goal({super.key});

  @override
  State<Goal> createState() => _GoalState();
}

class _GoalState extends State<Goal> {
  int steps = 0;
  List<bool> dailyGoals = [];
  List<bool> weeklyGoals = [];
  Map<String, bool> stampedDays = {};
  //일일 목표 관련
  bool isAttended = false;
  bool isWalked3000 = false;
  bool isWalked5000 = false;
  bool isWalked7000 = false;
  bool isWalked10000 = false;

  @override
  void initState() {
    super.initState();
    //then() 메서드를 사용하여 Future가 완료되었을 때 얻은 결과를 처리
    getGoalInfo().then(
      (data) {
        setState(
          () {
            //data를 State 변수에 저장
            steps = data['step'];
            dailyGoals = List<bool>.from(data['dailyGoal']);
            weeklyGoals = List<bool>.from(data['weeklyGoal']);
            print('받아서 새로 저장한 데이터: $weeklyGoals');
            // Map 업데이트
            stampedDays = {
              '월요일': weeklyGoals[0],
              '화요일': weeklyGoals[1],
              '수요일': weeklyGoals[2],
              '목요일': weeklyGoals[3],
              '금요일': weeklyGoals[4],
              '토요일': weeklyGoals[5],
              '일요일': weeklyGoals[6]
            };
            //일일 목표
            isAttended = dailyGoals[0];
            isWalked3000 = dailyGoals[1];
            isWalked5000 = dailyGoals[2];
            isWalked7000 = dailyGoals[3];
            isWalked10000 = dailyGoals[4];
          },
        );
      },
    );
  }

  @override
  Widget build(BuildContext context) {
    // 현재 화면의 크기를 가져오기
    double screenWidth = MediaQuery.of(context).size.width;
    double screenHeight = MediaQuery.of(context).size.height;
    // 걸음수 더미값

    int goalStep = 10000;

    return Scaffold(
      // appBar: AppBar(
      //   title: const Text('목표'),
      // ),
      body: Stack(
        children: <Widget>[
          //1. 배경 이미지
          Positioned.fill(
            child: Image.asset(
              'assets/backgrounds/goal.png',
              fit: BoxFit.cover,
            ),
          ),
          Center(
            child: Container(
              width: screenWidth * 0.9,
              height: screenHeight * 0.9,
              color: const Color(0xfffff3dc).withOpacity(0.8),
            ),
          ),
          Column(
            mainAxisAlignment: MainAxisAlignment.start,
            children: [
              const SizedBox(
                height: 75,
              ),
              Center(
                child: SizedBox(
                  width: screenWidth * 0.6,
                  height: screenWidth * 0.6,
                  child: FittedBox(
                    child: Column(
                      children: [
                        Stack(
                          alignment: Alignment.center,
                          children: <Widget>[
                            CircularProgressIndicator(
                              backgroundColor: const Color(0xffeeeeee),
                              color: const Color(0xff47E1A9),
                              value: steps / goalStep,
                              strokeWidth: 3,
                            ),
                            Column(
                              children: [
                                Text(
                                  '$steps',
                                  style: const TextStyle(
                                    fontSize: 8,
                                  ),
                                ),
                                const Text(
                                  "오늘",
                                  style: TextStyle(fontSize: 5),
                                ),
                                Text('목표 : $goalStep',
                                    style: const TextStyle(fontSize: 3))
                              ],
                            ),
                          ],
                        ),
                      ],
                    ),
                  ),
                ),
              ),
              Center(
                child: Padding(
                  padding: const EdgeInsets.symmetric(vertical: 30),
                  child: Column(
                    mainAxisAlignment: MainAxisAlignment.center,
                    children: [
                      const Text(
                        "일일 목표",
                        style: TextStyle(
                          fontSize: 35,
                        ),
                      ),
                      DailyGoalItem(
                        title: "출석",
                        isActivated: isAttended,
                        goalSteps: 0,
                      ),
                      DailyGoalItem(
                        title: "3000 걸음",
                        isActivated: isWalked3000,
                        goalSteps: 3000,
                      ),
                      DailyGoalItem(
                        title: "5000 걸음",
                        isActivated: isWalked5000,
                        goalSteps: 5000,
                      ),
                      DailyGoalItem(
                        title: "7000 걸음",
                        isActivated: isWalked7000,
                        goalSteps: 7000,
                      ),
                      DailyGoalItem(
                        title: "10000 걸음",
                        isActivated: isWalked10000,
                         goalSteps: 10000,
                      ),
                      const Text(
                        "주간 목표",
                        style: TextStyle(
                          fontSize: 35,
                        ),
                      ),
                      Column(
                        children: [
                          Row(
                              mainAxisAlignment: MainAxisAlignment.center,
                              children: [
                                WeeklyGoalItem(
                                    // 단축연산 - 왼쪽 값이 null이면 false 반환
                                    text: '월요일',
                                    isStamped: stampedDays['월요일'] ?? false),
                                WeeklyGoalItem(
                                    text: '화요일',
                                    isStamped: stampedDays['월요일'] ?? false),
                                WeeklyGoalItem(
                                    text: '수요일',
                                    isStamped: stampedDays['수요일'] ?? false),
                                WeeklyGoalItem(
                                    text: '목요일',
                                    isStamped: stampedDays['목요일'] ?? false),
                              ]),
                          Row(
                            mainAxisAlignment: MainAxisAlignment.center,
                            children: [
                              WeeklyGoalItem(
                                  text: '금요일',
                                  isStamped: stampedDays['금요일'] ?? false),
                              WeeklyGoalItem(
                                  text: '토요일',
                                  isStamped: stampedDays['토요일'] ?? false),
                              WeeklyGoalItem(
                                  text: '일요일',
                                  isStamped: stampedDays['일요일'] ?? false),
                            ],
                          ),
                        ],
                      ),
                    ],
                  ),
                ),
              ),
            ],
          ),
        ],
      ),
    );
  }
}
