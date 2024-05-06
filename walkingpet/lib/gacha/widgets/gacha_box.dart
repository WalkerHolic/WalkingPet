import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';
import 'package:flutter_svg/flutter_svg.dart';

class GachaBox extends StatelessWidget {
  final String boxName;
  final String boxImage; //박스 이미지 경로
  // final int totalBoxQuantity; //내가 가진 박스
  final String buttonImage; //버튼 이미지 경로
  final int remainCount; // 남은 상자 개수
  final VoidCallback onTap;

  const GachaBox({
    super.key,
    required this.boxName,
    required this.boxImage,
    // required this.totalBoxQuantity,
    required this.buttonImage,
    required this.remainCount,
    required this.onTap,
  });

  @override
  Widget build(BuildContext context) {
    double screenWidth = MediaQuery.of(context).size.width;
    double screenHeight = MediaQuery.of(context).size.height;
    return Column(
      children: [
        Text(boxName, style: TextStyle(fontSize: screenHeight * 0.03)),
        Text('x $remainCount',
            style: TextStyle(fontSize: screenHeight * 0.04, color: Colors.red)),
        Stack(
          alignment: Alignment.center,
          children: [
            Image.asset(
              boxImage,
              width: screenWidth * 0.3,
            ),
          ],
        ),
        GestureDetector(
          onTap: onTap,
          child: SvgPicture.asset(
            buttonImage,
            height: screenHeight * 0.06,
          ),
        ),
      ],
    );
  }
}
