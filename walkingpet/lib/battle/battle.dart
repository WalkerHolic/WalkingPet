import 'package:flutter/material.dart';
import 'package:walkingpet/battle/battleside.dart';

class Battle extends StatelessWidget {
  const Battle({super.key});

  @override
  Widget build(BuildContext context) {
    return Container(
      decoration: const BoxDecoration(
        image: DecorationImage(
          image: AssetImage("assets/backgrounds/battle.png"),
          //fit: BoxFit.cover, // 배경 이미지가 전체 화면을 채우도록 설정
        ),
      ),
      child: const Scaffold(
          backgroundColor: Colors.transparent, // Scaffold의 배경을 투명하게 설정
          body: Column(
            children: [
              Row(
                mainAxisAlignment: MainAxisAlignment.spaceAround,
                children: [
                  BattleSide(
                      health: 100,
                      power: 5,
                      defense: 3,
                      rating: 1357,
                      characterId: 1,
                      userCharacterLevel: 15,
                      nickname: "현승이",
                      isLeft: true),
                  BattleSide(
                      health: 100,
                      power: 5,
                      defense: 3,
                      rating: 1349,
                      characterId: 1,
                      userCharacterLevel: 15,
                      nickname: "지은이",
                      isLeft: false),
                ],
              ),
            ],
          )),
    );
  }
}
