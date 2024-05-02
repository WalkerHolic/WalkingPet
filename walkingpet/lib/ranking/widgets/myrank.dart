import 'package:flutter/material.dart';
import 'package:walkingpet/common/character_map.dart';

class MyRank extends StatelessWidget {
  // final int ranking, step;
  final int ranking, step, characterId;
  final String nickname;
  // String animal = "";

  const MyRank(
      {super.key,
      required this.ranking,
      required this.step,
      required this.nickname,
      required this.characterId});

  // final String animal = CharacterMap.idToAnimal[characterId] ?? "Unknown";

  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: const EdgeInsets.symmetric(
        vertical: 5,
      ),
      child: Row(
        mainAxisAlignment: MainAxisAlignment.spaceEvenly,
        children: [
          // 1. 캐릭터 이미지
          Image.asset(
            'assets/animals/cow/cow_walk.gif',
            // 'assets/animals/$characterId/${characterId}_walk.gif',
            // characterId
            height: 90,
          ),

          Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              Row(
                mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                children: [
                  // 2. 순위
                  SizedBox(
                    width: 60,
                    child: Text(
                      '$ranking위',
                      style: const TextStyle(
                        fontSize: 22,
                      ),
                      textAlign: TextAlign.center,
                    ),
                  ),

                  // 3. 닉네임
                  SizedBox(
                    width: 130,
                    child: Text(
                      nickname,
                      style: const TextStyle(
                        fontSize: 18,
                      ),
                      textAlign: TextAlign.center,
                    ),
                  ),
                ],
              ),

              // 4. 걸음 수
              SizedBox(
                width: 160,
                child: Row(
                  mainAxisAlignment: MainAxisAlignment.end,
                  children: [
                    Text(
                      step.toString(),
                      style: const TextStyle(
                        fontSize: 24,
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
        ],
      ),
    );
  }
}
