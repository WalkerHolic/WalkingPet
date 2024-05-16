import 'package:flutter/material.dart';
import 'package:pedometer/pedometer.dart';
import 'package:walkingpet/services/character/checkstep.dart';
import 'package:shared_preferences/shared_preferences.dart';
import 'package:intl/intl.dart';

class StepCounter with ChangeNotifier {
  int _steps = 0;
  int _baseSteps = 0;
  late Stream<StepCount> _stepCountStream;
  static SharedPreferences? _prefs;
  static final StepCounter _instance = StepCounter._internal();
  bool _resetting = false;

  // Private constructor
  StepCounter._internal() {
    initializePedometer();
  }

  // Factory constructor
  factory StepCounter() {
    return _instance;
  }

  int get steps => _steps;

  Future<void> initializePedometer() async {
    _stepCountStream = Pedometer.stepCountStream;
    _prefs = await SharedPreferences.getInstance();
    await _prefs?.reload();
    _baseSteps = _prefs?.getInt('baseSteps') ?? 0;
    if (_baseSteps == 0) {
      StepCount event = await _stepCountStream.first;
      await _prefs?.setInt('baseSteps', event.steps);
    }

    await checkFirstVisitToday();
    await _fetchInitialSteps();
    _stepCountStream.listen(_onStepCount);
  }

  void _onStepCount(StepCount event) async {
    if (_resetting) return;
    _prefs = await SharedPreferences.getInstance();
    await _prefs?.reload();
    _baseSteps = _prefs?.getInt('baseSteps') ?? 0;
    _steps = event.steps - _baseSteps;
    notifyListeners();
  }

  Future<void> _fetchInitialSteps() async {
    try {
      int serverSteps = await checkStep();
      StepCount event = await _stepCountStream.first;
      _prefs = await SharedPreferences.getInstance();
      await _prefs?.reload();
      _baseSteps = _prefs?.getInt('baseSteps') ?? 0;

      if (serverSteps > event.steps - _baseSteps) {
        int diff = serverSteps - (event.steps - _baseSteps);
        _baseSteps -= diff;
        await _prefs?.setInt('baseSteps', _baseSteps);
        _steps = event.steps - _baseSteps;
        notifyListeners();
      }
    } catch (e) {
      print("Failed to fetch initial steps: $e");
    }
  }

  Future<void> resetStep() async {
    _resetting = true;
    SharedPreferences prefs = await SharedPreferences.getInstance();
    await prefs.reload();
    _steps = 0;
    notifyListeners();
    StepCount event = await _stepCountStream.first;
    await prefs.setInt('baseSteps', event.steps);
    _steps = 0;
    notifyListeners();
    _resetting = false;
  }

  Future<void> checkFirstVisitToday() async {
    final prefs = await SharedPreferences.getInstance();
    await prefs.reload();

    String today = DateFormat('yyyy-MM-dd').format(DateTime.now());
    String? lastVisit = prefs.getString('lastVisit');

    if (lastVisit == null) {
      await prefs.setString('lastVisit', today);

      int serverSteps = await checkStep();
      if (serverSteps == -1) return;
      StepCount event = await _stepCountStream.first;
      int diff = event.steps - serverSteps;
      await prefs.setInt('baseSteps', diff);
      _steps = serverSteps;
      notifyListeners();

      return;
    }

    if (lastVisit != today) {
      await prefs.setString('lastVisit', today);
      await StepCounter().resetStep();
    } else {
      await prefs.setString('lastVisit', today);
    }
  }

  @override
  @mustCallSuper
  void dispose() {}
}
