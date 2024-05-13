import 'package:flutter/material.dart';
import 'package:flutter_svg/flutter_svg.dart';
import 'package:walkingpet/main.dart';
import 'package:walkingpet/services/goal/get_daily_reward.dart';

class DailyGoalItem extends StatefulWidget {
  final String title;
  // 서버에서 받은 데이터에 따라 초기 활성화 상태 변경
  final bool isActivated; //활성화 여부
  final bool isCompleted;
  final int goalSteps; // 각각 목표에 따라 리워드 지급 요청을 보내기 위해

  const DailyGoalItem({
    //생성자
    super.key,
    required this.title,
    //기존 상태 = 비활성
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
    //목표가 이미 완료된 경우 _isPressed를 true로 설정
    _isPressed = widget.isCompleted;
  }

  void _toggleButton() async {
    bool isFirstVisit = await checkFirstVisitToday();
    if (isFirstVisit) {
      Navigator.pop(context);
      return;
    }
    // 버튼이 이미 완료된 상태라면 요청을 보내지 않음
    if (_isPressed || widget.isCompleted) {
      ScaffoldMessenger.of(context).showSnackBar(const SnackBar(
        content: Text("이미 완료된 목표입니다."),
        duration: Duration(seconds: 2),
      ));
      return; // 더 이상의 작업을 수행하지 않고 함수 종료
    }

    // 버튼이 비활성화된 경우
    if (!widget.isActivated) {
      ScaffoldMessenger.of(context).showSnackBar(const SnackBar(
        content: Text("이 목표는 활성화되지 않았습니다."),
        duration: Duration(seconds: 2),
      ));
      return; // 비활성화 상태에서 요청을 보내지 않고 함수 종료
    }

    // 보상 요청을 서버로 보냄
    sendRewardRequest(widget.goalSteps).then((reward) {
      setState(() {
        _isPressed = true;
      });
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(
          content: Text("보상이 지급되었습니다. : $reward"),
          duration: const Duration(seconds: 2),
        ),
      );
    });
  }

  String getButtonAsset() {
    if (_isPressed) {
      print('이미 달성된 경우 : $_isPressed');
      //이미 달성된 경우
      return 'assets/buttons/button_complete.svg';
    } else if (widget.isActivated) {
      //달성되지 않았지만 활성화된 경우
      print('달성되지 않았지만 활성화된 경우 : $_isPressed');
      //왜 isPressed가 false로 뜨지?
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
