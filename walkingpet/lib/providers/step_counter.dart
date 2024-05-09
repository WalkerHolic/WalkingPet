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
    _initializePedometer();
  }

  // Factory constructor
  factory StepCounter() {
    print(identityHashCode(_instance));
    return _instance;
  }

  int get steps => _steps;
  int get bs => _baseSteps;

  Future<void> _initializePedometer() async {
    _prefs = await SharedPreferences.getInstance();
    _baseSteps = _prefs?.getInt('baseSteps') ?? 0;
    if (_baseSteps == 0) {
      await _prefs?.setInt('baseSteps', 0);
    }
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

  void _onStepCount(StepCount event) async {
    _prefs?.reload();
    _baseSteps = _prefs?.getInt('baseSteps') ?? 0;
    _steps = event.steps - _baseSteps;

    await _prefs?.setInt('eventSteps', event.steps);
    notifyListeners();
  }

  @override
  @mustCallSuper
  void dispose() {}
}
