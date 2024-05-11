// ignore_for_file: avoid_print

import 'package:flutter/material.dart';
import 'package:pedometer/pedometer.dart';
import 'package:walkingpet/services/character/checkstep.dart';
import 'package:shared_preferences/shared_preferences.dart';

class StepCounter with ChangeNotifier {
  int _steps = 0;
  int _baseSteps = 0;
  late Stream<StepCount> _stepCountStream;
  static SharedPreferences? _prefs;
  static final StepCounter _instance = StepCounter._internal();

  // Private constructor
  StepCounter._internal() {
    initializePedometer();
  }

  // Factory constructor
  factory StepCounter() {
    print(identityHashCode(_instance));
    return _instance;
  }

  int get steps => _steps;
  int get bs => _baseSteps;

  Future<void> initializePedometer() async {
    print("pedometer 초기화");
    _prefs = await SharedPreferences.getInstance();
    await _prefs?.reload();
    _baseSteps = _prefs?.getInt('baseSteps') ?? 0;
    if (_baseSteps == 0) {
      await _prefs?.setInt('baseSteps', 0);
    }

    _stepCountStream = Pedometer.stepCountStream;
    await _fetchInitialSteps();
    _stepCountStream.listen((event) {
      _prefs?.setInt('eventSteps', event.steps);
    });
    _stepCountStream.listen(_onStepCount);
    print("pedometer 초기화 완료");
  }

  Future<void> _fetchInitialSteps() async {
    try {
      int serverSteps = await checkStep();
      print(serverSteps);
      StepCount event = await _stepCountStream.first;
      if (serverSteps >= _steps) {
        _baseSteps = serverSteps * -1;
        await _prefs?.setInt('baseSteps', _baseSteps);
        _steps = event.steps - _baseSteps;
      }
    } catch (e) {
      print("Failed to fetch initial steps: $e");
    }
  }

  void _onStepCount(StepCount event) async {
    print("onStepCount 실행됨");
    _prefs = await SharedPreferences.getInstance();
    await _prefs?.reload();
    print(_prefs?.getInt('baseSteps'));
    _baseSteps = _prefs?.getInt('baseSteps') ?? 0;
    _steps = event.steps - _baseSteps;

    _prefs?.setInt('eventSteps', event.steps);
    await _prefs?.reload();
    print(_prefs);
    print(_prefs?.getInt('eventSteps'));
    notifyListeners();
    print(_steps);
  }

  Future<void> resetStep() async {
    print("걸음수 초기화");
    SharedPreferences prefs = await SharedPreferences.getInstance();
    await prefs.reload();
    print(prefs.getInt('eventSteps'));
    int currentSteps = prefs.getInt('eventSteps') ?? 0;
    print("eventSteps");
    print(currentSteps);
    await prefs.setInt(
        'baseSteps', currentSteps); // baseSteps를 현재의 eventSteps 값으로 설정
    _steps = 0; // 로컬 변수를 리셋
    notifyListeners();
    print("걸음수 초기화 완료");
  }

  @override
  @mustCallSuper
  void dispose() {}
}
