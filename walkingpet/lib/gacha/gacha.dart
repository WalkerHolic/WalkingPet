import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/rendering.dart';
import 'package:flutter/widgets.dart';
import 'package:flutter_svg/flutter_svg.dart';
import 'package:walkingpet/common/bottom_nav_bar.dart';
import 'package:walkingpet/gacha/widgets/gacha_box.dart';


class Gacha extends StatelessWidget {
  const Gacha({super.key});

  @override
  Widget build(BuildContext context) {
    double screenWidth = MediaQuery.of(context).size.width;
    double screenHeight = MediaQuery.of(context).size.height;
    //새로 나온 캐릭터 홍보
    const String itemName = "레드 드래곤";
    const int rating = 3; //별의 개수
    return Scaffold(
      // appBar: AppBar(
      //   title: const Text('뽑기 Page'),
      // ),
      body: Stack(
        children: <Widget>[
          //1. 배경이미지
          Positioned.fill(
            child: Image.asset('assets/backgrounds/morning.png',
                fit: BoxFit.cover),
          ),
          Center(
            child: Container(
              width: screenWidth * 0.9,
              height: screenHeight * 0.7,
              //모서리 둥글게
              decoration: BoxDecoration(
                  color: const Color(0xfffff3dc).withOpacity(0.9),
                  borderRadius: BorderRadius.circular(20)),
              child: Padding(
                padding: const EdgeInsets.symmetric(vertical: 30),
                child: Column(
                  //컨테이너 안에 컬럼 넣기
                  mainAxisAlignment: MainAxisAlignment.start, //컨테이너 상단부터 시작
                  children: <Widget>[
                    Row(
                      mainAxisAlignment: MainAxisAlignment.center,
                      children: [
                        ...List.generate(
                          rating,
                          (_) => const Icon(
                            Icons.star,
                            color: Color(0xffff004c),
                            size: 30,
                          ),
                        ),
                        const SizedBox(width: 10), //텍스트와 별 사이 여백
                        const Text(
                          itemName,
                          style: TextStyle(
                            fontSize: 25,
                          ),
                        ),
                      ],
                    ),
                    const Text(
                      "출시!",
                      style: TextStyle(
                        fontSize: 25,
                      ),
                    ),
                    const SizedBox(
                      //츌시 <-> 스테이지 사이 공간
                      height: 140,
                    ),
                    Stack(
                      alignment: Alignment.topCenter,
                      children: <Widget>[
                        Image.asset('assets/items/gacha_stage.png'),
                      ],
                    ),
                    const Spacer(),
                    const Row(
                      mainAxisAlignment: MainAxisAlignment.center,
                      children: [
                        GachaBox(
                          boxName: '일반 상자',
                          boxImage: 'assets/items/itembox_normal.png',
                          buttonImage: 'assets/buttons/button_gacha_normal.svg',
                        ),
                        SizedBox(width: 40), //일반상자 ~ 고급상자 사이 공간
                        GachaBox(
                          boxName: '고급 상자',
                          boxImage: 'assets/items/itembox_special.png',
                          buttonImage:
                              'assets/buttons/button_gacha_special.svg',
                        ),
                      ],
                    ),
                  ],
                ),
              ),
            ),
          ),
          Positioned(
            top: screenWidth / 8, //화면 높이 기준 위치
            child: Image.asset(
                'assets/animals/dragons/red/red_dragon_ready.gif',
                width: 400,
                fit: BoxFit.cover),
          ),
        ],
      ),
      bottomNavigationBar: const BottomNavBar(
        selectedIndex: 1,
      ),
    );
  }
}
