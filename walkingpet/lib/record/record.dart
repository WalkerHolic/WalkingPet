import 'dart:convert';

import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';
import 'package:flutter_dotenv/flutter_dotenv.dart';
import 'package:geolocator/geolocator.dart';
import 'package:kakaomap_webview/kakaomap_webview.dart';
import 'package:webview_flutter/webview_flutter.dart';
import 'package:walkingpet/main.dart';
import 'package:walkingpet/home/widgets/mainfontstyle.dart';

class Record extends StatefulWidget {
  const Record({super.key});

  @override
  State<Record> createState() => _RecordState();
}

class _RecordState extends State<Record> {
  late Future<Position> _currentPositionFuture;
  late WebViewController _mapController;
  final double _lat = 33.450701;
  final double _lng = 126.570667;

  @override
  Widget build(BuildContext context) {
    // 현재 화면의 크기 가져오기
    double screenWidth = MediaQuery.of(context).size.width;
    double screenHeight = MediaQuery.of(context).size.height;

    Size size = MediaQuery.of(context).size;

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
            padding: const EdgeInsets.only(top: 40),
            child: Row(
              mainAxisAlignment: MainAxisAlignment.spaceBetween,
              children: [
                const Text('       '),
                const MainFontStyle(size: 40, text: '기록'),
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

          // 앱 업로드를 위한 임시 코드
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

          // const Center(
          //   child: Text(
          //     '캐릭터와 함께 걸은\n나만의 장소를 기록하세요\n\n(준비중)',
          //     style: TextStyle(fontSize: 20, color: Colors.black),
          //     textAlign: TextAlign.center,
          //   ),
          // ),

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

              customScript: '''
                // 이벤트 마커 담을 변수
                var eventmarkers = [];

                // 마커 이미지 => 이벤트 팻말
                var imageSrc = 'https://ifh.cc/g/CqdgWa.png', // 마커이미지의 주소입니다
                    imageSize = new kakao.maps.Size(64, 69), // 마커이미지의 크기입니다
                    imageOption = {offset: new kakao.maps.Point(27, 69)}; // 마커이미지의 옵션입니다. 마커의 좌표와 일치시킬 이미지 안에서의 좌표를 설정합니다.

                var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize, imageOption),
                    markerPosition = new kakao.maps.LatLng(37.54699, 127.09598); // 마커가 표시될 위치입니다

                // 마커 추가할 함수
                function addMarker(position) {
                  var marker = new kakao.maps.Marker({position: position, image: markerImage});
                  marker.setMap(map);
                  eventmarkers.push(marker);
                }

                // 마커 표시하기 (반복문 활용)
                for(var i = 0 ; i < 3 ; i++){
                  addMarker(new kakao.maps.LatLng(36.355387454337716 + 0.0003 * i, 127.29839622974396 + 0.0003 * i));
                  kakao.maps.event.addListener(eventmarkers[i], 'click', (function(i) {
                    return function(){
                      onTapMarker.postMessage('marker ' + i + ' is tapped');
                    };
                  })(i));
                }
              ''',

              onTapMarker: (message) {
                ScaffoldMessenger.of(context)
                    .showSnackBar(SnackBar(content: Text(message.message)));
              },
            ),
          ),

          // 참고하려고 써둔 코드
          // Positioned(
          //   left: screenWidth * 0.05,
          //   top: screenHeight * 0.15,
          //   child: KakaoMapView(
          //     width: screenWidth * 0.9,
          //     height: screenHeight * 0.7,
          //     kakaoMapKey: kakaoMapKey!,
          //     lat: 36.355387454337716, // 위도
          //     lng: 127.29839622974396, // 경도
          //     // lat: _lat,
          //     // lng: _lng,
          //     zoomLevel: 3, // 초기 줌 레벨

          //     // 오버레이: '구경하기',
          //     // customOverlayStyle: '''<style>
          //     //   .customoverlay {position:relative;bottom:85px;border-radius:6px;border: 1px solid #ccc;border-bottom:2px solid #ddd;float:left;}
          //     //   .customoverlay:nth-of-type(n) {border:0; box-shadow:0px 1px 2px #888;}
          //     //   .customoverlay a {display:block;text-decoration:none;color:#000;text-align:center;border-radius:6px;font-size:14px;font-weight:bold;overflow:hidden;background: #d95050;background: #d95050 url(https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/arrow_white.png) no-repeat right 14px center;}
          //     //   .customoverlay .title {display:block;text-align:center;background:#fff;margin-right:35px;padding:10px 15px;font-size:14px;font-weight:bold;}
          //     //   .customoverlay:after {content:'';position:absolute;margin-left:-12px;left:50%;bottom:-12px;width:22px;height:12px;background:url('https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/vertex_white.png')}
          //     //                 </style>''',
          //     // customOverlay: '''
          //     //   const content = '<div class="customoverlay">' +
          //     //       '  <a href="https://map.kakao.com/link/map/11394059" target="_blank">' +
          //     //       '    <span class="title">userId</span>' +
          //     //       '  </a>' +
          //     //       '</div>';

          //     //   const position = new kakao.maps.LatLng($_lat, $_lng);

          //     //   const customOverlay = new kakao.maps.CustomOverlay({
          //     //       map: map,
          //     //       position: position,
          //     //       content: content,
          //     //       yAnchor: 1
          //     //   });
          //     //   ''',

          //     markerImageURL: 'https://ifh.cc/g/6FTDpn.png',

          //     onTapMarker: (message) {
          //       ScaffoldMessenger.of(context)
          //           .showSnackBar(SnackBar(content: Text(message.message)));
          //     },
          //   ),
          // ),

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
