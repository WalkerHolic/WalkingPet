import 'dart:math' as math;

import 'package:flutter/material.dart';
import 'package:walkingpet/home/widgets/mainfontstyle.dart';

class BattleSide extends StatelessWidget {
  final int health;
  final int power;
  final int defense;
  final int rating;
  final int characterId; // 이걸로 캐릭터의 종류를 구별
  final int userCharacterLevel;
  final String nickname;
  final bool isLeft;

  const BattleSide(
      {super.key,
      required this.health,
      required this.power,
      required this.defense,
      required this.rating,
      required this.characterId,
      required this.userCharacterLevel,
      required this.nickname,
      required this.isLeft});

  @override
  Widget build(BuildContext context) {
    return Column(
      children: [
        const SizedBox(
          height: 300,
        ),
        Row(
          children: [
            Transform.translate(
              offset: const Offset(0, 3),
              child: const MainFontStyle(size: 16, text: "점수: "),
            ),
            MainFontStyle(
              size: 32,
              text: rating.toString(),
            ),
          ],
        ),
        Image.asset(
          isLeft
              ? 'assets/images/health_bar_left.png'
              : 'assets/images/health_bar_right.png',
          scale: 1.1,
        ),
        const SizedBox(
          height: 30,
        ),
        Transform(
          alignment: Alignment.center,
          transform: isLeft
              ? Matrix4.identity()
              : Matrix4.rotationY(math.pi), // π (파이) 각도만큼 Y축을 기준으로 회전
          child: Image.asset(
            isLeft
                ? 'assets/animals/cow/cow_attack.gif'
                : 'assets/animals/cow/cow_attack.gif',
            scale: 1.2,
          ),
        ),
      ],
    );
  }
}
