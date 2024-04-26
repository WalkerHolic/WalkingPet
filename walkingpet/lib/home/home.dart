import 'package:flutter/material.dart';
import 'package:walkingpet/common/bottom_nav_bar.dart';
import 'package:walkingpet/home/widgets/mainfontstyle.dart';
import 'package:walkingpet/home/widgets/toprighticonwithttext.dart';
import 'package:walkingpet/home/widgets/mainfontstyle.dart';

class Home extends StatelessWidget {
  const Home({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Container(
        decoration: const BoxDecoration(
          image: DecorationImage(
            image: AssetImage("assets/backgrounds/day.png"), // 이미지 파일 경로 지정
            fit: BoxFit.cover, // 배경 이미지가 전체 화면을 채우도록 설정
          ),
        ),
        child: Center(
          child: Column(
            children: [
              const SizedBox(
                height: 30,
              ),
              Row(
                mainAxisAlignment: MainAxisAlignment.end,
                children: [
                  Transform.translate(
                    offset: const Offset(10, 0),
                    child:
                        const TopRightIconWithText(icon: "ranking", text: "랭킹"),
                  ),
                  const TopRightIconWithText(icon: "goal", text: "목표"),
                ],
              ),
              const SizedBox(
                height: 40,
              ),
              const MainFontStyle(size: 48, text: "걸음수"),
              Transform.translate(
                offset: const Offset(0, -20),
                child: const MainFontStyle(size: 100, text: "16384"),
              ),
              Image.asset(
                'assets/animals/cow/cow_run.gif',
              ),
            ],
          ),
        ),
      ),
      bottomNavigationBar: const BottomNavBar(
        selectedIndex: 2,
      ),
    );
  }
}
