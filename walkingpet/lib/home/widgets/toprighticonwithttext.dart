import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';
import 'package:flutter_svg/flutter_svg.dart';

class TopRightIconWithText extends StatelessWidget {
  final String icon;
  final String text;

  const TopRightIconWithText({
    super.key,
    required this.icon,
    required this.text,
  });

  @override
  Widget build(BuildContext context) {
    return Column(
      children: [
        IconButton(
          onPressed: () {
            Navigator.pushNamed(context, '/$icon');
          },
          icon: SvgPicture.asset(
            'assets/icons/$icon.svg',
            width: 60,
            height: 60,
          ),
        ),
        Transform.translate(
          offset: const Offset(0, -10),
          child: Text(
            text,
            style: const TextStyle(
              fontSize: 20,
              fontWeight: FontWeight.bold,
              color: Colors.white,
              shadows: [
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
          ),
        )
      ],
    );
  }
}
