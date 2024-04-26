import 'package:flutter/material.dart';
import 'package:flutter_svg/flutter_svg.dart';

class DailyGoalItem extends StatefulWidget {
  final String title;

  const DailyGoalItem({
    super.key,
    required this.title,
  });
  @override
  _DailyGoalItemState createState() => _DailyGoalItemState();
}

class _DailyGoalItemState extends State<DailyGoalItem> {
  bool _isPressed = false;

  _toggleButton() {
    setState(() {
      _isPressed = true;
    });
  }

  @override
  Widget build(BuildContext context) {
    // 현재 화면의 크기를 가져오기
    double screenWidth = MediaQuery.of(context).size.width;
    double screenHeight = MediaQuery.of(context).size.height;
    return SizedBox(
      width: screenWidth * 0.7,
      child: Padding(
        padding: const EdgeInsets.symmetric(vertical: 2),
        child: Row(
          mainAxisAlignment: MainAxisAlignment.spaceBetween,
          children: [
            Text(
              widget.title,
              style: const TextStyle(
                fontSize: 23,
              ),
            ),
            GestureDetector(
                onTap: _toggleButton,
                child: SvgPicture.asset(
                  _isPressed
                      ? 'assets/buttons/brown_button_pushed.svg'
                      : 'assets/buttons/brown_button.svg',
                  width: 75,
                )),
          ],
        ),
      ),
    );
  }
}
