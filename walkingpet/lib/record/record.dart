import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';
import 'package:flutter_dotenv/flutter_dotenv.dart';
import 'package:kakaomap_webview/kakaomap_webview.dart';
import 'package:walkingpet/main.dart';

class Record extends StatelessWidget {
  const Record({super.key});

  @override
  Widget build(BuildContext context) {
    // 현재 화면의 크기 가져오기
    double screenWidth = MediaQuery.of(context).size.width;
    double screenHeight = MediaQuery.of(context).size.height;

    final String? kakaoMapKey = dotenv.env['MAP_APP_KEY'];

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

          // 3-2. 지도 KakaoMapView
          Positioned(
            left: screenWidth * 0.05,
            top: screenHeight * 0.15,
            child: KakaoMapView(
              width: screenWidth * 0.9,
              height: screenHeight * 0.7,
              kakaoMapKey: kakaoMapKey!,
              lat: 36.355387454337716, // 위도
              lng: 127.29839622974396, // 경도
              zoomLevel: 3, // 초기 줌 레벨
              markerImageURL: 'https://ifh.cc/g/6FTDpn.png',
              // customScript: '''
              //   var markers = [];

              //   function addMarker(position) {
              //     var marker = new kakao.maps.Marker({position: position});
              //     marker.setMap(map);
              //     markers.push(marker);
              //   }

              //   for(var i = 0 ; i < 3 ; i++){
              //     addMarker(new kakao.maps.LatLng(36.355387454337716 + 0.0003 * i, 127.29839622974396 + 0.0003 * i));
              //     kakao.maps.event.addListener(markers[i], 'click', (function(i) {
              //       return function(){
              //         onTapMarker.postMessage('marker ' + i + ' is tapped');
              //       };
              //     })(i));
              //   }

              //   var zoomControl = new kakao.maps.ZoomControl();
              //   map.addControl(zoomControl, kakao.maps.ControlPosition.RIGHT);

              //   var mapTypeControl = new kakao.maps.MapTypeControl();
              //   map.addControl(mapTypeControl, kakao.maps.ControlPosition.TOPRIGHT);
              // ''',

              onTapMarker: (message) {
                ScaffoldMessenger.of(context)
                    .showSnackBar(SnackBar(content: Text(message.message)));
              },
            ),
          ),

          // 3-3. 내 기록 / 기록 생성
          Positioned(
            left: 0,
            right: 0,
            bottom: screenHeight * 0.1,
            child: const Row(
                mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                children: [
                  Text('내 기록'),
                  Text('기록하기'),
                ]),
          ),
        ],
      ),
    );
  }
}
