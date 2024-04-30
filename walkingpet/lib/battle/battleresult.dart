import 'package:flutter/material.dart';
import 'package:walkingpet/battle/resultfontstyle.dart';
import 'package:walkingpet/home/widgets/mainfontstyle.dart';

class BattleResult extends StatelessWidget {
  const BattleResult({super.key});

  @override
  Widget build(BuildContext context) {
    return Container(
      decoration: const BoxDecoration(
        image: DecorationImage(
          image: AssetImage("assets/backgrounds/battleResult.png"),
          fit: BoxFit.cover, // 배경 이미지가 전체 화면을 채우도록 설정
        ),
      ),
      child: Scaffold(
        backgroundColor: Colors.transparent,
        body: Center(
          child: Column(
            children: [
              const SizedBox(
                height: 200,
              ),
              const ResultFontStyle(size: 40, text: "WIN", color: Colors.blue),
              const SizedBox(
                height: 100,
              ),
              Image.asset(
                'assets/animals/cow/cow_idle.gif',
                scale: 1.2,
              ),
              Transform.translate(
                  offset: const Offset(80, -140),
                  child: const ResultFontStyle(
                      size: 20, text: "경험치 +5", color: Colors.green)),
              const Row(
                mainAxisAlignment: MainAxisAlignment.center,
                children: [
                  MainFontStyle(size: 20, text: "점수:"),
                  MainFontStyle(size: 40, text: " 1378 "),
                  ResultFontStyle(size: 40, text: "(+10)", color: Colors.blue)
                ],
              ),
              const SizedBox(
                height: 30,
              ),
              InkWell(
                onTap: () {
                  Navigator.pushNamed(context, '/battleready');
                },
                child: Image.asset(
                  'assets/buttons/yesiknow_button.png',
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }
}
