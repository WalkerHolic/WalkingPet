import 'package:flutter/material.dart';
import 'package:nes_ui/nes_ui.dart';
import 'package:walkingpet/ranking/widgets/nes_tab.dart';
// import 'package:nes_ui/nes_ui.dart';
// import 'package:walkingpet/ranking/widgets/nes_tab.dart';
import 'package:walkingpet/ranking/widgets/rank.dart';
import 'package:walkingpet/ranking/widgets/top1to3.dart';

class Ranking extends StatelessWidget {
  const Ranking({super.key});

  @override
  Widget build(BuildContext context) {
    // 현재 화면의 크기 가져오기
    double screenWidth = MediaQuery.of(context).size.width;
    double screenHeight = MediaQuery.of(context).size.height;

    return Scaffold(
      // appBar: AppBar(
      //   title: const Text('랭킹'),
      // ),
      body: Stack(
        children: [
          // 1. 배경 이미지
          Positioned.fill(
            child: Image.asset(
              'assets/backgrounds/ranking.png',
              fit: BoxFit.cover,
            ),
          ),

          // 2. 투명 레이어
          Positioned(
            left: screenWidth * 0.04,
            top: screenHeight * 0.1,
            child: Container(
              width: screenWidth * 0.92,
              height: screenHeight * 0.8,
              color: const Color.fromARGB(255, 255, 255, 255).withOpacity(0.8),
            ),
          ),

          // 3. 내용
          const Center(
            child: Column(
              mainAxisAlignment: MainAxisAlignment.center,
              children: [
                // 3-1. 랭킹
                Text('랭킹 & X버튼'),

                // 3-2. 개인 / 그룹 / 배틀 선택
                Text('개인'),

                // 3-3. 어제 / 실시간 / 누적 선택
                Text('어제 | 실시간 | 누적'),

                // 3-4. 1~3위 표시
                Row(
                  mainAxisAlignment: MainAxisAlignment.center,
                  children: [
                    Top1to3(
                      ranking: '2',
                      characterId: '13',
                      nickname: '이겜재밌냐ㅎ',
                      step: '226,254',
                    ),
                    Top1to3(
                      ranking: '1',
                      characterId: '13',
                      nickname: '1등이다야효',
                      step: '362,254',
                    ),
                    Top1to3(
                      ranking: '3',
                      characterId: '13',
                      nickname: '나는지은이얌',
                      step: '160,254',
                    ),
                  ],
                ),

                // 3-5. 유저의 랭킹 표시
                Rank(
                  ranking: '897',
                  characterId: '1',
                  nickname: '하이빅싸피야',
                  step: '226,254',
                ),

                // 3-6. 4~10위 표시
                Text(
                  'Top 10',
                  style: TextStyle(fontSize: 30),
                ),
                Column(
                  mainAxisAlignment: MainAxisAlignment.center,
                  children: [
                    Rank(
                      ranking: '1',
                      characterId: '13',
                      nickname: '1등이다야효',
                      step: '362,254',
                    ),
                    Rank(
                      ranking: '2',
                      characterId: '13',
                      nickname: '이겜재밌냐ㅎ',
                      step: '226,254',
                    ),
                    Rank(
                      ranking: '3',
                      characterId: '13',
                      nickname: '나는지은이얌',
                      step: '160,254',
                    ),
                    Rank(
                      ranking: '4',
                      characterId: '1',
                      nickname: '룰루루4등잼',
                      step: '226,254',
                    ),
                    Rank(
                      ranking: '5',
                      characterId: '1',
                      nickname: '우희힇울렐레',
                      step: '226,254',
                    ),
                  ],
                ),
              ],
            ),
          ),
        ],
      ),
    );
  }
}
