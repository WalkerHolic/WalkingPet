import 'package:flutter/material.dart';

class Rank extends StatelessWidget {
  // final int ranking, characterId, step;
  final String ranking, characterId, nickname, step;

  const Rank({
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
        vertical: 10,
      ),
      child: Row(
        mainAxisAlignment: MainAxisAlignment.center,
        children: [
          Text(
            ranking,
            style: const TextStyle(
              fontSize: 23,
            ),
          ),
          const SizedBox(width: 7),
          // Image.asset(
          //   'assets/animals/cow/cow_walk.gif',
          //   // characterId
          //   height: 40,
          //   // scale: 0.3,
          // ),
          // const SizedBox(width: 7),
          Text(
            nickname,
            style: const TextStyle(
              fontSize: 20,
            ),
          ),
          const SizedBox(width: 10),
          Text(
            step,
            style: const TextStyle(
              fontSize: 30,
            ),
          ),
          const SizedBox(width: 3),
          const Text(
            '걸음',
            style: TextStyle(
              fontSize: 20,
            ),
          ),
        ],
      ),
    );
  }
}




//  ranking, characterId, level, nickname, step