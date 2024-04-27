import 'package:flutter/material.dart';
import 'package:pedometer/pedometer.dart';

class StepCounter with ChangeNotifier {
  int _steps = 0;
  late Stream<StepCount> _stepCountStream;

  StepCounter() {
    _initializePedometer();
  }

  int get steps => _steps;

  void _initializePedometer() {
    _stepCountStream = Pedometer.stepCountStream;
    _stepCountStream.listen(_onStepCount);
  }

  void _onStepCount(StepCount event) {
    _steps = event.steps;
    notifyListeners();
  }
}
