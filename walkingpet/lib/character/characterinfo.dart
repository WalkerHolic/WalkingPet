import 'package:flutter/material.dart';
import 'package:walkingpet/character/widgets/character_stat.dart';
import 'package:walkingpet/common/bottom_nav_bar.dart';

// 캐릭터 정보
class CharacterInfo extends StatelessWidget {
  // final String nickname;
  // final int characterId,
  //     level,
  //     experience,
  //     maxExperience;

  const CharacterInfo({super.key});

  @override
  Widget build(BuildContext context) {
    // 현재 화면의 크기 가져오기
    double screenWidth = MediaQuery.of(context).size.width;
    double screenHeight = MediaQuery.of(context).size.height;

    return Scaffold(
      body: Stack(
        children: [
          // 1. 배경 이미지
          Positioned.fill(
            child: Image.asset(
              'assets/backgrounds/characterinfo.png',
              fit: BoxFit.cover,
            ),
          ),

          // 2. 투명 레이어 (전체 영역)
          Positioned(
            child: Container(
              width: screenWidth,
              height: screenHeight,
              color: const Color.fromARGB(255, 255, 255, 255).withOpacity(0.4),
            ),
          ),

          // 2. 투명 레이어 (특정 영역만)
          Positioned(
            left: screenWidth * 0.05,
            top: screenHeight * 0.1,
            child: Container(
              width: screenWidth * 0.9,
              height: screenHeight * 0.75,
              decoration: BoxDecoration(
                color:
                    const Color.fromARGB(255, 255, 243, 212).withOpacity(0.75),
                borderRadius: BorderRadius.circular(10),
              ),
            ),
          ),

          // 3. 내용
          Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              // 1. 유저 닉네임
              const Text(
                '닉네임적어요',
                style: TextStyle(
                  // fontWeight: FontWeight.bold,
                  fontSize: 35,
                ),
              ),
              const SizedBox(height: 5),

              // 2. 캐릭터 이미지
              Image.asset(
                'assets/animals/cow/cow_walk.gif',
                height: 200,
                scale: 0.3,
              ),

              // 3. 변경 버튼
              Image.asset('assets/icons/character_change_button.png'),

              // ElevatedButton(
              //   onPressed: () {
              //     Navigator.pushNamed(context, '/characterinfo');
              //   },
              //   child: const Text('변경버튼'),
              // ),

              // DottedButton(
              //   onTap: () {
              //     print('도트 버튼 클릭!');
              //   },
              //   label: '클릭',
              //   dotSize: 6.0,
              //   dotColor: Colors.blue,
              //   textColor: Colors.blue,
              //   numberOfDots: 20,
              // ),
              const SizedBox(height: 10),

              // 4. 레벨 & 경험치 바
              Row(
                mainAxisAlignment: MainAxisAlignment.center,
                children: [
                  const Text(
                    'Lv. 7',
                    style: TextStyle(
                      fontSize: 25,
                    ),
                  ),
                  const SizedBox(
                    width: 20,
                  ),
                  SizedBox(
                    width: 200,
                    child: Stack(
                      alignment: Alignment.center,
                      children: [
                        const SizedBox(
                          width: 185,
                          height: 20,
                          child: ClipRRect(
                            borderRadius: BorderRadius.all(Radius.circular(10)),
                            child: LinearProgressIndicator(
                              value: 0.7,
                              backgroundColor: Colors.grey,
                              valueColor:
                                  AlwaysStoppedAnimation<Color>(Colors.green),
                            ),
                          ),
                        ),
                        // SizedBox(
                        //   width: 10,
                        //   Image.asset(
                        //     'assets/icons/character_change_button.png',
                        //     height: 30,
                        //   ),
                        // ),
                        Positioned(
                          right: 0,
                          child: SizedBox(
                            width: 200,
                            height: 20,
                            child: Image.asset(
                              'assets/icons/character_bar.png',
                            ),
                          ),
                        ),
                      ],
                    ),
                  ),
                ],
              ),
              const SizedBox(height: 10),

              Row(
                mainAxisAlignment: MainAxisAlignment.center,
                children: [
                  // 5. 능력치: 체력, 공격력, 방어력
                  const Column(
                    children: [
                      CharacterInfoStat(
                          statname: '체력', point: 400, fix: 300, upgrade: 100),
                      CharacterInfoStat(
                          statname: '공격력', point: 18, fix: 10, upgrade: 8),
                      CharacterInfoStat(
                          statname: '방어력', point: 12, fix: 10, upgrade: 2),
                    ],
                  ),
                  const SizedBox(
                    width: 20,
                  ),

                  // 6. & 7. 남은 능력치 포인트 & 초기화 버튼
                  Column(
                    children: [
                      // 6. 남은 능력치 포인트
                      const Text(
                        '남은 포인트',
                        style: TextStyle(
                          fontSize: 13,
                        ),
                      ),
                      const Text(
                        '3',
                        style: TextStyle(
                          fontSize: 30,
                        ),
                      ),
                      // 7. 초기화 버튼
                      Image.asset(
                        'assets/icons/character_reset_button.png',
                        height: 30,
                      ),
                      // ElevatedButton(
                      //   onPressed: () {
                      //     Navigator.pushNamed(context, '/characterinfo');
                      //   },
                      //   child: const Text('초기화'),
                      // ),
                    ],
                  ),
                ],
              ),
            ],
          ),
        ],
      ),
      bottomNavigationBar: const BottomNavBar(
        selectedIndex: 0,
      ),
    );
  }
}
