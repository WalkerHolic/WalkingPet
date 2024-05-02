import 'package:flutter/material.dart';

class Top1to3 extends StatelessWidget {
  final String ranking, characterId, nickname, step;

  const Top1to3({
    super.key,
    required this.ranking,
    required this.characterId,
    required this.nickname,
    required this.step,
  });

  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: const EdgeInsets.symmetric(
        horizontal: 5,
      ),
      child: Column(
        children: [
          // 1. 순위 (1~3위)
          Text(
            ranking,
            style: const TextStyle(
              fontSize: 34,
            ),
          ),

          // 2. 캐릭터 이미지 (현재: gif 파일)
          Image.asset(
            'assets/animals/cow/cow_walk.gif',
            // characterId
            height: 90,
          ),

          // 걸음수 생략
          // Text(
          //   step,
          //   style: const TextStyle(
          //     fontSize: 20,
          //   ),
          // ),

          // 3. 닉네임
          Text(
            nickname,
            style: const TextStyle(
              fontSize: 12,
            ),
          ),
        ],
      ),
    );
  }
}
