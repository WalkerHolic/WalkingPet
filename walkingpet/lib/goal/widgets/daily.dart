import 'package:flutter/material.dart';
import 'package:flutter_svg/flutter_svg.dart';
import 'package:walkingpet/services/goal/get_daily_reward.dart';

class DailyGoalItem extends StatefulWidget {
  final String title;
  final bool isActivated;
  final bool isCompleted;
  final int goalSteps;

  const DailyGoalItem({
    super.key,
    required this.title,
    required this.isActivated,
    required this.isCompleted,
    required this.goalSteps,
  });

  @override
  _DailyGoalItemState createState() => _DailyGoalItemState();
}

class _DailyGoalItemState extends State<DailyGoalItem> {
  late bool _isPressed;

  @override
  void initState() {
    super.initState();
    _isPressed = widget.isCompleted;
  }

  void _toggleButton() async {
    final scaffoldMessenger = ScaffoldMessenger.of(context);

    // 현재 표시된 스낵바가 있다면 숨김
    scaffoldMessenger.hideCurrentSnackBar();

    if (_isPressed || widget.isCompleted) {
      _showSnackBar("이미 완료된 목표입니다.");
      return;
    }

    if (!widget.isActivated) {
      _showSnackBar("이 목표는 활성화되지 않았습니다.");
      return;
    }

    sendRewardRequest(widget.goalSteps).then((reward) {
      setState(() {
        _isPressed = true;
      });
      _showSnackBar("보상이 지급되었습니다.");
    });
  }

  void _showSnackBar(String message) {
    final scaffoldMessenger = ScaffoldMessenger.of(context);

    // 현재 표시된 스낵바가 있다면 숨김
    scaffoldMessenger.hideCurrentSnackBar();

    scaffoldMessenger.showSnackBar(
      SnackBar(
        content: Text(message),
        duration: const Duration(seconds: 1),
      ),
    );
  }

  String getButtonAsset() {
    if (_isPressed) {
      return 'assets/buttons/button_complete.svg';
    } else if (widget.isActivated) {
      return 'assets/buttons/brown_button.svg';
    } else {
      return 'assets/buttons/button_not_enough.svg';
    }
  }

  @override
  Widget build(BuildContext context) {
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
              style: TextStyle(
                fontSize: screenHeight * 0.024,
              ),
            ),
            GestureDetector(
              onTap: _toggleButton,
              child: SvgPicture.asset(
                getButtonAsset(),
                width: screenWidth * 0.18,
              ),
            ),
          ],
        ),
      ),
    );
  }
}
