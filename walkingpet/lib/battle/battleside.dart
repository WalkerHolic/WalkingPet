import 'dart:math' as math;
import 'dart:async';

import 'package:flutter/material.dart';
import 'package:walkingpet/common/character_map.dart';
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
  final List<dynamic> attackDamage;
  final List<dynamic> receivedDamage;
  final List<dynamic> userHealth;
  final List<dynamic> loseDamage;

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
    required this.attackDamage,
    required this.receivedDamage,
    required this.userHealth,
    required this.loseDamage,
  });

  @override
  State<BattleSide> createState() => _BattleSideState();
}

class _BattleSideState extends State<BattleSide> {
  String animal = "cow";

  double _currentPercent = 0.0;

  int _sequenceIndex = 0;
  Timer? _timer;

  @override
  void initState() {
    super.initState();
    animal = CharacterMap.idToAnimal[widget.characterId] ?? "Unknown";
    final List<dynamic> damageSequence = widget.loseDamage;

    // 여기에 지연 후 실행할 코드를 작성
    _currentPercent = damageSequence[_sequenceIndex].clamp(0.0, 1.0);
    _timer = Timer.periodic(const Duration(milliseconds: 1800), (timer) {
      if (_sequenceIndex < damageSequence.length) {
        setState(() {
          // 각 시퀀스 값을 현재 퍼센트에 추가하고, 결과가 1을 초과하지 않도록 합니다.
          _currentPercent = damageSequence[_sequenceIndex].clamp(0.0, 1.0);

          if (_sequenceIndex + 1 < damageSequence.length) {
            Timer(const Duration(milliseconds: 500), () {
              _sequenceIndex++;
            });
          } else {
            _timer?.cancel();
            Timer(const Duration(seconds: 1), () {
              // 여기에 지연 후 실행할 코드를 작성
              Navigator.pushReplacementNamed(context, '/battleresult');
            });
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
        SizedBox(
          height: 150,
          child: Stack(
            children: [
              if (widget.attackDamage[_sequenceIndex] < 0)
                MainFontStyle(
                  size: 20,
                  text: "-${widget.receivedDamage[_sequenceIndex]}",
                  color: Colors.red,
                ),
              Transform(
                alignment: Alignment.center,
                transform: widget.isLeft
                    ? Matrix4.identity()
                    : Matrix4.rotationY(math.pi),
                // 여기서 올바르게 translate 적용
                child: Transform.translate(
                  offset: const Offset(58, 0),
                  child: Image.asset(
                    widget.attackDamage[_sequenceIndex] >= 0
                        ? 'assets/animals/$animal/${animal}_attack.gif'
                        : 'assets/animals/$animal/${animal}_hurt.gif',
                    scale: 1.2,
                  ),
                ),
              ),
            ],
          ),
        ),
        Container(
          width: 150,
          height: 150,
          decoration: BoxDecoration(
            color: Colors.white.withOpacity(0.6), // 투명한 흰색 배경 추가
            borderRadius: BorderRadius.circular(10.0), // 모서리를 둥글게 처리
          ),
          padding: const EdgeInsets.all(16),
          child: Center(
            child: Column(
              children: [
                Row(
                  mainAxisAlignment: MainAxisAlignment.center,
                  children: [
                    MainFontStyle(
                        size: 16, text: "Lv.${widget.userCharacterLevel}"),
                    const SizedBox(
                      width: 10,
                    ),
                    MainFontStyle(size: 12, text: widget.nickname),
                  ],
                ),
                const SizedBox(
                  height: 10,
                ),
                Row(
                  crossAxisAlignment: CrossAxisAlignment.center,
                  mainAxisAlignment: MainAxisAlignment.start,
                  children: [
                    Image.asset(
                      'assets/icons/icon_health.png', // assets 폴더에 위치한 SVG 파일
                      height: 20, // 높이 지정
                      width: 20, // 너비 지정
                    ),
                    const SizedBox(
                      width: 10,
                    ),
                    MainFontStyle(size: 16, text: (widget.health).toString()),
                  ],
                ),
                const SizedBox(
                  height: 5,
                ),
                Row(
                  crossAxisAlignment: CrossAxisAlignment.center,
                  mainAxisAlignment: MainAxisAlignment.start,
                  children: [
                    Image.asset(
                      'assets/icons/icon_power.png', // assets 폴더에 위치한 SVG 파일
                      height: 20, // 높이 지정
                      width: 20, // 너비 지정
                    ),
                    const SizedBox(
                      width: 10,
                    ),
                    MainFontStyle(size: 16, text: (widget.power).toString()),
                  ],
                ),
                const SizedBox(
                  height: 5,
                ),
                Row(
                  crossAxisAlignment: CrossAxisAlignment.center,
                  mainAxisAlignment: MainAxisAlignment.start,
                  children: [
                    Image.asset(
                      'assets/icons/icon_depense.png', // assets 폴더에 위치한 SVG 파일
                      height: 20, // 높이 지정
                      width: 20, // 너비 지정
                    ),
                    const SizedBox(
                      width: 10,
                    ),
                    MainFontStyle(size: 16, text: (widget.defense).toString()),
                  ],
                ),
              ],
            ),
          ),
        ),
      ],
    );
  }
}
