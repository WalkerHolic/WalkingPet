// ignore_for_file: avoid_print

import 'package:flutter/material.dart';
import 'package:pedometer/pedometer.dart';
import 'package:walkingpet/services/character/checkstep.dart';
import 'package:shared_preferences/shared_preferences.dart';
import 'package:intl/intl.dart'; // 날짜 포맷을 위한 패키지

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
    // 싱글톤 고유성 확인
    //print(identityHashCode(_instance));
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

    await checkFirstVisitToday(); // 오늘 첫 방문인지 확인 (재부팅도 여기서 체크)
    await _fetchInitialSteps();
    _stepCountStream.listen(_onStepCount);
  }

  void _onStepCount(StepCount event) async {
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
      }
    } catch (e) {
      print("Failed to fetch initial steps: $e");
    }
  }

  Future<void> resetStep() async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    await prefs.reload();
    StepCount event = await _stepCountStream.first;
    await prefs.setInt(
        'baseSteps', event.steps); // baseSteps를 현재의 event.steps 값으로 설정
    await prefs.reload();
    _steps = 0; // 로컬 변수를 리셋
    notifyListeners();
  }

  Future<void> checkFirstVisitToday() async {
    final prefs = await SharedPreferences.getInstance();
    await prefs.reload();

    // // 테스트 코드
    // DateTime now = DateTime.now();
    // DateTime today =
    //     DateTime(now.year, now.month, now.day, 17, 5); // 오늘 오전 9시 7분

    // // 기준 시간보다 이전이면 어제를 기준 날짜로 설정
    // if (now.isBefore(today)) {
    //   today = today.subtract(const Duration(days: 1));
    // }

    // String todayStr =
    //     DateFormat('yyyy-MM-dd').format(today); // 기준 날짜를 'yyyy-MM-dd' 형식으로 포맷
    // String? lastVisit = prefs.getString('lastVisit'); // 마지막 접속 날짜를 가져옴

    String today = DateFormat('yyyy-MM-dd')
        .format(DateTime.now()); // 오늘 날짜를 'yyyy-MM-dd' 형식으로 포맷
    String? lastVisit = prefs.getString('lastVisit'); // 마지막 접속 날짜를 가져옴

    print("lastVisit있니? $lastVisit");
    if (lastVisit == null) {
      //await prefs.setString('lastVisit', today);
      await prefs.setString('lastVisit', today);

      // 지웠다 다시 깔았을 경우 서버 데이터로 초기화
      int serverSteps = await checkStep();
      if (serverSteps == -1) return; // 액세스토큰이 없는 상태에서 요청을 보냈을 경우 (즉, 완전 첫 이용)
      StepCount event = await _stepCountStream.first;
      int diff = event.steps - serverSteps;
      await prefs.setInt('baseSteps', diff);
      _steps = serverSteps;

      return;
    }

    // todayStr(테스트) -> today(실제)
    if (lastVisit != today) {
      // 자정이 넘어갔다! 걸음수를 초기화 하자!!
      await prefs.setString('lastVisit', today); // 오늘 날짜로 마지막 접속 날짜를 업데이트
      await StepCounter().resetStep();
    } else {
      // 이미 오늘 접속한 경우 실행할 로직 추가 (아무것도 하지 않음)
      await prefs.setString('lastVisit', today);
    }
  }

  @override
  @mustCallSuper
  void dispose() {}
}
