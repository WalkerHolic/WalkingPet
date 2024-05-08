import 'dart:math';
import 'package:flutter/material.dart';
import 'package:pedometer/pedometer.dart';
import 'package:walkingpet/services/character/checkstep.dart';
import 'package:shared_preferences/shared_preferences.dart';

class StepCounter with ChangeNotifier {
  int _steps = 0;
  int _baseSteps = 0;
  late Stream<StepCount> _stepCountStream;
  static SharedPreferences? _prefs;

  static StepCounter? _instance;

  factory StepCounter() {
    _instance ??= StepCounter._internal();
    return _instance!;
  }

  StepCounter._internal() {
    _initializePedometer();
  }

  int get steps => _steps;

  Future<void> _initializePedometer() async {
    _prefs = await SharedPreferences.getInstance();
    _baseSteps = _prefs?.getInt('baseSteps') ?? 0;

    _stepCountStream = Pedometer.stepCountStream;
    _stepCountStream.listen(_onStepCount);
    _fetchInitialSteps();
  }

  Future<void> _fetchInitialSteps() async {
    try {
      int serverSteps = await checkStep();
      StepCount event = await _stepCountStream.first;

      if (serverSteps > event.steps) {
        _baseSteps -= serverSteps;
        await _prefs?.setInt('baseSteps', _baseSteps);
      }
    } catch (e) {
      print("Failed to fetch initial steps: $e");
    }
  }

  void _onStepCount(StepCount event) {
    _steps = event.steps - _baseSteps;
    notifyListeners();
  }

  void resetSteps() async {
    try {
      StepCount event = await _stepCountStream.first;
      _baseSteps = event.steps;
      await _prefs?.setInt('baseSteps', _baseSteps);
      notifyListeners();
      print("걸음 수가 리셋되었습니다.");
    } catch (e) {
      print("걸음 수 리셋 중 오류 발생: $e");
    }
  }

  @override
  @mustCallSuper
  void dispose() {
    // 싱글톤이므로 dispose 로직을 비활성화
  }
}
