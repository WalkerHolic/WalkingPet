import 'package:flutter/material.dart';

class GroupRank extends StatelessWidget {
  final int ranking, teamPoint;
  final String teamName;

  const GroupRank({
    super.key,
    required this.ranking,
    required this.teamPoint,
    required this.teamName,
    // required this.explain,
  });

  @override
  Widget build(BuildContext context) {
    return Container(
      decoration: BoxDecoration(
        color: const Color.fromARGB(255, 255, 255, 255).withOpacity(0.8),
        borderRadius: BorderRadius.circular(5),
      ),
      margin: const EdgeInsets.symmetric(horizontal: 10, vertical: 5),
      padding: const EdgeInsets.symmetric(vertical: 5),
      child: Column(
        children: [
          Row(
            mainAxisAlignment: MainAxisAlignment.spaceEvenly,
            children: [
              const SizedBox(width: 25),
              // 1. 순위 : 1,2,3위는 해당하는 메달 이미지를, 나머지는 텍스트만 나오도록 수정
              SizedBox(
                width: 120,
                child: Row(
                  mainAxisAlignment: MainAxisAlignment.center,
                  children: [
                    SizedBox(
                      width: 70,
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
                                      style: const TextStyle(fontSize: 24),
                                      textAlign: TextAlign.end,
                                    ),
                    ),
                    const Text(
                      ' 위',
                      style: TextStyle(fontSize: 22),
                    ),
                  ],
                ),
              ),

              // 2. 그룹 점수
              SizedBox(
                width: 100,
                child: Row(
                  mainAxisAlignment: MainAxisAlignment.end,
                  children: [
                    Text(
                      teamPoint.toString(),
                      style: const TextStyle(
                        fontSize: 24,
                        color: Color.fromARGB(255, 241, 86, 9),
                      ),
                    ),
                    const SizedBox(width: 5),
                    const Text(
                      '점',
                      style: TextStyle(fontSize: 16),
                    ),
                    const SizedBox(width: 5),
                  ],
                ),
              ),
            ],
          ),

          // 3. 그룹 이름
          SizedBox(
            width: 300,
            child: Text(
              teamName,
              style: const TextStyle(
                fontSize: 24,
              ),
              textAlign: TextAlign.center,
            ),
          ),

          // 4. 그룹 설명
          // Text(
          //   explain,
          //   style: const TextStyle(fontSize: 14),
          // ),
        ],
      ),
    );
  }
}
