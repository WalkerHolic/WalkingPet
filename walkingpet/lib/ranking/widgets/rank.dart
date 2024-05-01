import 'package:flutter/material.dart';

class Rank extends StatelessWidget {
  final int ranking, step;
  final String nickname;

  // Rank.fromJson(Map<String, dynamic> json, {super.key})
  //     : ranking = json['ranking'],
  //       step = json['step'],
  //       nickname = json['nickname'];

  const Rank({
    super.key,
    required this.ranking,
    required this.nickname,
    required this.step,
  });

  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: const EdgeInsets.symmetric(
        vertical: 5,
      ),
      child: Row(
        mainAxisAlignment: MainAxisAlignment.center,
        children: [
          // 1. 순위
          SizedBox(
            width: 36,
            child: Text(
              ranking.toString(),
              style: const TextStyle(
                fontSize: 22,
              ),
              textAlign: TextAlign.center,
            ),
          ),

          // 2. 닉네임
          SizedBox(
            width: 120,
            child: Text(
              nickname,
              style: const TextStyle(
                fontSize: 16,
              ),
              textAlign: TextAlign.center,
            ),
          ),

          // 3. 걸음 수
          SizedBox(
            width: 130,
            child: Row(
              mainAxisAlignment: MainAxisAlignment.end,
              children: [
                Text(
                  step.toString(),
                  style: const TextStyle(
                    fontSize: 22,
                    color: Color.fromARGB(255, 241, 86, 9),
                  ),
                ),
                const SizedBox(width: 5),
                const Text(
                  '걸음',
                  style: TextStyle(
                    fontSize: 14,
                  ),
                ),
                const SizedBox(width: 5),
              ],
            ),
          ),
        ],
      ),
    );
  }
}




//  ranking, characterId, level, nickname, step