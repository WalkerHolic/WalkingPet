import 'package:flutter/material.dart';
import 'package:walkingpet/battle/battleside.dart';
import 'package:walkingpet/services/battle/getbattleinfo.dart';

class Battle extends StatefulWidget {
  final Map<String, dynamic> myCharacterData; // widget.characterData로 사용
  const Battle({super.key, required this.myCharacterData});

  @override
  State<Battle> createState() => _BattleState();
}

class _BattleState extends State<Battle> {
  Map<String, dynamic> battleData = {};
  bool isLoading = true;

  @override
  void initState() {
    super.initState();
    initBattleInfo(); // 위젯이 로드될 때 fetchData 호출
  }

  Future<void> initBattleInfo() async {
    print(widget.myCharacterData);
    try {
      var response = await getBattleInfo();
      setState(() {
        battleData = response['data']; // API 응답을 상태에 저장
        isLoading = false;
      });
    } catch (e) {
      isLoading = false;
    }
  }

  @override
  Widget build(BuildContext context) {
    return Container(
      decoration: const BoxDecoration(
        image: DecorationImage(
          image: AssetImage("assets/backgrounds/battle.png"),
          //fit: BoxFit.cover, // 배경 이미지가 전체 화면을 채우도록 설정
        ),
      ),
      child: Scaffold(
          backgroundColor: Colors.transparent, // Scaffold의 배경을 투명하게 설정
          body: Column(
            children: [
              if (!isLoading)
                const Row(
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
                      isLeft: true,
                      damageRatioList: [
                        0,
                        0.1,
                        0,
                        0.3,
                        0,
                        0.4,
                        0,
                        0.3
                      ], // 입은 데미지
                      attckDamage: [3, -1, 5, -1, 8, -1, 2, -1],
                    ),
                    BattleSide(
                      health: 100,
                      power: 5,
                      defense: 3,
                      rating: 1349,
                      characterId: 1,
                      userCharacterLevel: 15,
                      nickname: "지은이",
                      isLeft: false,
                      damageRatioList: [0.3, 0, 0.2, 0, 0.3, 0, 0.1, 0],
                      attckDamage: [-1, 8, -1, 2, -1, 6, -1, 7],
                    ),
                  ],
                ),
            ],
          )),
    );
  }
}
