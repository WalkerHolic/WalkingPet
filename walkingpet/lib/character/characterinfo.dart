import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';
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
          Center(
            child: Column(
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
                TextButton(
                  onPressed: () {
                    Navigator.pushNamed(context, '/characterinfo');
                  },
                  child: Image.asset(
                    'assets/buttons/character_change_button.png',
                    scale: 0.85,
                  ),
                ),

                // 4. 레벨 & 경험치 바 & 경험치 아이템 사용 버튼 (+버튼)
                Padding(
                  padding: const EdgeInsets.symmetric(vertical: 15),
                  child: SizedBox(
                    width: screenWidth * 0.9,
                    child: Row(
                      mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                      children: [
                        // 4-1. 레벨
                        const Text(
                          'Lv. 7',
                          style: TextStyle(
                            fontSize: 23,
                          ),
                        ),

                        // 4-2. 경험치 바
                        SizedBox(
                          width: 200,
                          child: Stack(
                            alignment: Alignment.center,
                            children: [
                              // 4-2-1. Linear Progress Bar
                              const SizedBox(
                                width: 183,
                                height: 25,
                                child: ClipRRect(
                                  borderRadius:
                                      BorderRadius.all(Radius.circular(10)),
                                  child: LinearProgressIndicator(
                                    value: 0.7,
                                    backgroundColor: Color(0xFF727272),
                                    valueColor: AlwaysStoppedAnimation<Color>(
                                        Color(0xFFF3A52F)),
                                  ),
                                ),
                              ),

                              // 4-2-2. 도트 이미지
                              Image.asset(
                                'assets/images/character_bar_gray.png',
                                scale: 0.7,
                              ),

                              // 4-2-3. 경험치 값
                              const Text(
                                '110/200',
                                style: TextStyle(color: Colors.white),
                              )
                            ],
                          ),
                        ),

                        // 4-3. 경험치 아이템 사용 버튼 (+버튼)
                        SizedBox(
                          // width: 30,
                          child: Image.asset(
                            'assets/buttons/yellow_plus_button.png',
                            scale: 0.75,
                          ),
                        ),
                      ],
                    ),
                  ),
                ),

                // 5. 능력치 & 남은 능력치 포인트 & 초기화 버튼
                Padding(
                  padding: const EdgeInsets.symmetric(vertical: 10),
                  child: SizedBox(
                    width: screenWidth * 0.9,
                    child: Row(
                      mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                      children: [
                        // 5-1. 능력치: 체력, 공격력, 방어력
                        const Column(
                          children: [
                            CharacterInfoStat(
                                statname: '체력',
                                point: 400,
                                fix: 300,
                                upgrade: 100),
                            CharacterInfoStat(
                                statname: '공격력',
                                point: 18,
                                fix: 10,
                                upgrade: 8),
                            CharacterInfoStat(
                                statname: '방어력',
                                point: 12,
                                fix: 10,
                                upgrade: 2),
                          ],
                        ),

                        Column(
                          children: [
                            // 5-2. 남은 능력치 포인트
                            const Text(
                              '남은 포인트',
                              style: TextStyle(
                                fontSize: 13,
                              ),
                            ),
                            const Text(
                              '3',
                              style: TextStyle(
                                fontSize: 35,
                              ),
                            ),

                            // 5-3. 초기화 버튼
                            TextButton(
                              onPressed: () {
                                Navigator.pushNamed(context, '/characterinfo');
                              },
                              child: Image.asset(
                                'assets/buttons/character_reset_button.png',
                                scale: 0.8,
                              ),
                            ),
                          ],
                        ),
                      ],
                    ),
                  ),
                ),
              ],
            ),
          ),

          // 4. (임시 코드) levelup 관련
          Positioned(
            bottom: 0,
            child: ElevatedButton(
              onPressed: () {
                Navigator.pushNamed(context, '/levelup');
              },
              child: const Text('Level UP Preview'),
            ),
          ),
        ],
      ),
      bottomNavigationBar: const BottomNavBar(
        selectedIndex: 0,
      ),
    );
  }
}
