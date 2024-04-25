import 'package:flutter/material.dart';
import 'package:flutter_svg/flutter_svg.dart';

class MainFontStyle extends StatelessWidget {
  final double size;
  final String text;

  const MainFontStyle({
    super.key,
    required this.size,
    required this.text,
  });

  @override
  Widget build(BuildContext context) {
    return Text(
      text,
      style: TextStyle(
        fontSize: size,
        color: Colors.white,
        shadows: const [
          Shadow(
            // 외곽선 색상 및 오프셋 설정
            offset: Offset(-1.5, -1.5),
            color: Colors.black,
            blurRadius: 1,
          ),
          Shadow(
            offset: Offset(1.5, -1.5),
            color: Colors.black,
            blurRadius: 1,
          ),
          Shadow(
            offset: Offset(1.5, 1.5),
            color: Colors.black,
            blurRadius: 1,
          ),
          Shadow(
            offset: Offset(-1.5, 1.5),
            color: Colors.black,
            blurRadius: 1,
          ),
        ],
      ),
    );
  }
}
