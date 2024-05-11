import 'package:flutter/material.dart';

class GroupRanking extends StatelessWidget {
  const GroupRanking({super.key});

  @override
  Widget build(BuildContext context) {
    return const Center(
      child: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        children: [
          // 3-1. 개인 / 그룹 / 배틀 선택
          Text(
            '그룹랭킹',
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
