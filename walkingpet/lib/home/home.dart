import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'package:flutter_svg/flutter_svg.dart';
import 'package:kakao_flutter_sdk/kakao_flutter_sdk.dart';
import 'package:nes_ui/nes_ui.dart';
import 'package:walkingpet/common/bottom_nav_bar.dart';
import 'package:walkingpet/common/character_map.dart';
import 'package:walkingpet/main.dart';
import 'package:walkingpet/providers/step_counter.dart';
import 'package:walkingpet/home/widgets/mainfontstyle.dart';
import 'package:walkingpet/home/widgets/toprighticonwithttext.dart';
import 'package:provider/provider.dart';
import 'package:walkingpet/services/home/gethomecharacter.dart';

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
      var responseInfo = await getHomeCharacter();
      var characterInfoData = responseInfo['data'];
      int characterId = characterInfoData['characterId'] as int;
      setState(() {
        animal = CharacterMap.idToAnimal[characterId] ?? "bunny";
        isLoading = false;
      });
    } catch (e) {}
  }

  // 로그아웃
  Future<void> deleteTokens() async {
    const storage = FlutterSecureStorage();
    await storage.delete(key: 'ACCESS_TOKEN');
    await storage.delete(key: 'REFRESH_TOKEN');
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
          resizeToAvoidBottomInset: false,
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
                      mainAxisAlignment: MainAxisAlignment.spaceBetween,
                      crossAxisAlignment: CrossAxisAlignment.start,
                      children: [
                        // 로그아웃 아이콘
                        Transform.translate(
                          offset: Offset(screenWidth * 0.01, 0),
                          child: IconButton(
                            onPressed: () async {
                              // 로그아웃 여부 확인 모달
                              bool? confirmLogout = await showDialog(
                                context: context,
                                builder: (context) {
                                  // return const LogoutDialog(
                                  //   confirmLabel: '확인',
                                  //   cancelLabel: '취소',
                                  //   message: '로그아웃 하시겠습니까?',
                                  // );
                                  return AlertDialog(
                                    title: const Text('로그아웃'),
                                    content: const Text('로그아웃 하시겠습니까?'),
                                    actions: <Widget>[
                                      TextButton(
                                        onPressed: () {
                                          Navigator.of(context)
                                              .pop(false); // 취소
                                        },
                                        child: const Text('취소'),
                                      ),
                                      TextButton(
                                        onPressed: () {
                                          Navigator.of(context).pop(true); // 확인
                                        },
                                        child: const Text('확인'),
                                      ),
                                    ],
                                  );
                                },
                              );

                              // 사용자가 로그아웃을 확인했을 때만 로그아웃을 진행
                              if (confirmLogout == true) {
                                // 로그아웃 함수
                                try {
                                  await UserApi.instance.logout();
                                  await deleteTokens(); // 로그아웃 시 토큰 삭제
                                  Navigator.pushNamed(
                                      context, '/login'); // 로그인 페이지로 이동
                                  print('로그아웃 성공, SDK에서 토큰 삭제');
                                } catch (error) {
                                  AccessTokenInfo tokenInfo =
                                      await UserApi.instance.accessTokenInfo();
                                  print(
                                      '토큰 유효성 체크 성공 ${tokenInfo.id} ${tokenInfo.expiresIn}');
                                  print('로그아웃 실패, SDK에서 토큰 삭제 $error');
                                }
                              }
                            },
                            icon: Image.asset(
                              'assets/icons/logout.png',
                              // width: svgWidth,
                              height: screenHeight * 0.07,
                            ),
                          ),
                        ),

                        Row(
                          mainAxisAlignment: MainAxisAlignment.end,
                          children: [
                            // Transform.translate(
                            //   offset: Offset(screenWidth * 0.10, 0),
                            //   child: const TopRightIconWithText(
                            //       icon: "record", text: "기록"),
                            // ),
                            Transform.translate(
                              offset: Offset(screenWidth * 0.05, 0),
                              child: const TopRightIconWithText(
                                  icon: "ranking", text: "랭킹"),
                            ),
                            const TopRightIconWithText(
                                icon: "goal", text: "목표"),
                            SizedBox(
                              width: screenWidth * 0.01,
                            ),
                          ],
                        ),
                      ],
                    ),
                    SizedBox(
                      height: screenHeight * 0.05,
                    ),
                    MainFontStyle(size: screenWidth * 0.1, text: "걸음수"),
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
                    SizedBox(
                      height: screenHeight * 0.06,
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
