import 'package:flutter/material.dart';

class Rank extends StatelessWidget {
  final int ranking, score;
  final String nickname, rankingUnit;

  const Rank({
    super.key,
    required this.ranking,
    required this.nickname,
    required this.score,
    required this.rankingUnit,
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
          // 1,2,3위는 해당하는 메달 이미지를, 나머지는 텍스트만 나오도록 수정
          SizedBox(
            width: 36,
            child: ranking == 1
                ? Image.asset(
                    'assets/icons/gold_medal.png',
                    width: 36,
                    height: 36,
                    fit: BoxFit.contain,
                  )
                : ranking == 2
                    ? Image.asset(
                        'assets/icons/silver_medal.png',
                        width: 36,
                        height: 36,
                        fit: BoxFit.contain,
                      )
                    : ranking == 3
                        ? Image.asset(
                            'assets/icons/bronze_medal.png',
                            width: 36,
                            height: 36,
                            fit: BoxFit.contain,
                          )
                        : Text(
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
                  score.toString(),
                  style: const TextStyle(
                    fontSize: 22,
                    color: Color.fromARGB(255, 241, 86, 9),
                  ),
                ),
                const SizedBox(width: 5),
                Text(
                  rankingUnit,
                  style: const TextStyle(
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
