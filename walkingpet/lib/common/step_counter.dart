import 'package:flutter/material.dart';
import 'package:pedometer/pedometer.dart';
import 'package:walkingpet/services/character/checkstep.dart';

class StepCounter with ChangeNotifier {
  int _steps = 0;
  late Stream<StepCount> _stepCountStream;
  int _baseSteps = 0; // 기기에서 측정된 초기 걸음수를 저장할 변수
  final int _realSteps = 0;
  int _serverSteps = 0;

  static final StepCounter _instance = StepCounter._internal();

  factory StepCounter() {
    return _instance;
  }

  StepCounter._internal() {
    _initializePedometer();
  }

  int get steps => _steps;

  void _initializePedometer() {
    _stepCountStream = Pedometer.stepCountStream;
    _stepCountStream
        .listen(_onStepCount); // 새로운 걸음수 데이터가 도착할 때마다 _onStepCount 콜백 함수르르 호출
    _fetchInitialSteps();
  }

  Future<void> _fetchInitialSteps() async {
    try {
      int serverSteps = await checkStep(); // 서버에서 걸음수를 가져옴
      StepCount event = await _stepCountStream.first;
      _baseSteps = event.steps;
      if (serverSteps > event.steps) _serverSteps = serverSteps;
      _steps = event.steps - _baseSteps + _serverSteps;
    } catch (e) {
      print("Failed to fetch initial steps: $e");
    }
  }

  void _onStepCount(StepCount event) {
    _steps = event.steps - _baseSteps + _serverSteps; // 새로운 걸음수를 계산
    notifyListeners();
  }

  // 현재 쓰는 곳 없음
  void setSteps(int newSteps) {
    _steps = newSteps;

    notifyListeners(); // 상태 변경을 알림
  }

  /* 자정에 백그라운드에서 실행할 함수 */
  void resetSteps() async {
    print("백그라운드 되나요");
    StepCount event = await _stepCountStream.first;
    _baseSteps = event.steps;
    _serverSteps = 0;

    notifyListeners(); // 상태 변경을 알림
  }
}
