import 'package:flutter/material.dart';

class Goal extends StatelessWidget {
  const Goal({super.key});

  @override
  Widget build(BuildContext context) {
    // 현재 화면의 크기를 가져오기
    double screenWidth = MediaQuery.of(context).size.width;
    double screenHeight = MediaQuery.of(context).size.height;
    // 걸음수 더미값
    double Step = 7500;
    double goalStep = 10000;
    return Scaffold(
      appBar: AppBar(
        title: const Text('목표'),
      ),
      body: Stack(
        children: <Widget>[
          //1. 배경 이미지
          Positioned.fill(
            child: Image.asset(
              'assets/backgrounds/goal.png',
              fit: BoxFit.cover,
            ),
          ),
          Center(
            child: Container(
              width: screenWidth * 0.9,
              color: const Color(0xfffff3dc).withOpacity(0.8),
            ),
          ),
          Center(
            child: SizedBox(
              width: screenWidth * 0.6,
              height: screenWidth * 0.6,
              child: const FittedBox(
                child: Stack(
                  alignment: Alignment.center,
                  children: <Widget>[
                    CircularProgressIndicator(
                      backgroundColor: Color(0xffeeeeee),
                      color: Color(0xff47E1A9),
                      value: 0.1,
                      strokeWidth: 3,
                    ),
                    Text("야호"),
                  ],
                ),
              ),
            ),
          )
        ],
      ),
    );
  }
}
