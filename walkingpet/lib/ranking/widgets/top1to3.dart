import 'package:flutter/material.dart';
import 'package:walkingpet/common/character_map.dart';

class Top1to3 extends StatelessWidget {
  final int ranking, step, characterId;
  final String nickname;

  const Top1to3({
    super.key,
    required this.ranking,
    required this.characterId,
    required this.nickname,
    required this.step,
  });

  @override
  Widget build(BuildContext context) {
    String animal = CharacterMap.idToAnimal[characterId] ?? "Unknown";

    return Padding(
      padding: const EdgeInsets.symmetric(
        horizontal: 5,
      ),
      child: Column(
        children: [
          // 1. 순위 (1~3위)
          Text(
            ranking.toString(),
            style: const TextStyle(
              fontSize: 34,
            ),
          ),

          // 2. 캐릭터 이미지
          Image.asset(
            // 'assets/animals/$animal/${animal}_walk.gif',
            'assets/animals/bunny/bunny_walk.gif',
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
