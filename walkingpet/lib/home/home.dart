import 'package:flutter/material.dart';
import 'package:walkingpet/common/bottom_nav_bar.dart';
import 'package:walkingpet/common/character_map.dart';
import 'package:walkingpet/providers/step_counter.dart';
import 'package:walkingpet/home/widgets/mainfontstyle.dart';
import 'package:walkingpet/home/widgets/toprighticonwithttext.dart';
import 'package:provider/provider.dart';
import 'package:walkingpet/services/character/characterinfo.dart';

class Home extends StatefulWidget {
  const Home({super.key});

  @override
  State<Home> createState() => _HomeState();
}

class _HomeState extends State<Home> {
  @override
  void initState() {
    super.initState();
    initInfo();
  }

  String animal = "cow";
  bool isLoading = true;

// API 요청으로 데이터 불러오기
  Future<void> initInfo() async {
    try {
      var responseInfo = await getCharacterInfo();
      var characterInfoData = responseInfo['data'];
      int characterId = characterInfoData['characterId'] as int;

      setState(() {
        animal = CharacterMap.idToAnimal[characterId] ?? "bunny";
        isLoading = false;
      });
    } catch (e) {}
  }

  @override
  Widget build(BuildContext context) {
    return Container(
      decoration: const BoxDecoration(
        image: DecorationImage(
          image: AssetImage("assets/backgrounds/day.png"), // 이미지 파일 경로 지정
          fit: BoxFit.cover, // 배경 이미지가 전체 화면을 채우도록 설정
        ),
      ),
      child: ChangeNotifierProvider(
        create: (context) => StepCounter(),
        child: Scaffold(
          backgroundColor: Colors.transparent, // Scaffold 배경을 투명하게 설정
          body: Stack(
            children: [
              Center(
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
                          child: const TopRightIconWithText(
                              icon: "ranking", text: "랭킹"),
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
                      child: Consumer<StepCounter>(
                          builder: (context, provider, child) {
                        return MainFontStyle(
                            size: 100, text: "${provider.steps}");
                      }),
                    ),
                    if (!isLoading)
                      Image.asset(
                        //'assets/animals/wolf/wolf_run.gif',
                        'assets/animals/$animal/${animal}_walk.gif',
                      ),
                    const MainFontStyle(
                      size: 12,
                      text: "걸음수는 하루 첫 접속 기준으로 계산됩니다",
                      color: Colors.yellow,
                    ),
                  ],
                ),
              ),
              const Positioned(
                bottom: 0,
                left: 0,
                right: 0,
                child: BottomNavBar(
                  selectedIndex: 2,
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }
}
