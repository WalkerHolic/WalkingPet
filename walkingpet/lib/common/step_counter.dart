import 'package:flutter/material.dart';
import 'package:pedometer/pedometer.dart';
import 'package:walkingpet/services/character/checkstep.dart';

class StepCounter with ChangeNotifier {
  int _steps = 0;
  late Stream<StepCount> _stepCountStream;
  int _baseSteps = 0; // 기기에서 측정된 초기 걸음수를 저장할 변수
  int _realSteps = 0;

  StepCounter() {
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
      _realSteps = serverSteps > _baseSteps ? serverSteps : _baseSteps;
      _steps = serverSteps > _baseSteps ? serverSteps : _baseSteps;
    } catch (e) {
      print("Failed to fetch initial steps: $e");
    }
  }

  void _onStepCount(StepCount event) {
    _steps = _realSteps + event.steps - _baseSteps; // 새로운 걸음수를 계산
    notifyListeners();
    print(_steps);
  }

  void setSteps(int newSteps) {
    _steps = newSteps;

    notifyListeners(); // 상태 변경을 알림
  }
}
