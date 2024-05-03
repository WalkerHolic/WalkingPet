import 'package:flutter/material.dart';

class CharacterBox extends StatelessWidget {
  final int charaterId,
      characterGrade,
      userCharacterId,
      userCharacterLevel,
      userCharacterUpgrade,
      userCharacterStatus;
  final String characterName;

  const CharacterBox(
      {super.key,
      required this.charaterId,
      required this.characterGrade,
      required this.userCharacterId,
      required this.userCharacterLevel,
      required this.userCharacterUpgrade,
      required this.userCharacterStatus,
      required this.characterName});

  @override
  Widget build(BuildContext context) {
    // 현재 화면의 크기 가져오기
    double screenWidth = MediaQuery.of(context).size.width;
    // double screenHeight = MediaQuery.of(context).size.height;

    return Container(
      width: screenWidth * 0.2,
      decoration: BoxDecoration(
        color: const Color(0xFF1D2A3D),
        border: Border.all(
          color: const Color(0xFF3C6093),
          width: 2, // 테두리 두께
        ),
        borderRadius: BorderRadius.circular(10),
        // boxShadow: [
        //   BoxShadow(
        //     color: Colors.grey.withOpacity(0.5), // 그림자 색상
        //     spreadRadius: 2, // 그림자 범위
        //     blurRadius: 7, // 그림자 흐림 효과
        //     offset: const Offset(0, 3), // 그림자 위치 조정
        //   ),
        // ],
      ),
      child: Stack(
        children: [
          Column(
            children: [
              Row(
                mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                children: [
                  // 1. 캐릭터 고유 레벨 (1-3성)
                  Row(
                    children: [
                      ...List.generate(
                        characterGrade,
                        (_) => const Icon(
                          Icons.star,
                          color: Colors.yellow,
                          size: 10,
                        ),
                      ),
                    ],
                  ),

                  // 2. 사용자의 캐릭터 보유 개수
                  Row(
                    children: [
                      const Text(
                        '+',
                        style: TextStyle(color: Colors.yellow, fontSize: 10),
                      ),
                      Text(
                        userCharacterUpgrade.toString(),
                        style:
                            const TextStyle(color: Colors.yellow, fontSize: 10),
                      ),
                    ],
                  ),
                ],
              ),

              // 3. 캐릭터 이미지(id)
              Image.asset(
                'assets/animals/cow/cow_idle.gif',
                height: 70,
                // scale: 1,
              ),

              // 4. 사용자의 캐릭터 레벨
              Row(
                mainAxisAlignment: MainAxisAlignment.center,
                children: [
                  const Text(
                    'Lv. ',
                    style: TextStyle(color: Colors.white, fontSize: 10),
                  ),
                  Text(
                    userCharacterLevel.toString(),
                    style: const TextStyle(color: Colors.white, fontSize: 10),
                  ),
                ],
              ),

              // 5. 캐릭터 고유 이름
              Text(
                characterName,
                style: const TextStyle(color: Colors.white, fontSize: 11),
              )
            ],
          ),

          // 6. 사용자의 캐릭터 보유 여부 => 자물쇠로 표시
          // Center(
          //   child: Image.asset(
          //     'assets/images/character_lock.png',
          //     // height: 200,
          //     // scale: 0.3,
          //   ),
          // ),
        ],
      ),
    );
  }
}