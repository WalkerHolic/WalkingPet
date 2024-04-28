import 'package:flutter/material.dart';
import 'package:walkingpet/common/bottom_nav_bar.dart';
import 'package:walkingpet/home/widgets/mainfontstyle.dart';

class Battle extends StatelessWidget {
  const Battle({super.key});

  @override
  Widget build(BuildContext context) {
    return Container(
      decoration: const BoxDecoration(
        image: DecorationImage(
          image:
              AssetImage("assets/backgrounds/battleReady.jpg"), // 이미지 파일 경로 지정
          fit: BoxFit.cover, // 배경 이미지가 전체 화면을 채우도록 설정
        ),
      ),
      child: Scaffold(
        backgroundColor: Colors.transparent, // Scaffold의 배경을 투명하게 설정
        body: Stack(
          children: [
            Container(
              margin: const EdgeInsets.fromLTRB(
                  20.0, 40.0, 20.0, 100.0), // 좌, 상, 우, 하 각각 다른 마진 적용

              padding: const EdgeInsets.all(16), // 모든 방향에 16.0의 패딩을 적용
              decoration: BoxDecoration(
                color: Colors.white.withOpacity(0.6), // 투명한 흰색 배경 추가
                borderRadius: BorderRadius.circular(10.0), // 모서리를 둥글게 처리
              ),
              child: const Center(
                child: Column(
                  children: [
                    MainFontStyle(size: 40, text: "오리빠따죠"),
                    Row(
                      crossAxisAlignment: CrossAxisAlignment.center,
                      children: [
                        MainFontStyle(size: 30, text: "점수:"),
                        MainFontStyle(size: 50, text: "1357")
                      ],
                    )
                  ],
                ),
              ),
            ),
            const Positioned(
              bottom: 0,
              left: 0,
              right: 0,
              child: BottomNavBar(
                selectedIndex: 3,
              ),
            ),
          ],
        ),
      ),
    );
  }
}