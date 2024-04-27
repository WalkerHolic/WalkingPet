import 'package:flutter/material.dart';
import 'package:nes_ui/nes_ui.dart';
import 'package:walkingpet/ranking/widgets/personal.dart';
import 'package:walkingpet/ranking/widgets/group.dart';
import 'package:walkingpet/ranking/widgets/battle.dart';

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
          // const Text(
          //   '랭킹',
          //   style: TextStyle(fontSize: 30),
          // ),
          const NesTabView(
            tabs: [
              NesTabItem(
                child: YesterdayRanking(),
                label: '개인',
              ),
              NesTabItem(
                child: GroupRanking(),
                label: '그룹',
              ),
              NesTabItem(
                child: BattleRanking(),
                label: '배틀',
              ),
            ],
          ),
        ],
      ),
    );
  }
}
