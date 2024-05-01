import 'package:flutter/material.dart';
import 'package:walkingpet/common/bottom_nav_bar.dart';
import 'package:walkingpet/common/character_map.dart';
import 'package:walkingpet/common/star.dart';
import 'package:walkingpet/home/widgets/mainfontstyle.dart';
import 'package:walkingpet/services/battle/getmyinfo.dart';

class BattleReady extends StatefulWidget {
  const BattleReady({super.key});

  @override
  State<BattleReady> createState() => _BattleReadyState();
}

class _BattleReadyState extends State<BattleReady> {
  Map<String, dynamic> characterData = {};
  String animal = "";
  bool isLoading = true;

  @override
  void initState() {
    super.initState();
    initMyInfo(); // 위젯이 로드될 때 fetchData 호출
  }

  Future<void> initMyInfo() async {
    try {
      var response = await getMyInfo();
      setState(() {
        characterData = response['data']; // API 응답을 상태에 저장
        int characterId = characterData['characterId']
            as int; // API에서 characterId가 int 타입이라고 가정
        animal = CharacterMap.idToAnimal[characterId] ??
            "Unknown"; // characterId에 해당하는 동물이 없을 경우 "Unknown"을 사용
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
          image:
              AssetImage("assets/backgrounds/battleReady.jpg"), // 이미지 파일 경로 지정
          fit: BoxFit.cover, // 배경 이미지가 전체 화면을 채우도록 설정
        ),
      ),
      child: Scaffold(
        backgroundColor: Colors.transparent, // Scaffold의 배경을 투명하게 설정
        body: Stack(
          children: [
            if (!isLoading)
              Container(
                margin: const EdgeInsets.fromLTRB(
                    20.0, 40.0, 20.0, 100.0), // 좌, 상, 우, 하 각각 다른 마진 적용

                padding: const EdgeInsets.all(16), // 모든 방향에 16.0의 패딩을 적용
                decoration: BoxDecoration(
                  color: Colors.white.withOpacity(0.6), // 투명한 흰색 배경 추가
                  borderRadius: BorderRadius.circular(10.0), // 모서리를 둥글게 처리
                ),
                child: Center(
                  child: Column(
                    children: [
                      MainFontStyle(
                          size: 40,
                          text: characterData['nickname']), // 동적 데이터 사용
                      MainFontStyle(
                          size: 30, text: "점수: ${characterData['rating']}"),
                      const SizedBox(
                        height: 100,
                      ),
                      Transform.translate(
                        offset: const Offset(0, 60),
                        child: Star(
                          count: characterData['grade'],
                        ),
                      ),
                      Image.asset(
                        'assets/animals/$animal/${animal}_walk.gif',
                      ),
                      Transform.translate(
                        offset: const Offset(0, -20),
                        child: MainFontStyle(
                            size: 30, text: "Lv.${characterData['level']}"),
                      ),
                      Transform.translate(
                        offset: const Offset(0, -15),
                        child: Image.asset(
                          'assets/buttons/character_change_button.png',
                        ),
                      ),
                      const SizedBox(
                        height: 5,
                      ),
                      InkWell(
                          onTap: () {
                            Navigator.pushNamed(context, '/battle');
                          },
                          child: Stack(
                            children: [
                              Image.asset(
                                  'assets/buttons/battle_start_button.png'),
                              Center(
                                child: Column(
                                  mainAxisAlignment: MainAxisAlignment.center,
                                  children: [
                                    const SizedBox(
                                      height: 10,
                                    ),
                                    const Text(
                                      "Battle Start!",
                                      style: TextStyle(fontSize: 20),
                                    ),
                                    Text(
                                      "일일 남은 횟수: ${characterData['battleCount']}/10",
                                      style: const TextStyle(fontSize: 12),
                                    ),
                                  ],
                                ),
                              ),
                            ],
                          )),
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
