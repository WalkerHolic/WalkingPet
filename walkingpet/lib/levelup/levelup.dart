import 'package:flutter/material.dart';

class LevelUp extends StatelessWidget {
  const LevelUp({super.key});

  @override
  Widget build(BuildContext context) {
    // 현재 화면의 크기 가져오기
    double screenWidth = MediaQuery.of(context).size.width;
    double screenHeight = MediaQuery.of(context).size.height;

    return Stack(
      children: [
        // 1. 배경 이미지
        Positioned.fill(
          child: ClipRRect(
            borderRadius: BorderRadius.circular(10),
            child: Image.asset(
              'assets/backgrounds/levelup_sky.png',
              fit: BoxFit.cover,
            ),
          ),
        ),

        // 2. 투명 레이어 (전체 영역)
        Positioned(
          child: Container(
            width: screenWidth,
            height: screenHeight,
            decoration: BoxDecoration(
              color: const Color.fromARGB(255, 255, 255, 255).withOpacity(0.3),
              borderRadius: BorderRadius.circular(10),
            ),
          ),
        ),

        // 3. 내용
        Center(
          child: Column(
            // mainAxisAlignment: MainAxisAlignment.center,
            children: [
              // 1. 레벨업 표시
              // const Text('LEVEL UP !'),
              const SizedBox(
                height: 90,
              ),

              // 2. 캐릭터 이미지
              Image.asset(
                'assets/animals/cow/cow_idle.gif',
                height: 200,
                scale: 0.3,
              ),

              // 3. 레벨 상승 표시
              const Row(
                mainAxisAlignment: MainAxisAlignment.center,
                children: [
                  Text('Lv.47'),
                  Text(' > '),
                  Text(
                    'Lv.48',
                    style: TextStyle(fontSize: 25),
                  ),
                ],
              ),

              // 4. 보상
              const Column(
                children: [
                  Text('--- 보상 ---'),
                  Text('능력치 포인트 + 5'),
                  Text('선물상자 X 1'),
                ],
              ),

              const SizedBox(
                height: 20,
              ),

              // 5. 확인 버튼
              TextButton(
                onPressed: () => Navigator.of(context).pop(),
                child: Image.asset(
                  'assets/buttons/yesiknow_button.png',
                  scale: 0.85,
                ),
              ),
            ],
          ),
        ),
      ],
    );
  }
}
