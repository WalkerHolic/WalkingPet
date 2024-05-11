import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';
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

// 캐릭처 초기화
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
    double screenWidth = MediaQuery.of(context).size.width;
    double screenHeight = MediaQuery.of(context).size.height;

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
              Positioned(
                bottom: screenHeight * 0.2, // 화면 아래에서 20% 위치
                left: 0,
                right: 0,
                child: Center(
                  child: MainFontStyle(
                    size: screenWidth * 0.033,
                    text: "걸음수는 하루 첫 접속 기준으로 계산됩니다",
                    color: Colors.yellow,
                  ),
                ),
              ),
              Center(
                child: Column(
                  children: [
                    SizedBox(
                      height: screenHeight * 0.04,
                    ),
                    Row(
                      mainAxisAlignment: MainAxisAlignment.end,
                      children: [
                        Transform.translate(
                          offset: Offset(screenWidth * 0.04, 0),
                          child: const TopRightIconWithText(
                              icon: "ranking", text: "랭킹"),
                        ),
                        const TopRightIconWithText(icon: "goal", text: "목표"),
                        SizedBox(
                          width: screenWidth * 0.01,
                        ),
                      ],
                    ),
                    SizedBox(
                      height: screenHeight * 0.05,
                    ),
                    MainFontStyle(size: screenWidth * 0.13, text: "걸음수"),
                    Transform.translate(
                      offset: Offset(0, screenHeight * -0.03),
                      child: Consumer<StepCounter>(
                          builder: (context, provider, child) {
                        return MainFontStyle(
                          size: screenWidth * 0.3,
                          text: "${provider.steps}",
                        );
                      }),
                    ),
                    if (!isLoading)
                      SizedBox(
                        height: screenHeight * 0.22,
                        child: Image.asset(
                          //'assets/animals/wolf/wolf_run.gif',
                          'assets/animals/$animal/${animal}_walk.gif',
                          fit: BoxFit.fitHeight,
                        ),
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
