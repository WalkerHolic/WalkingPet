import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';
import 'package:flutter_svg/flutter_svg.dart';

class GachaBox extends StatelessWidget {
  final String boxName;
  final String boxImage; //박스 이미지 경로
  // final int totalBoxQuantity; //내가 가진 박스
  final String buttonImage; //버튼 이미지 경로
  final VoidCallback onTap;

  const GachaBox({
    super.key,
    required this.boxName,
    required this.boxImage,
    // required this.totalBoxQuantity,
    required this.buttonImage,
    required this.onTap,
  });

  @override
  Widget build(BuildContext context) {
    return Column(
      children: [
        Stack(
          alignment: Alignment.center,
          children: [
            Image.asset(
              boxImage,
              width: 100,
              height: 95, // 상자 넘쳐서 150 -> 95로 줄임
            ),
            Positioned(
              top: 0,
              child: Text(
                boxName,
                style: const TextStyle(fontSize: 23),
              ),
            ),
          ],
        ),
        GestureDetector(
          onTap: onTap,
          child: SvgPicture.asset(
            buttonImage,
            height: 45,
          ),
        ),
      ],
    );
  }
}
