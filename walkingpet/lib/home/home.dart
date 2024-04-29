import 'package:flutter/material.dart';
import 'package:walkingpet/common/bottom_nav_bar.dart';
import 'package:walkingpet/common/step_counter.dart';
import 'package:walkingpet/home/widgets/mainfontstyle.dart';
import 'package:walkingpet/home/widgets/toprighticonwithttext.dart';
import 'package:provider/provider.dart';
import 'package:walkingpet/services/character/checkstep.dart';

class Home extends StatefulWidget {
  const Home({super.key});

  @override
  State<Home> createState() => _HomeState();
}

class _HomeState extends State<Home> {
  @override
  void initState() {
    super.initState();
    initStepData(); // 위젯이 로드될 때 fetchData 호출
  }

  Future<void> initStepData() async {
    try {
      var jsonData = await checkStep();
      print(jsonData);
    } catch (e) {
      print('Failed to load data: $e');
    }
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
                    Image.asset(
                      'assets/animals/cow/cow_run.gif',
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
