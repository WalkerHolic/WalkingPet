import 'dart:convert';

import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';
import 'package:flutter_dotenv/flutter_dotenv.dart';
import 'package:geolocator/geolocator.dart';
import 'package:kakaomap_webview/kakaomap_webview.dart';
import 'package:walkingpet/services/record/eventmarkers.dart';
import 'package:walkingpet/services/record/usermarkers.dart';
import 'package:webview_flutter/webview_flutter.dart';
import 'package:walkingpet/home/widgets/mainfontstyle.dart';

class Record extends StatefulWidget {
  const Record({super.key});

  @override
  State<Record> createState() => _RecordState();
}

class _RecordState extends State<Record> {
  late Future<Position> _currentPositionFuture;
  late WebViewController _mapController;
  // final double _lat = 33.450701;
  // final double _lng = 126.570667;

  final lat = 36.355387454337716;
  final lng = 127.29839622974396;

  // 이벤트 마커 담을 변수
  List<dynamic> eventmarkers = [];
  // 사용자의 마커 담을 변수
  List<dynamic> usermarkers = [];

  bool isLoading = true;

  @override
  void initState() {
    super.initState();
    initInfo();
  }

  // API 요청으로 데이터 불러오기
  Future<void> initInfo() async {
    try {
      var responseEvents = await getEventMarkers();
      var responseUsers = await getUserMarkers();
      setState(() {
        eventmarkers = responseEvents['data']['eventRecordList'];
        usermarkers = responseUsers['data']['normalRecordList'];
        isLoading = false;
      });
    } catch (e) {
      isLoading = false;
    }
  }

  // 현재 위치 가져오기

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
          if (isLoading)
            const Center(
                child: Text(
              '기록 떠올리는 중..',
              style: TextStyle(
                color: Colors.black,
              ),
            ))
          else
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
                var eventMarkers = ${jsonEncode(eventmarkers)};

                // 마커 이미지 => 이벤트 팻말
                // var eventImageSrc = 'https://ifh.cc/g/po7J27.png', // 마커이미지의 주소입니다
                var eventImageSrc = 'https://ifh.cc/g/0kW3Od.png', // 마커이미지의 주소입니다
                    eventImageSize = new kakao.maps.Size(35, 35), // 마커이미지의 크기입니다
                    eventImageOption = {offset: new kakao.maps.Point(0, 0)}; // 마커이미지의 옵션입니다. 마커의 좌표와 일치시킬 이미지 안에서의 좌표를 설정합니다.

                var eventMarkerImage = new kakao.maps.MarkerImage(eventImageSrc, eventImageSize, eventImageOption);
                
                // 마커 추가할 함수
                function addEventMarker(position) {
                  var marker = new kakao.maps.Marker({position: position, image: eventMarkerImage});
                  marker.setMap(map);
                  return marker;
                }
                
                // 마커 표시하기 (반복문 활용)
                for(var i = 0 ; i < eventMarkers.length ; i++){
                  var marker = addEventMarker(new kakao.maps.LatLng(eventMarkers[i].latitude, eventMarkers[i].longitude));
                  kakao.maps.event.addListener(marker, 'click', (function(i) {
                    return function(){
                      onTapMarker.postMessage('marker ' + eventMarkers[i].title + ' is tapped');
                    };
                  })(i));
                }
                
                // 사용자 마커 담을 변수
                var userMarkers = ${jsonEncode(usermarkers)};

                // 마커 이미지 => 사용자 팻말
                var userImageSrc = 'https://ifh.cc/g/CqdgWa.png', // 마커이미지의 주소입니다
                    userImageSize = new kakao.maps.Size(35, 35), // 마커이미지의 크기입니다
                    userImageOption = {offset: new kakao.maps.Point(0, 0)}; // 마커이미지의 옵션입니다. 마커의 좌표와 일치시킬 이미지 안에서의 좌표를 설정합니다.

                var userMarkerImage = new kakao.maps.MarkerImage(userImageSrc, userImageSize, userImageOption);
                
                // 사용자 마커 추가할 함수
                function addUserMarker(position) {
                  var marker = new kakao.maps.Marker({position: position, image: userMarkerImage});
                  marker.setMap(map);
                  return marker;
                }
                
                // 사용자 마커 표시하기 (반복문 활용)
                for(var i = 0 ; i < userMarkers.length ; i++){
                  var marker = addUserMarker(new kakao.maps.LatLng(userMarkers[i].latitude, userMarkers[i].longitude));
                  kakao.maps.event.addListener(marker, 'click', (function(i) {
                    return function(){
                      onTapMarker.postMessage('marker ' + userMarkers[i].title + ' is tapped');
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
            left: screenWidth * 0.1,
            right: screenWidth * 0.1,
            bottom: screenHeight * 0.05,
            child: Row(
              mainAxisAlignment: MainAxisAlignment.spaceEvenly,
              children: [
                // GestureDetector(
                //   onTap: () {
                //     print('내 기록 보기 버튼 클릭');
                //   },
                //   child: Stack(
                //     alignment: Alignment.center,
                //     children: [
                //       Image.asset(
                //         'assets/buttons/green_short_button.png',
                //         height: screenHeight * 0.06,
                //         fit: BoxFit.cover,
                //       ),
                //       const Text(
                //         '내 기록',
                //         style: TextStyle(fontSize: 20),
                //       )
                //     ],
                //   ),
                // ),
                GestureDetector(
                  onTap: () {
                    print('기록하기 버튼 클릭');
                  },
                  child: Stack(
                    alignment: Alignment.center,
                    children: [
                      Image.asset(
                        'assets/buttons/green_short_button.png',
                        height: screenHeight * 0.06,
                        fit: BoxFit.cover,
                      ),
                      const Text(
                        '기록하기',
                        style: TextStyle(fontSize: 20),
                      )
                    ],
                  ),
                ),
              ],
            ),
          ),
        ],
      ),
    );
  }
}
