import 'package:flutter/material.dart';
import 'package:walkingpet/battle/resultfontstyle.dart';
import 'package:walkingpet/home/widgets/mainfontstyle.dart';

class BattleResult extends StatelessWidget {
  final Map<String, dynamic> battleResult;
  final Map<String, dynamic> levelUpResponse;
  final String animal;

  const BattleResult({
    super.key,
    required this.battleResult,
    required this.levelUpResponse,
    required this.animal,
  });

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
              ResultFontStyle(
                size: 40,
                text: battleResult['battleResult'] ? "WIN" : "LOSE",
                color: battleResult['battleResult'] ? Colors.blue : Colors.red,
              ),
              const SizedBox(
                height: 100,
              ),
              Image.asset(
                battleResult['battleResult']
                    ? 'assets/animals/$animal/${animal}_attack.gif'
                    : 'assets/animals/$animal/${animal}_hurt.gif',
                scale: 1.2,
              ),
              Transform.translate(
                  offset: const Offset(80, -140),
                  child: ResultFontStyle(
                      size: 20,
                      text: "경험치 +${battleResult['rewardExperience']}",
                      color: Colors.green)),
              Row(
                mainAxisAlignment: MainAxisAlignment.center,
                children: [
                  const MainFontStyle(size: 20, text: "점수:"),
                  MainFontStyle(
                      size: 40, text: " ${battleResult['resultRating']} "),
                  ResultFontStyle(
                      size: 40,
                      text:
                          '(${battleResult['rewardRating'] > 0 ? "+" : ""}${battleResult['rewardRating']})',
                      color: battleResult['rewardRating'] > 0
                          ? Colors.blue
                          : Colors.red),
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
