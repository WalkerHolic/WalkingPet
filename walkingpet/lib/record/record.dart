import 'package:flutter/material.dart';
import 'package:walkingpet/main.dart';

class Record extends StatelessWidget {
  const Record({super.key});

  @override
  Widget build(BuildContext context) {
    // 현재 화면의 크기 가져오기
    double screenWidth = MediaQuery.of(context).size.width;
    double screenHeight = MediaQuery.of(context).size.height;

    return Scaffold(
      resizeToAvoidBottomInset: false,
      body: Stack(
        children: [
          // 1. 배경 이미지
          Positioned.fill(
            child: Image.asset(
              'assets/backgrounds/map_road.png',
              fit: BoxFit.cover,
            ),
          ),

          // 2. 투명 레이어 (전체 영역)
          Positioned(
            child: Container(
              width: screenWidth,
              height: screenHeight,
              color: const Color.fromARGB(255, 255, 255, 255).withOpacity(0.4),
            ),
          ),

          // 2. 투명 레이어 (특정 영역만)
          // Positioned(
          //   left: screenWidth * 0.05,
          //   top: screenHeight * 0.15,
          //   child: Container(
          //     width: screenWidth * 0.9,
          //     height: screenHeight * 0.8,
          //     decoration: BoxDecoration(
          //       color:
          //           const Color.fromARGB(255, 255, 243, 212).withOpacity(0.75),
          //       borderRadius: BorderRadius.circular(10),
          //     ),
          //   ),
          // ),

          // 3. 내용
          // if (isLoading)
          //   const Center(
          //       child: Text(
          //     '캐릭터 정보 로딩중..',
          //     style: TextStyle(
          //       color: Colors.black,
          //     ),
          //   ))
          // else

          // 3-1. 기록 & 'X' 버튼
          Padding(
            padding: const EdgeInsets.only(top: 30),
            child: Row(
              mainAxisAlignment: MainAxisAlignment.spaceBetween,
              children: [
                const Text(' '),
                const Text(
                  '기록',
                  style: TextStyle(fontSize: 40),
                ),
                TextButton(
                  onPressed: () async {
                    bool isFirstVisit = await checkFirstVisitToday();
                    if (isFirstVisit) {
                      Navigator.pop(context);
                      return;
                    }
                    Navigator.pop(context);
                    // Navigator.pushReplacementNamed(
                    //     context, '/home'); // 현재 경로를 '/home'으로 교체
                  },
                  style: ButtonStyle(
                    padding: MaterialStateProperty.all(
                        EdgeInsets.zero), // 버튼의 내부 패딩 제거
                  ),
                  child: const Text(
                    'X',
                    style: TextStyle(fontSize: 30, color: Colors.black),
                  ),
                )
              ],
            ),
          ),

          // 3-2. 지도
          // RecordMap();
          // void main() {AuthRepository.initialize(appKey: '98dfecd4151782eef7342a07e95b9c57');}
          // KakaoMap(),
          
        ],
      ),
    );
  }
}
