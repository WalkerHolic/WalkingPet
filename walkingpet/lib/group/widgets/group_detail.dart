import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';
import 'package:walkingpet/common/bottom_nav_bar.dart';
import 'package:walkingpet/group/widgets/member_scrollable_list.dart';
import 'package:http/http.dart' as http; // http 요청을 위해

// git branch test
class GroupDetail extends StatelessWidget {
  final String groupName; //팀 이름

  const GroupDetail({
    super.key,
    required this.groupName,
  });

  @override
  Widget build(BuildContext context) {
    double screenWidth = MediaQuery.of(context).size.width;
    double screenHeight = MediaQuery.of(context).size.height;
    int goalStep = 10000; // 목표 걸음수
    int currStep = 3000; // 현재 결음수

    return Scaffold(
      body: Stack(
        children: <Widget>[
          Positioned.fill(
            child: Image.asset(
              'assets/backgrounds/group.png',
              fit: BoxFit.cover,
            ),
          ),
          Positioned.fill(
            child: Container(
              color: Colors.white.withOpacity(0.6),
            ),
          ),
          Center(
            child: Container(
              width: screenWidth * 0.9,
              height: screenHeight * 0.8,
              decoration: BoxDecoration(
                  color: const Color(0xfffff3dc).withOpacity(0.9),
                  borderRadius: BorderRadius.circular(20)),
              child: Padding(
                padding:
                    const EdgeInsets.symmetric(vertical: 20, horizontal: 10),
                child: Column(
                  children: <Widget>[
                    const Text(
                      "대전 2팀 모여라!",
                      style: TextStyle(fontSize: 33),
                    ),
                    const SizedBox(height: 10),
                    const Text(
                      "그룹의 홈",
                      style: TextStyle(fontSize: 20),
                    ),
                    Padding(
                      padding: const EdgeInsets.symmetric(vertical: 20),
                      child: Container(
                        width: screenWidth * 0.8,
                        height: screenHeight * 0.1,
                        decoration: BoxDecoration(
                          color: Colors.white.withOpacity(0.7),
                          borderRadius: BorderRadius.circular(20),
                        ),
                        child: Column(
                          //뒤에 배경 깔기위해... container로 묶으려고
                          children: [
                            const Text(
                              "함께 10000보 걷기",
                              style: TextStyle(fontSize: 25),
                            ),
                            Stack(
                              alignment: Alignment.center,
                              // 프로그래스 바 위에 걸음 수를 띄우기 위해
                              children: <Widget>[
                                SizedBox(
                                  // 선형 프로그래스 바의 크기 조절을 위해
                                  width: 250,
                                  child: LinearProgressIndicator(
                                    // 그룹 목표
                                    value: currStep / goalStep,
                                    minHeight: 20,
                                    color: const Color(0xff7DF39D),
                                    backgroundColor: const Color(0xff43695c),
                                  ),
                                ),
                                Text(
                                  "$currStep / $goalStep",
                                  style: const TextStyle(
                                    color: Colors.white,
                                    fontSize: 25,
                                  ),
                                )
                              ],
                            ),
                          ],
                        ),
                      ),
                    ),
                    const Text(
                      "그룹원 목록",
                      style: TextStyle(
                        fontSize: 25,
                      ),
                    ),
                    const SizedBox(
                      height: 20,
                    ),
                    MemberScrollableList(),
                    ElevatedButton(
                        onPressed: () {}, child: const Text("그룹 나가기"))
                  ],
                ),
              ),
            ),
          ),
          Positioned(
            left: screenWidth * 0.9 / 2 - 50,
            top: screenHeight * 0.35,
            child: Image.asset(
              'assets/items/stars.png',
              width: 150,
            ),
          ),
        ],
      ),
      bottomNavigationBar: const BottomNavBar(
        selectedIndex: 4,
      ),
    );
  }
}
