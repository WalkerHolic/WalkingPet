import 'dart:math' as math;
import 'dart:async';

import 'package:flutter/material.dart';
import 'package:walkingpet/home/widgets/mainfontstyle.dart';
import 'package:percent_indicator/percent_indicator.dart';

class BattleSide extends StatefulWidget {
  final int health;
  final int power;
  final int defense;
  final int rating;
  final int characterId; // 이걸로 캐릭터의 종류를 구별
  final int userCharacterLevel;
  final String nickname;
  final bool isLeft;
  final List<double> damageRatioList;
  final List<double> attckDamage;

  const BattleSide({
    super.key,
    required this.health,
    required this.power,
    required this.defense,
    required this.rating,
    required this.characterId,
    required this.userCharacterLevel,
    required this.nickname,
    required this.isLeft,
    required this.damageRatioList,
    required this.attckDamage,
  });

  @override
  State<BattleSide> createState() => _BattleSideState();
}

class _BattleSideState extends State<BattleSide> {
  double _currentPercent = 0.0;

  int _sequenceIndex = 0;
  Timer? _timer;

  @override
  void initState() {
    super.initState();
    final List<double> damageSequence = widget.damageRatioList;
    _timer = Timer.periodic(const Duration(seconds: 1), (timer) {
      if (_sequenceIndex < damageSequence.length) {
        setState(() {
          // 각 시퀀스 값을 현재 퍼센트에 추가하고, 결과가 1을 초과하지 않도록 합니다.
          _currentPercent = (_currentPercent + damageSequence[_sequenceIndex])
              .clamp(0.0, 1.0);

          if (_sequenceIndex + 1 < damageSequence.length) {
            _sequenceIndex++;
          } else {
            _timer?.cancel();
            Navigator.pushReplacementNamed(context, '/battleresult');
          }
        });
      } else {}
    });
  }

  @override
  void dispose() {
    _timer?.cancel();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Column(
      children: [
        const SizedBox(
          height: 300,
        ),
        Row(
          children: [
            Transform.translate(
              offset: const Offset(0, 3),
              child: const MainFontStyle(size: 16, text: "점수: "),
            ),
            MainFontStyle(
              size: 32,
              text: widget.rating.toString(),
            ),
          ],
        ),
        Stack(
          alignment: Alignment.center,
          children: [
            Image.asset(
              widget.isLeft
                  ? 'assets/images/health_bar_left.png'
                  : 'assets/images/health_bar_right.png',
              scale: 1.1,
            ),
            Transform.translate(
              offset:
                  widget.isLeft ? const Offset(15.5, 0) : const Offset(-16, 0),
              child: LinearPercentIndicator(
                backgroundColor: Colors.grey,
                progressColor: Colors.green,
                width: 130,
                percent: 1,
                animation: true,
                lineHeight: 6,
                barRadius: const Radius.circular(2),
              ),
            ),
            Transform(
              alignment: Alignment.center,
              transform: widget.isLeft
                  ? Matrix4.rotationY(math.pi)
                  : Matrix4.identity(),
              child: Transform.translate(
                offset:
                    widget.isLeft ? const Offset(-15, 0) : const Offset(-16, 0),
                child: LinearPercentIndicator(
                  backgroundColor: Colors.transparent,
                  width: 130,
                  percent: _currentPercent,
                  animation: true,
                  lineHeight: 6,
                  barRadius: const Radius.circular(2),
                  animateFromLastPercent: true,
                  animationDuration: 100,
                ),
              ),
            ),
          ],
        ),
        const SizedBox(
          height: 30,
        ),
        Transform(
          alignment: Alignment.center,
          transform:
              widget.isLeft ? Matrix4.identity() : Matrix4.rotationY(math.pi),
          // 여기서 올바르게 translate 적용
          child: Transform.translate(
            offset: const Offset(58, 0),
            child: Image.asset(
              widget.attckDamage[_sequenceIndex] >= 0
                  ? 'assets/animals/cow/cow_attack.gif'
                  : 'assets/animals/cow/cow_hurt.gif',
              scale: 1.2,
            ),
          ),
        ),
      ],
    );
  }
}
