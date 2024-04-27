import 'package:flutter/material.dart';

class BattleRanking extends StatelessWidget {
  const BattleRanking({super.key});

  @override
  Widget build(BuildContext context) {
    return const Center(
      child: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        children: [
          // 3-1. 개인 / 그룹 / 배틀 선택
          Text(
            '배틀랭킹',
            style: TextStyle(fontSize: 50),
          ),
          Text(
            '- 공사중 -',
            style: TextStyle(fontSize: 35),
          ),
        ],
      ),
    );
  }
}
