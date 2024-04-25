import 'package:flutter/material.dart';
import 'package:pixelarticons/pixelarticons.dart';

class Login extends StatelessWidget {
  const Login({super.key});

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
              Row(
                children: [
                  Column(
                    children: [Icon(Pixel.calendarcheck), Text("목표")],
                  ),
                  Column(
                    children: [Icon(Pixel.trophy), Text("랭킹")],
                  )
                ],
              )
            ],
          ),
        ),
      ),
    );
  }
}
