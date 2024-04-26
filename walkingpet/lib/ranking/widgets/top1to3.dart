import 'package:flutter/material.dart';

class Top1to3 extends StatelessWidget {
  // final int ranking, characterId, step;
  final String ranking, characterId, nickname, step;
  // final String level;

  const Top1to3({
    super.key,
    required this.ranking,
    required this.characterId,
    // required this.level,
    required this.nickname,
    required this.step,
  });

  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: const EdgeInsets.symmetric(
        horizontal: 15,
      ),
      child: Column(
        children: [
          Text(
            ranking,
            style: const TextStyle(
              fontSize: 35,
            ),
          ),
          Image.asset(
            'assets/animals/cow/cow_walk.gif',
            // characterId
            height: 100,
            // scale: 0.3,
          ),
          Text(
            step,
            style: const TextStyle(
              fontSize: 25,
            ),
          ),
          Text(
            nickname,
            style: const TextStyle(
              fontSize: 15,
            ),
          ),
        ],
      ),
    );
  }
}
