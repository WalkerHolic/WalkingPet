import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';
import 'package:walkingpet/character/widgets/character_box.dart';

class CharacterChange extends StatelessWidget {
  const CharacterChange({super.key});

  @override
  Widget build(BuildContext context) {
    // 현재 화면의 크기 가져오기
    double screenWidth = MediaQuery.of(context).size.width;
    double screenHeight = MediaQuery.of(context).size.height;

    return Stack(
      children: [
        // 1. 배경 (바깥)
        Positioned(
          child: Container(
            width: screenWidth,
            height: screenHeight,
            decoration: BoxDecoration(
              color: const Color(0xFF00A8FF),
              borderRadius: BorderRadius.circular(5),
            ),
          ),
        ),

        // 2. 배경 (안쪽)
        Center(
          child: Column(
            children: [
              const Padding(
                padding: EdgeInsets.all(7),
                child: Text(
                  '캐릭터변경',
                  style: TextStyle(
                    fontSize: 30,
                    color: Colors.white,
                  ),
                ),
              ),
              Positioned(
                bottom: 0,
                child: Container(
                  width: screenWidth * 0.7,
                  height: screenHeight * 0.5,
                  decoration: BoxDecoration(
                    color: const Color(0xFF283C57),
                    borderRadius: BorderRadius.circular(5),
                  ),
                ),
              ),
            ],
          ),
        ),

        // 3. 내용
        Center(
          child: Column(
            // mainAxisAlignment: MainAxisAlignment.center,
            children: [
              // 3-1. 캐릭터 선택 박스
              const SizedBox(
                height: 70,
              ),
              Padding(
                padding: const EdgeInsets.symmetric(vertical: 10),
                child: SizedBox(
                  height: screenHeight * 0.37,
                  width: screenWidth * 0.7,
                  child: GridView.builder(
                    gridDelegate:
                        const SliverGridDelegateWithFixedCrossAxisCount(
                      crossAxisCount: 3, // 1 개의 한 행에 보여줄 개수
                      childAspectRatio: 1 / 1.5, // item 의 가로, 세로 비율
                      mainAxisSpacing: 10, // 수평 Padding
                      crossAxisSpacing: 0, // 수직 Padding
                    ),
                    // itemCount: imageList.length,
                    itemCount: 12,
                    itemBuilder: (BuildContext context, int index) {
                      return const Center(
                        child: CharacterBox(
                            charaterId: 1,
                            characterGrade: 3,
                            userCharacterId: 1,
                            userCharacterLevel: 5,
                            userCharacterUpgrade: 7,
                            userCharacterStatus: 1,
                            characterName: '미친 소'),
                      );
                    },
                  ),
                ),
              ),

              // 3-2. 변경 버튼
              Positioned(
                bottom: 0,
                child: Stack(
                  alignment: Alignment.center,
                  children: [
                    TextButton(
                      onPressed: () => Navigator.of(context).pop(),
                      child: Image.asset(
                        'assets/buttons/green_button.png',
                      ),
                    ),
                    const Text(
                      '변경하기',
                      style: TextStyle(fontSize: 21),
                    ),
                  ],
                ),
              ),
            ],
          ),
        ),
      ],
    );
  }
}
