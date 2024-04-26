import 'package:flutter/material.dart';
import 'package:walkingpet/goal/widgets/daily.dart';
import 'package:walkingpet/goal/widgets/weekly.dart';

class Goal extends StatelessWidget {
  const Goal({super.key});

  @override
  Widget build(BuildContext context) {
    // 현재 화면의 크기를 가져오기
    double screenWidth = MediaQuery.of(context).size.width;
    double screenHeight = MediaQuery.of(context).size.height;
    // 걸음수 더미값
    int steps = 7500;
    int goalStep = 10000;
    //주간 목표 달성
    bool isMonStamped = true;
    bool isTueStamped = false;
    bool isWedStamped = false;
    bool isThrStamped = false;
    bool isFriStamped = false;
    bool isSatStamped = false;
    bool isSunStamped = false;
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
                      const DailyGoalItem(title: "출석"),
                      const DailyGoalItem(title: "3000 걸음"),
                      const DailyGoalItem(title: "5000 걸음"),
                      const DailyGoalItem(title: "7000 걸음"),
                      const DailyGoalItem(title: "10000 걸음"),
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
                                  text: '월요일', isStamped: isMonStamped),
                              WeeklyGoalItem(
                                  text: '화요일', isStamped: isTueStamped),
                              WeeklyGoalItem(
                                  text: '수요일', isStamped: isWedStamped),
                              WeeklyGoalItem(
                                  text: '목요일', isStamped: isThrStamped),
                            ],
                          ),
                          Row(
                            mainAxisAlignment: MainAxisAlignment.center,
                            children: [
                              WeeklyGoalItem(
                                  text: '금요일', isStamped: isFriStamped),
                              WeeklyGoalItem(
                                  text: '토요일', isStamped: isSatStamped),
                              WeeklyGoalItem(
                                  text: '일요일', isStamped: isSunStamped),
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
