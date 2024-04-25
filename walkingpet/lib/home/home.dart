import 'package:flutter/material.dart';
import 'package:walkingpet/home/widgets/toprighticonwithttext.dart';

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
        child: const Center(
          child: Column(
            children: [
              SizedBox(
                height: 40,
              ),
              Row(
                mainAxisAlignment: MainAxisAlignment.end,
                children: [
                  TopRightIconWithText(icon: "ranking", text: "랭킹"),
                  TopRightIconWithText(icon: "goal", text: "목표"),
                ],
              )
            ],
          ),
        ),
      ),
    );
  }
}
