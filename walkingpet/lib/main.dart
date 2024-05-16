import 'dart:async';

import 'package:flutter/material.dart';
import 'package:nes_ui/nes_ui.dart';
import 'package:walkingpet/battle/battleready.dart';
import 'package:walkingpet/character/characterinfo.dart';
import 'package:walkingpet/providers/character_info.dart';
import 'package:walkingpet/providers/step_counter.dart';
import 'package:walkingpet/gacha/gacha.dart';
import 'package:walkingpet/goal/goal.dart';
import 'package:walkingpet/group/group.dart';
import 'package:walkingpet/home/home.dart';
import 'package:walkingpet/login/login.dart';
import 'package:walkingpet/ranking/ranking.dart';
import 'package:flutter/services.dart'; // SystemChrome을 사용하기 위해 필요
import 'package:permission_handler/permission_handler.dart';
import 'package:provider/provider.dart';
import 'package:walkingpet/providers/gachabox_count_provider.dart';
import 'package:kakao_flutter_sdk_common/kakao_flutter_sdk_common.dart';
import 'package:shared_preferences/shared_preferences.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';

import 'package:intl/intl.dart';
import 'package:walkingpet/record/record.dart';

void main() async {
  WidgetsFlutterBinding.ensureInitialized();
  await _requestPermissions();
  _scheduleDailyTask();
  //_initSSE();

  /* 상단바, 하단바 모두 표시 & 상단바 투명하게 */
  SystemChrome.setSystemUIOverlayStyle(const SystemUiOverlayStyle(
    statusBarColor: Colors.transparent, // 상단바 배경을 투명하게
    statusBarIconBrightness: Brightness.dark, // 상단바 아이콘을 어둡게 (라이트 모드)
  ));

  /* 세로 방향 고정 */
  SystemChrome.setPreferredOrientations([
    DeviceOrientation.portraitUp, // 세로 방향으로 위를 가리킴
    DeviceOrientation.portraitDown // 세로 방향으로 아래를 가리킴
  ]);

  KakaoSdk.init(
    nativeAppKey: '779b15a502d5db79f97478c1a0076650',
    javaScriptAppKey: '98dfecd4151782eef7342a07e95b9c57',
  );

  // REFRESH_TOKEN 확인
  const storage = FlutterSecureStorage();
  String? refreshToken = await storage.read(key: 'REFRESH_TOKEN');

  runApp(
    MultiProvider(
      providers: [
        ChangeNotifierProvider(create: (context) => BoxCounterProvider()),
        ChangeNotifierProvider<StepCounter>(create: (context) => StepCounter()),
        ChangeNotifierProvider<CharacterProvider>(
          create: (context) => CharacterProvider(),
        ),
      ],
      child: MyApp(startRoute: refreshToken != null ? '/home' : '/login'),
    ),
  );
  //SystemChrome.setEnabledSystemUIMode(SystemUiMode.immersive);
  // SystemChannels.navigation.setMethodCallHandler((MethodCall call) async {
  //   if (call.method == 'popRoute') {
  //     return Future.value(false); // 뒤로 가기 이벤트를 무시합니다.
  //   }
  //   return Future.value(true);
  // }
}

class MyApp extends StatelessWidget {
  final String startRoute;

  const MyApp({super.key, required this.startRoute});

  @override
  Widget build(BuildContext context) {
    // 1. 기본 테마 가져오기
    final ThemeData baseTheme = flutterNesTheme();

    // 2. TextTheme 생성하고, 모든 텍스트 스타일에 DungGeunMo 글꼴 적용!
    final TextTheme textTheme = baseTheme.textTheme.copyWith(
      titleLarge:
          baseTheme.textTheme.titleLarge?.copyWith(fontFamily: 'DungGeunMo'),
      titleMedium:
          baseTheme.textTheme.titleMedium?.copyWith(fontFamily: 'DungGeunMo'),
      titleSmall:
          baseTheme.textTheme.titleSmall?.copyWith(fontFamily: 'DungGeunMo'),
      headlineLarge:
          baseTheme.textTheme.headlineLarge?.copyWith(fontFamily: 'DungGeunMo'),
      headlineMedium: baseTheme.textTheme.headlineMedium
          ?.copyWith(fontFamily: 'DungGeunMo'),
      headlineSmall:
          baseTheme.textTheme.headlineSmall?.copyWith(fontFamily: 'DungGeunMo'),
      bodyLarge:
          baseTheme.textTheme.bodyLarge?.copyWith(fontFamily: 'DungGeunMo'),
      bodyMedium:
          baseTheme.textTheme.bodyMedium?.copyWith(fontFamily: 'DungGeunMo'),
      bodySmall:
          baseTheme.textTheme.bodySmall?.copyWith(fontFamily: 'DungGeunMo'),
      labelLarge:
          baseTheme.textTheme.labelLarge?.copyWith(fontFamily: 'DungGeunMo'),
      labelMedium:
          baseTheme.textTheme.labelMedium?.copyWith(fontFamily: 'DungGeunMo'),
      labelSmall:
          baseTheme.textTheme.labelSmall?.copyWith(fontFamily: 'DungGeunMo'),
      displayLarge:
          baseTheme.textTheme.displayLarge?.copyWith(fontFamily: 'DungGeunMo'),
    );

    // 3. 수정된 TextTheme + 기존 테마 (결합) => 새 테마 생성
    final ThemeData newTheme = baseTheme.copyWith(
      textTheme: textTheme,
    );

    return MaterialApp(
      debugShowCheckedModeBanner: false,
      title: 'Waking Pet',
      theme: newTheme,
      //initialRoute: startRoute,
      initialRoute: startRoute,
      routes: {
        '/login': (context) => const Login(),
        '/home': (context) => const Home(),
        '/goal': (context) => const Goal(),
        '/ranking': (context) => const Ranking(),
        '/characterinfo': (context) => const CharacterInfo(),
        '/gacha': (context) => const Gacha(),
        '/battleready': (context) => const BattleReady(),
        '/group': (context) => const Group(),
        '/record': (context) => const Record(),
      },
    );
  }
}

Future<bool> checkFirstVisitToday() async {
  final prefs = await SharedPreferences.getInstance();
  await prefs.reload();
  String? lastVisit = prefs.getString('lastVisit'); // 마지막 접속 날짜를 가져옴
  // String today = DateFormat('yyyy-MM-dd')
  //     .format(DateTime.now()); // 오늘 날짜를 'yyyy-MM-dd' 형식으로 포맷

  //테스트용
  DateTime now = DateTime.now(); // 현재 시간을 가져옵니다.

  if (lastVisit != DateFormat('yyyy-MM-dd').format(DateTime.now())) {
    await prefs.setString(
        'lastVisit',
        DateFormat('yyyy-MM-dd')
            .format(DateTime.now())); // 오늘 날짜로 마지막 접속 날짜를 업데이트
    await StepCounter().resetStep();
    return true;
  } else {
    // 이미 오늘 접속한 경우 실행할 로직 추가 (아무것도 하지 않음)
    return false;
  }
}

// 권한 요청 메소드 정의
Future<void> _requestPermissions() async {
  // ACTIVITY_RECOGNITION 권한 요청
  var status = await Permission.activityRecognition.request();
  if (status.isGranted) {
    return;
  } else {
    // 사용자가 권한을 거부한 경우 처리할 로직 추가 가능
    SystemNavigator.pop();
  }
}

// 11시 59분부터 12시 까지 1초마다 StepCounter().resetStep() 실행
void _scheduleDailyTask() {
  DateTime now = DateTime.now();
  DateTime firstRun = DateTime(now.year, now.month, now.day, 23, 59, 00);

  if (now.isAfter(firstRun) &&
      now.isBefore(DateTime(now.year, now.month, now.day, 0, 0, 0)
          .add(const Duration(days: 1)))) {
    // 현재 시간이 23:59:55와 00:00:00 사이에 들어오면 즉시 실행
    StepCounter().resetStep();
    // 이후 매일 같은 시간에 실행되도록 타이머 재설정
    firstRun = firstRun.add(const Duration(days: 1));
  } else if (now.isAfter(firstRun)) {
    // 현재 시간이 23:59:55를 넘었으면 다음 날로 설정
    firstRun = firstRun.add(const Duration(days: 1));
  }

  Duration initialDelay = firstRun.difference(now);
  Timer(initialDelay, () {
    DateTime endRun =
        DateTime(firstRun.year, firstRun.month, firstRun.day, 0, 0);
    Timer.periodic(const Duration(seconds: 1), (timer) async {
      DateTime currentTime = DateTime.now();
      if (currentTime.isAfter(endRun)) {
        timer.cancel();
      } else {
        await StepCounter().resetStep();
      }
    });
  });
}
