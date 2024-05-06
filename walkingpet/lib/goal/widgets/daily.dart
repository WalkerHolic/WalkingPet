import 'package:flutter/material.dart';
import 'package:flutter_svg/flutter_svg.dart';
import 'package:walkingpet/services/goal/get_daily_reward.dart';

class DailyGoalItem extends StatefulWidget {
  final String title;
  // 서버에서 받은 데이터에 따라 초기 활성화 상태 변경
  final isActivated;
  final int goalSteps; // 각각 목표에 따라 리워드 지급 요청을 보내기 위해

  const DailyGoalItem({
    //생성자
    super.key,
    required this.title,
    //기존 상태 = 비활성
    required this.isActivated,
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
    _isPressed = false;
  }

  _toggleButton() {
    if (!widget.isActivated) {
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(
          content: Text("이 목표는 이미 완료되었거나, 활성화 되지 않았습니다."),
          duration: Duration(seconds: 2),
        ),
      );
    } else if (!_isPressed) {
      sendRewardRequest(widget.goalSteps).then((success) {
        if (success) {
          setState(() {
            _isPressed = true;
          });
        } else {
          ScaffoldMessenger.of(context).showSnackBar(
            const SnackBar(
              content: Text("리워드 요청에 실패했습니다."),
              duration: Duration(seconds: 2),
            ),
          );
        }
      });
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
              ),
            ),
          ],
        ),
      ),
    );
  }
}
