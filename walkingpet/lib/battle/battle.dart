import 'package:flutter/material.dart';
import 'package:walkingpet/battle/battleside.dart';
import 'package:walkingpet/home/widgets/mainfontstyle.dart';
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
                Row(
                  mainAxisAlignment: MainAxisAlignment.spaceAround,
                  children: [
                    BattleSide(
                      health: widget.myCharacterData['health'],
                      power: widget.myCharacterData['power'],
                      defense: widget.myCharacterData['defense'],
                      rating: widget.myCharacterData['rating'],
                      characterId: 8,
                      //characterId: widget.myCharacterData['characterId'],
                      userCharacterLevel: widget.myCharacterData['level'],
                      nickname: widget.myCharacterData['nickname'],
                      isLeft: true, // 입은 데미지
                      attackDamage: battleData['battleProgressInfo']
                          ['userAttackDamage'],
                      receivedDamage: battleData['battleProgressInfo']
                          ['enemyAttackDamage'],

                      userHealth: battleData['battleProgressInfo']
                          ['userHealth'],
                      loseDamage: battleData['battleProgressInfo']
                          ['userLoseDamage'],
                    ),
                    Transform.translate(
                      offset: const Offset(0, 280),
                      child: const MainFontStyle(size: 16, text: "vs"),
                    ),
                    BattleSide(
                      health: battleData['enemyInfo']['health'],
                      power: battleData['enemyInfo']['power'],
                      defense: battleData['enemyInfo']['defense'],
                      rating: battleData['enemyInfo']['rating'],
                      characterId: battleData['enemyInfo']['characterId'],
                      userCharacterLevel: battleData['enemyInfo']['level'],
                      nickname: battleData['enemyInfo']['nickname'],
                      isLeft: false,
                      attackDamage: battleData['battleProgressInfo']
                          ['enemyAttackDamage'],
                      receivedDamage: battleData['battleProgressInfo']
                          ['userAttackDamage'],
                      userHealth: battleData['battleProgressInfo']
                          ['userHealth'],
                      loseDamage: battleData['battleProgressInfo']
                          ['enemyLoseDamage'],
                    ),
                  ],
                ),
            ],
          )),
    );
  }
}
